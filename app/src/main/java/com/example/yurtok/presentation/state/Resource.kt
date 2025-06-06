package com.example.yurtok.presentation.state


sealed class Resource<out T> {
    data class Success<out T>(val result: T): Resource<T>()
    data class Failure(val e: Exception): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}