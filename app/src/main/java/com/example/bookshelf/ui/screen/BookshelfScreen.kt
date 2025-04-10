package com.example.bookshelf.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.bookshelf.model.BookItem

@Composable
fun BookshelfScreen(viewModel: BooksViewModel = viewModel(factory = BooksViewModel.factory)) {
    val books = viewModel.booksUiState
    val bookState = viewModel.bookUiState

    // البحث عند أول تحميل الشاشة
    LaunchedEffect(Unit) {
        viewModel.searchBooks("android") // مثال بحث
    }

    // حالة الكتاب المفرد (عند الضغط على الكتاب)
    if (bookState != null) {
        // عرض تفاصيل الكتاب إذا كانت موجودة
        BookDetailScreen(bookState)
    } else {
        // عرض قائمة الكتب إذا كانت موجودة
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(books) { book ->
                BookItemCard(book, onClick = {
                    // عند الضغط على كتاب، جلب تفاصيله
                    viewModel.getBookById(book.id)
                })
            }
        }
    }
}

@Composable
fun BookItemCard(book: BookItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            val imageUrl = book.volumeInfo.imageLinks?.thumbnail?.replace("http", "https")
            if (imageUrl != null) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = book.volumeInfo.title,
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                )
            }
            Text(
                text = book.volumeInfo.title,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun BookDetailScreen(book: BookItem) {
    // عرض تفاصيل الكتاب المفصل
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        val imageUrl = book.volumeInfo.imageLinks?.large?.replace("http", "https")
        if (imageUrl != null) {
            AsyncImage(
                model = imageUrl,
                contentDescription = book.volumeInfo.title,
                modifier = Modifier
                    .height(300.dp)
                    .fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = book.volumeInfo.title,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Author(s): ${book.volumeInfo.authors.joinToString(", ")}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = book.volumeInfo.description ?: "No description available.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
