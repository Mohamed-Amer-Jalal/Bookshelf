package com.example.bookshelf.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.bookshelf.BooShelfApplication
import com.example.bookshelf.data.BooksRepository
import com.example.bookshelf.model.BookItem
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class BooksViewModel(private val repository: BooksRepository) : ViewModel() {

    var booksUiState by mutableStateOf<List<BookItem>>(emptyList()) // لحفظ قائمة الكتب
        private set

    var bookUiState by mutableStateOf<BookItem?>(null) // لحفظ الكتاب المفرد
        private set

    fun searchBooks(query: String) {
        viewModelScope.launch {
            try {
                // جلب الكتب من الـ Repository
                booksUiState = repository.getBooks(query)
            } catch (e: IOException) {
                // يمكن التعامل مع الخطأ بشكل أفضل
                booksUiState = emptyList()  // أو يمكن إضافة حالة خطأ هنا
                e.printStackTrace()
            } catch (e: HttpException) {
                // التعامل مع الأخطاء من الـ API
                booksUiState = emptyList()  // أو حالة خطأ مخصصة
                e.printStackTrace()
            }
        }
    }

    // جلب كتاب بواسطة الـ id
    fun getBookById(id: String) {
        viewModelScope.launch {
            try {
                // استرجاع الكتاب بواسطة الـ id
                bookUiState = repository.getBookById(id)
            } catch (e: IOException) {
                bookUiState = null // أو يمكنك إضافة رسالة خطأ هنا
                e.printStackTrace()
            } catch (e: HttpException) {
                bookUiState = null // أو يمكنك إضافة رسالة خطأ هنا
                e.printStackTrace()
            }
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BooShelfApplication)
                val repository = application.container.boolRepository
                BooksViewModel(repository)
            }
        }
    }
}

