package com.example.objectdetectusingtensorflow.ui

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.objectdetectusingtensorflow.data.Bird
import com.example.objectdetectusingtensorflow.repository.BirdRepo
import com.example.objectdetectusingtensorflow.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: BirdRepo) : ViewModel() {
    private val _birdClaasification = MutableLiveData<UiState<Pair<Any, String>>>()
    val birdClassification: LiveData<UiState<Pair<Any, String>>>
            get() = _birdClaasification

    fun running(data: Bird) = viewModelScope.launch {
        repo.runBirdClassification(data){
            _birdClaasification.value = it
        }
    }


    private val _image = MutableLiveData<Bitmap>()
    val image: LiveData<Bitmap>
        get() = _image

    fun setImage(bitmap: Bitmap){
        _image.value = bitmap
    }
}