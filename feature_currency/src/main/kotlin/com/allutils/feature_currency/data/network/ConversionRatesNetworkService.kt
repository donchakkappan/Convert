package com.allutils.feature_currency.data.network

import com.allutils.base.retrofit.ApiResult
import com.allutils.feature_currency.data.network.models.response.ConversionRateResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ConversionRatesNetworkService {

    @GET("{api_key}/latest/{base_code}")
    suspend fun getConversionRates(
        @Path("api_key") apiKey: String,
        @Path("base_code") baseCode: String
    ): ApiResult<ConversionRateResponse>

}
