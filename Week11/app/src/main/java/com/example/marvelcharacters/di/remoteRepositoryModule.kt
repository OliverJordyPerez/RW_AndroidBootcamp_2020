package com.example.marvelcharacters.di

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.marvelcharacters.BuildConfig
import com.example.marvelcharacters.app.App
import com.example.marvelcharacters.repository.remote.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val remoteRepositoryModule = module {

    // BASE_URL
    single(named("BASE_URL")) {
        "https://gateway.marvel.com"
    }

    // PeriodicWorkRequest constraints
    single {
        Constraints.Builder()
            .setRequiredNetworkType(NetworkType.NOT_ROAMING)
            .setRequiresBatteryNotLow(true)
            .setRequiresStorageNotLow(true)
            .build()
    }

    // Remote Api
    single {
        RemoteApi(get())
    }

    // PeriodicWorkRequestBuilder
    single {
        PeriodicWorkRequestBuilder<RemoteApiWorker>(15, TimeUnit.MINUTES)
            .setConstraints(get())
            .build()
    }

    // Build retrofit
    single {
        val contentType = "application/json".toMediaType()
        Retrofit.Builder()
            .client(get())
            .baseUrl(get<String>(named("BASE_URL")))
            .addConverterFactory(Json.nonstrict.asConverterFactory(contentType))
            .build()
            .create(RemoteDataSource::class.java)
    }

    // Interceptor
    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    // Build OkHttpClient
    single {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            client.addInterceptor(get<HttpLoggingInterceptor>())
        }
        client.addInterceptor(get<HttpLoggingInterceptor>())
        client.build()
    }

}