package com.example.bookshelf.data

import com.example.bookshelf.network.BooksApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val boolRepository: BooksRepository
}

class DefaultAppContainer : AppContainer {
    private val json = Json {
        ignoreUnknownKeys = true // تجاهل المفاتيح غير المعروفة
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/books/v1/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    private val retrofitService: BooksApiService by lazy {
        retrofit.create(BooksApiService::class.java)
    }

    override val boolRepository: BooksRepository by lazy {
        DefaultBooksRepository(retrofitService)
    }
}