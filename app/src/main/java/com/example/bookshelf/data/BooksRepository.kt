package com.example.bookshelf.data

import com.example.bookshelf.model.BookItem
import com.example.bookshelf.network.BooksApiService

interface BooksRepository {
    suspend fun getBooks(query: String): List<BookItem>
    suspend fun getBookById(id: String): BookItem
}

class DefaultBooksRepository(
    private val apiService: BooksApiService
) : BooksRepository {
    override suspend fun getBooks(query: String): List<BookItem> {
        return apiService.searchBooks(query).items
    }

    override suspend fun getBookById(id: String): BookItem {
        return apiService.getBookById(id)
    }
}