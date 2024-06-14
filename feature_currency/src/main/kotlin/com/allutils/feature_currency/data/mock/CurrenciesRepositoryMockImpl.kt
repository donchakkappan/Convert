package com.allutils.feature_currency.data

import android.content.Context
import com.allutils.base.result.Result
import com.allutils.feature_currency.data.network.models.response.ConversionRateResponse
import com.allutils.feature_currency.data.network.models.response.toDomainModel
import com.allutils.feature_currency.domain.CurrenciesRepository
import com.allutils.feature_currency.domain.models.output.ConversionRatesOutput
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

internal class CurrenciesRepositoryMockImpl(private val context: Context) : CurrenciesRepository {

    override suspend fun getConversionRates(
        baseCode: String
    ): Result<List<ConversionRatesOutput>> {
        val json = readJsonFromAssets(context, "conversions.json")
        val conversionRates = Gson().fromJson(json, ConversionRateResponse::class.java)
        return Result.Success(conversionRates.toDomainModel())
    }

    private fun readJsonFromAssets(context: Context, fileName: String): String {
        val assetManager = context.assets
        val inputStream = assetManager.open(fileName)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        return bufferedReader.use { it.readText() }
    }

}
