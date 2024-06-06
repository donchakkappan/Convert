package com.allutils.convert

import com.allutils.base.retrofit.ApiResultAdapterFactory
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import timber.log.Timber

val appModule = module {

    single {
        HttpLoggingInterceptor { message ->
            Timber.d("Http: $message")
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        val contentType = "application/json".toMediaType()

        val json = kotlinx.serialization.json.Json {
            // By default Kotlin serialization will serialize all of the keys present in JSON object and throw an
            // exception if given key is not present in the Kotlin class. This flag allows to ignore JSON fields
            ignoreUnknownKeys = true
        }

        @OptIn(ExperimentalSerializationApi::class)
        Retrofit.Builder()
            .baseUrl(BuildConfig.GRADLE_CURRENCY_API_BASE_U_R_L)
            .client(get())
            .addConverterFactory(json.asConverterFactory(contentType))
            .addCallAdapterFactory(ApiResultAdapterFactory())
            .build()
    }
}
