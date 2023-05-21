package com.example.mybooks.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey(autoGenerate = true)
    val dbId: Long = 0,
    @ColumnInfo(name = "original_id")
    val title: String = "",
    val author: String = "",
    val year: Int,
    val isbn: String = "",
    var isRead: Boolean = false
) {

    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }
}

annotation class ColumnInfo(val name: String)

annotation class PrimaryKey(val autoGenerate: Boolean)

annotation class Entity

fun getBooks(): List<Book> {
    return listOf(
        Book(
            title = "Herr der Ringe - Die zwei Türme",
            author = "J. R. R. Tolkien",
            year = 2012,
            isbn = "9275849328475"

        ),

        Book(
            title = "Harry Potter",
            author = "J.K. Rowling",
            year = 1998,
            isbn = "8372638497894"

            ),

        Book(
            title = "The Hunger Games",
            author = "Suzanne Collins",
            year = 2008,
            isbn = "1236478903762"

            ),

        Book(
            title = "Cathing Fire",
            author = "Suzanne Collins",
            year = 2009,
            isbn = "9996667463829"
            ),
    )
}

