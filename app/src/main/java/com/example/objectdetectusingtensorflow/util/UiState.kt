package com.example.objectdetectusingtensorflow.util

sealed class UiState<out T>{
    data class Success<out T>(val value: T): UiState<T>()
    data class Failure(val message:  String): UiState<Nothing>()
}
