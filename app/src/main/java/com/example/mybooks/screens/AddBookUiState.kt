package com.example.mybooks.screens

import com.example.mybooks.models.Book

data class AddBookUiState(
    val title: String = "",
    val year: Int = 0,
    val author: String = "",
    val isbn: String = "",

    var titleErr: Boolean = false,
    val yearErr: Boolean = false,
    val authorErr: Boolean = false,
    val isbnErr: Boolean = false,

    val actionEnabled: Boolean = false
    )

fun AddBookUiState.toBook(): Book = Book(
    title = title,
    year = year,
    author = author,
    isbn = isbn,
)

fun AddBookUiState.toBookUiState(actionEnabled: Boolean): AddBookUiState = AddBookUiState(
    title = title,
    year = year,
    author = author,
    isbn = isbn,
    actionEnabled = actionEnabled
)

fun AddBookUiState.hasError() : Boolean {
    val titleResult = com.example.mybooks.common.Validator.validateBookTitle(title)
    val yearResult = com.example.mybooks.common.Validator.validateBookYear(year)
    val authorResult = com.example.mybooks.common.Validator.validateBookAuthor(author)
    val isbnResult = com.example.mybooks.common.Validator.validateBookIsbn(isbn)

    return listOf(
        titleResult,
        yearResult,
        authorResult,
        isbnResult,

        ).any {
        !it.successful
    }
}