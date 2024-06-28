package com.allutils.feature_currency.data

import com.allutils.base.retrofit.ApiResult
import com.allutils.feature_currency.BuildConfig
import com.allutils.feature_currency.data.database.CurrenciesDao
import com.allutils.feature_currency.data.database.models.entities.toDomainModel
import com.allutils.feature_currency.data.network.ConversionRatesNetworkService
import com.allutils.feature_currency.data.network.models.response.toConversionRatesEntityModel
import com.allutils.feature_currency.data.network.models.response.toMetaDataEntityModel
import com.allutils.feature_currency.domain.ICurrenciesRepository
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.utils.Resource
import com.allutils.feature_currency.utils.networkBoundResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class CurrenciesRepositoryImpl(
    private val networkService: ConversionRatesNetworkService,
    private val databaseService: CurrenciesDao
) : ICurrenciesRepository {

    override suspend fun getConversionRates(
        baseCode: String
    ): Flow<Resource<List<ConversionRatesOutput>>> = networkBoundResource(
        query = {
            flow {
                val localConversionRates = databaseService
                    .getAllConversionRates(baseCode = baseCode)
                    .map { it.toDomainModel() }

                emit(localConversionRates)
            }
        },

        fetch = {
            networkService.getConversionRates(BuildConfig.GRADLE_CURRENCY_API_KEY, baseCode)
        },

        saveFetchResult = { conversionRates ->

            when (conversionRates) {
                is ApiResult.Success -> {
                    conversionRates
                        .data
                        .also {
                            databaseService.insertConversionRateMetaData(it.toMetaDataEntityModel())
                            databaseService.insertBulkConversionRates(it.toConversionRatesEntityModel())
                        }
                }

                is ApiResult.Error -> {

                }

                is ApiResult.Exception -> {

                }
            }
        },

        shouldFetch = { conversionRates ->
            conversionRates.isEmpty()
        }
    )

    override suspend fun getFavoriteConversionRates(baseCode: String): Flow<Resource<List<ConversionRatesOutput>>> = networkBoundResource(
        query = {
            flow {
                val localConversionRates = databaseService
                    .getFavoriteConversionRates(baseCode = baseCode)
                    .map { it.toDomainModel() }

                emit(localConversionRates)
            }
        },

        fetch = {
            networkService.getConversionRates(BuildConfig.GRADLE_CURRENCY_API_KEY, baseCode)
        },

        saveFetchResult = { conversionRates ->

            when (conversionRates) {
                is ApiResult.Success -> {
                    conversionRates
                        .data
                        .also {
                            databaseService.insertConversionRateMetaData(it.toMetaDataEntityModel())
                            databaseService.insertBulkConversionRates(it.toConversionRatesEntityModel())
                        }
                }

                is ApiResult.Error -> {

                }

                is ApiResult.Exception -> {

                }
            }
        },

        shouldFetch = { conversionRates ->
            conversionRates.isEmpty()
        }
    )

    override suspend fun getLocalConversionRate(baseCode: String, localCurrencyCode: String): Flow<Resource<List<ConversionRatesOutput>>> =
        networkBoundResource(
            query = {
                flow {
                    val localConversionRates = databaseService
                        .getLocalConversionRate(baseCode = baseCode, currencyCode = localCurrencyCode)
                        .map { it.toDomainModel() }

                    emit(localConversionRates)
                }
            },

            fetch = {
                networkService.getConversionRates(BuildConfig.GRADLE_CURRENCY_API_KEY, baseCode)
            },

            saveFetchResult = { conversionRates ->

                when (conversionRates) {
                    is ApiResult.Success -> {
                        conversionRates
                            .data
                            .also {
                                databaseService.insertConversionRateMetaData(it.toMetaDataEntityModel())
                                databaseService.insertBulkConversionRates(it.toConversionRatesEntityModel())
                            }
                    }

                    is ApiResult.Error -> {

                    }

                    is ApiResult.Exception -> {

                    }
                }
            },

            shouldFetch = { conversionRates ->
                conversionRates.isEmpty()
            }
        )

    override suspend fun isThereAnyFavorite(): Boolean {
        return databaseService.hasFavoriteItem() > 0
    }

    override suspend fun markFavoriteAndGetAll(baseCode: String, favoriteCode: String): Flow<Resource<List<ConversionRatesOutput>>> =
        networkBoundResource(
            query = {
                flow {
                    val localConversionRates = databaseService
                        .getFavoriteConversionRates(baseCode = baseCode)
                        .map { it.toDomainModel() }

                    emit(localConversionRates)
                }
            },

            fetch = {
                networkService.getConversionRates(BuildConfig.GRADLE_CURRENCY_API_KEY, baseCode)
            },

            saveFetchResult = { conversionRates ->
                databaseService.markAsFavoriteConversion(favoriteCode)
                when (conversionRates) {
                    is ApiResult.Success -> {
                        conversionRates
                            .data
                            .also {
                                databaseService.insertConversionRateMetaData(it.toMetaDataEntityModel())
                                databaseService.insertBulkConversionRates(it.toConversionRatesEntityModel())
                            }
                    }

                    is ApiResult.Error -> {

                    }

                    is ApiResult.Exception -> {

                    }
                }
            },

            shouldFetch = { conversionRates ->
                true
            }
        )

}
