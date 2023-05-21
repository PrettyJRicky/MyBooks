package com.example.mybooks.screens

sealed class AddBookUiEvent {
    object TitleChanged : AddBookUiEvent()
    object YearChanged : AddBookUiEvent()
    object AuthorChanged : AddBookUiEvent()
    object IsbnChanged : AddBookUiEvent()
    object submit : AddBookUiEvent()
}
