package com.example.mybooks.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mybooks.models.Book
import com.example.mybooks.models.getBooks
import com.example.mybooks.ui.theme.Shapes

@Preview
@Composable
fun BookRow(
    book: Book = getBooks()[0],
    modifier: Modifier = Modifier,
    onBookRowClick: (String) -> Unit = {},
    onReadClick: (Book) -> Unit = {}
) {
    Card(modifier = modifier
        .clickable {
            onBookRowClick(book.dbId.toString())
        }
        .fillMaxWidth()
        .padding(5.dp),
        shape = Shapes.large,
        elevation = 10.dp
    ) {
        Column {
            Box(modifier = Modifier
                .height(150.dp)
                .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                ReadIcon(book, onReadClick)
            }

            BookDetails(modifier = Modifier.padding(12.dp), book = book)
        }
    }
}

@Composable
fun ReadIcon(book: Book, onReadClick: (Book) -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp),
        contentAlignment = Alignment.TopEnd
    ){
        Icon(modifier = Modifier.clickable { onReadClick(book) },
            tint = MaterialTheme.colors.secondary,
            imageVector =
            if( book.isRead ) {
                Icons.Default.Check
            } else {
                Icons.Default.CheckCircle
            },
            contentDescription = "Book has been read"
        )
    }
}


@Composable
fun BookDetails(modifier: Modifier = Modifier, book: Book) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            book.title,
            modifier = Modifier.weight(6f),
            style = MaterialTheme.typography.h6
        )

        Text(
            "Author: " + book.author,
            modifier = Modifier.weight(6f),
            style = MaterialTheme.typography.h6
        )

        IconButton(
            modifier = Modifier.weight(1f),
            onClick = { expanded = !expanded }) {
            Icon(imageVector =
            if (expanded) Icons.Filled.KeyboardArrowDown
            else Icons.Filled.KeyboardArrowUp,
                contentDescription = "expand",
                modifier = Modifier
                    .size(25.dp),
                tint = Color.DarkGray
            )
        }
    }

    AnimatedVisibility(
        visible = expanded,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column (modifier = modifier) {
            Text(text = "Released: ${book.year}", style = MaterialTheme.typography.caption)
            val newValue = "${book.isbn[0]}${book.isbn[1]}${book.isbn[2]}${book.isbn[3]}${book.isbn[4]}${book.isbn[5]}${book.isbn[6]}${book.isbn[7]}${book.isbn[8]}${book.isbn[9]}"
            Text(text = "ISBN: $newValue", style = MaterialTheme.typography.caption)

            Divider(modifier = Modifier.padding(3.dp))

        }
    }
}
