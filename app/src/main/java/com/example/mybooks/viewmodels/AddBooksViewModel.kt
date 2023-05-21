package com.example.mybooks.viewmodels


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.mybooks.common.Validator
import com.example.mybooks.repositories.BookRepository
import com.example.mybooks.screens.*


class AddBooksViewModel(private val repository: BookRepository): ViewModel() {

    var bookUiState by mutableStateOf(AddBookUiState())
        private set

    fun updateUIState(newBookUiState: AddBookUiState, event: AddBookUiEvent){
        var state = AddBookUiState() // this is needed because copy always creates a new instance

        when (event) {
            is AddBookUiEvent.TitleChanged -> {
                val titleResult = Validator.validateBookTitle(newBookUiState.title)
                state = if(!titleResult.successful) newBookUiState.copy(titleErr = true) else newBookUiState.copy(titleErr = false)
            }
            is AddBookUiEvent.YearChanged -> {
                val yearResult = Validator.validateBookYear(newBookUiState.year)
                state = if(!yearResult.successful) newBookUiState.copy(yearErr = true) else newBookUiState.copy(yearErr = false)
            }

            is AddBookUiEvent.AuthorChanged -> {
                val directorResult = Validator.validateBookAuthor(newBookUiState.author)
                state = if(!directorResult.successful) newBookUiState.copy(authorErr = true) else newBookUiState.copy(authorErr = false)
            }

            is AddBookUiEvent.IsbnChanged -> {
                val actorsResult = Validator.validateBookIsbn(newBookUiState.isbn)
                state = if(!actorsResult.successful) newBookUiState.copy(isbnErr = true) else newBookUiState.copy(isbnErr = false)
            }


            else -> {}
        }

        bookUiState = state.copy(actionEnabled = !newBookUiState.hasError())
    }




    suspend fun saveBook() {
        val book = bookUiState.toBook()

        repository.addBook(book)
    }
    suspend fun removeBook(){
        //repository.deleteBook(book = Book())
    }

}