package com.example.objectdetectusingtensorflow.repository

import com.example.objectdetectusingtensorflow.data.Bird
import com.example.objectdetectusingtensorflow.util.UiState

interface BirdRepo {
    fun runBirdClassification(data: Bird, result: (UiState<Pair<Any,String>>) -> Unit)
}