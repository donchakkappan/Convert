package com.allutils.feature_currency.data

import com.allutils.base.result.Result
import com.allutils.base.retrofit.ApiResult
import com.allutils.feature_currency.BuildConfig
import com.allutils.feature_currency.data.local.CurrenciesDao
import com.allutils.feature_currency.data.local.models.entities.toDomainModel
import com.allutils.feature_currency.data.network.ConversionRatesNetworkService
import com.allutils.feature_currency.data.network.models.response.toConversionRatesEntityModel
import com.allutils.feature_currency.data.network.models.response.toDomainModel
import com.allutils.feature_currency.data.network.models.response.toMetaDataEntityModel
import com.allutils.feature_currency.domain.CurrenciesRepository
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import timber.log.Timber

internal class CurrenciesRepositoryImpl(
    private val networkService: ConversionRatesNetworkService,
    private val databaseService: CurrenciesDao
) : CurrenciesRepository {

    override suspend fun getConversionRates(
        baseCode: String
    ): Result<List<ConversionRatesOutput>> =
        when (val networkResponse =
            networkService.getConversionRates(BuildConfig.GRADLE_CURRENCY_API_KEY, baseCode)) {
            is ApiResult.Success -> {
                val conversionRates = networkResponse
                    .data
                    .also {
                        databaseService.insert(it.toMetaDataEntityModel())
                        databaseService.insertConversionRates(it.toConversionRatesEntityModel())
                    }
                    .toDomainModel()
                Result.Success(conversionRates)
            }

            is ApiResult.Error -> {
                Result.Failure()
            }

            is ApiResult.Exception -> {
                Timber.e(networkResponse.throwable)

                val localConversionRates = databaseService
                    .getRates(baseCode = baseCode)
                    .map { it.toDomainModel() }

                Result.Success(localConversionRates)
            }
        }

}