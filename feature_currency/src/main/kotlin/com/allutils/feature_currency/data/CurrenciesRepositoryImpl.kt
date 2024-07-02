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
import kotlinx.coroutines.runBlocking
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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

        shouldFetch = {
            runBlocking { it.isEmpty() || isLocalDataOld(baseCode) }
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
                    databaseService.markAsFavoriteConversion(favoriteCode)
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

            shouldFetch = {
                runBlocking { isLocalDataOld(baseCode) }
            }
        )

    override suspend fun unmarkFavoriteAndGetAll(baseCode: String, favoriteCode: String): Flow<Resource<List<ConversionRatesOutput>>> =
        flow {
            databaseService.unmarkAsFavoriteConversion(favoriteCode)
            val localConversionRates = databaseService
                .getFavoriteConversionRates(baseCode = baseCode)
                .map { it.toDomainModel() }

            emit(Resource.Success(localConversionRates))
        }

    override suspend fun getLastUpdatedTime(baseCode: String): String {
        return databaseService.getLastUpdatedTime(baseCode).toFormattedLocalDateTime()
    }

    private suspend fun isLocalDataOld(baseCode: String): Boolean {
        return databaseService.getLastUpdatedTime(baseCode).isOlderThanADay()
    }

    private fun Long.toFormattedLocalDateTime(pattern: String = "EEE, dd MMM yyyy HH:mm:ss"): String {
        val instant = Instant.ofEpochSecond(this)
        val zonedDateTime = instant.atZone(ZoneId.systemDefault())
        val formatter = DateTimeFormatter.ofPattern(pattern)
        return zonedDateTime.format(formatter)
    }

    private fun Long.isOlderThanADay(): Boolean {
        val currentInstant = Instant.now()
        val oneDayAgoInstant = currentInstant.minusSeconds(24 * 60 * 60)

        return this < oneDayAgoInstant.epochSecond
    }

}
