package com.example.mybooks.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.mybooks.common.InjectorUtils
import com.example.mybooks.viewmodels.BooksViewModel
import com.example.mybooks.widgets.HomeTopAppBar
import com.example.mybooks.widgets.BookRow
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController = rememberNavController()
){
    val viewModel: BooksViewModel = viewModel(factory = InjectorUtils.provideBooksViewModelFactory(
        LocalContext.current))

    Scaffold(topBar = {
        HomeTopAppBar(
            title = "Home",
            menuContent = {
                DropdownMenuItem(onClick = { navController.navigate(Screen.AddBookScreen.route) }) {
                    Row {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Add Movie", modifier = Modifier.padding(4.dp))
                        Text(text = "Add Movie", modifier = Modifier
                            .width(100.dp)
                            .padding(4.dp))
                    }
                }
            }
        )
    }) { padding ->
        MainContent(
            modifier = Modifier.padding(padding),
            viewModel = viewModel
        )
    }
}

@Composable
fun MainContent(
    modifier: Modifier,
    viewModel: BooksViewModel
) {
    BookList(
        modifier = modifier,
        viewModel = viewModel
    )
}

@Composable
fun BookList(
    modifier: Modifier = Modifier,
    viewModel: BooksViewModel
) {
    val bookListState by viewModel.bookListState.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val items = bookListState

    LazyColumn {
        if (items.isEmpty()) {
            item {
                Text(
                    text = "THE LIST IS EMPTY!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                )
            }
        } else {
            items(items) { bookItem ->
                BookRow(book = bookItem, onReadClick = { book ->
                    coroutineScope.launch { viewModel.updateReadBooks(book) }
                })
            }
        }

    }
}