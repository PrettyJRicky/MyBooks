package com.example.mybooks.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.mybooks.R
import com.example.mybooks.common.InjectorUtils
import com.example.mybooks.viewmodels.AddBooksViewModel
import com.example.mybooks.widgets.SimpleTextField
import com.example.mybooks.widgets.SimpleTopAppBar
import kotlinx.coroutines.launch

@Composable
fun AddBookScreen(
    navController: NavController
){
    val viewModel: ViewModel = viewModel(factory = InjectorUtils.provideAddBooksViewModelFactory(
        LocalContext.current))
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            SimpleTopAppBar(arrowBackClicked = { navController.popBackStack() }) {
                Text(text = stringResource(id = R.string.add_book))
            }
        },
    ) { padding ->
        MainContent(
            Modifier.padding(padding),
            booksViewModel = viewModel as AddBooksViewModel,
            navController = navController
        )
    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    booksViewModel: AddBooksViewModel,
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(10.dp)
    ) {

        BookBody(
            bookUiState = booksViewModel.bookUiState,
            onBookValueChange = { newUiState, event -> booksViewModel.updateUIState(newUiState, event)},
            onSaveClick = {
                coroutineScope.launch {
                    booksViewModel.saveBook()
                    navController.navigate(Screen.MainScreen.route)
                }
            }
        )
    }
}

@Composable
fun BookBody(
    bookUiState: AddBookUiState,
    onBookValueChange: (AddBookUiState, AddBookUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        BookInputForm(bookUiState = bookUiState, onBookValueChange = onBookValueChange)

        Button(
            enabled = bookUiState.actionEnabled,
            onClick = onSaveClick) {
            Text(text = stringResource(R.string.add))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BookInputForm(
    bookUiState: AddBookUiState,
    onBookValueChange: (AddBookUiState, AddBookUiEvent) -> Unit,
) {
    SimpleTextField(
        value = bookUiState.title,
        label = stringResource(R.string.enter_book_title),
        isError = bookUiState.titleErr,
        errMsg = stringResource(id = R.string.title_required)
    ) { input ->
        onBookValueChange(bookUiState.copy(title = input), AddBookUiEvent.TitleChanged)
    }

    SimpleTextField(
        value = bookUiState.year.toString(),
        label = stringResource(id = R.string.enter_book_year),
        errMsg = stringResource(id = R.string.year_required),
        isError = bookUiState.yearErr
    ) { input ->
        onBookValueChange(bookUiState.copy(year = input.toInt()), AddBookUiEvent.YearChanged)
    }


    SimpleTextField(
        value = bookUiState.author,
        label = stringResource(id = R.string.enter_author),
        errMsg = stringResource(id = R.string.year_required),
        isError = bookUiState.yearErr
    ) { input ->
        onBookValueChange(bookUiState.copy(author = input), AddBookUiEvent.AuthorChanged)
    }


    SimpleTextField(
        value = bookUiState.isbn,
        label = stringResource(id = R.string.enter_isbn),
        errMsg = stringResource(id = R.string.year_required),
        isError = bookUiState.yearErr
    ){ input ->
            onBookValueChange(bookUiState.copy(isbn = input), AddBookUiEvent.IsbnChanged) }
}