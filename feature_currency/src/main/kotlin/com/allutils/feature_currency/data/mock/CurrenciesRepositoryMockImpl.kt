package com.allutils.feature_currency.data

import android.content.Context
import com.allutils.feature_currency.data.network.models.response.ConversionRateResponse
import com.allutils.feature_currency.domain.ICurrenciesRepository
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.allutils.feature_currency.utils.Resource
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.BufferedReader
import java.io.InputStreamReader

internal class CurrenciesRepositoryMockImpl(private val context: Context) : ICurrenciesRepository {

    override suspend fun getConversionRates(
        baseCode: String
    ): Flow<Resource<List<ConversionRatesOutput>>> {
        val json = readJsonFromAssets(context, "conversions.json")
        val conversionRates = Gson().fromJson(json, ConversionRateResponse::class.java)
        return flow {}
    }

    override suspend fun getFavoriteConversionRates(baseCode: String): Flow<Resource<List<ConversionRatesOutput>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocalConversionRate(baseCode: String, localCurrencyCode: String): Flow<Resource<List<ConversionRatesOutput>>> {
        TODO("Not yet implemented")
    }

    override suspend fun isThereAnyFavorite(): Boolean {
        TODO("Not yet implemented")
    }

    private fun readJsonFromAssets(context: Context, fileName: String): String {
        val assetManager = context.assets
        val inputStream = assetManager.open(fileName)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        return bufferedReader.use { it.readText() }
    }

}
