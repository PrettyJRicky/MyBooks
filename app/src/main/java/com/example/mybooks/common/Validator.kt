package com.example.mybooks.common

object Validator {

    fun validateBookTitle(title: String): ValidationResult {
        return ValidationResult(title.isNotBlank())
    }

    fun validateBookYear(year: Int): ValidationResult {
        val result = year.toString().isNotBlank()
        return ValidationResult(result)
    }


    fun validateBookAuthor(author: String): ValidationResult {
        return ValidationResult(author.isNotBlank())
    }

    fun validateBookIsbn(isbn: String): ValidationResult {
        return ValidationResult(isbn.isNotBlank())
    }

}

data class ValidationResult(
    val successful: Boolean
)