package com.example.objectdetectusingtensorflow.repository

import android.graphics.Bitmap
import com.example.objectdetectusingtensorflow.data.Bird
import com.example.objectdetectusingtensorflow.ml.BirdModel
import com.example.objectdetectusingtensorflow.util.UiState
import org.tensorflow.lite.support.image.TensorImage
import javax.inject.Inject

class BirdRepoImpl @Inject constructor(private val birdModel: BirdModel) : BirdRepo {
    override fun runBirdClassification(data: Bird, result: (UiState<Pair<Any, String>>) -> Unit) {
        try {
            val newBitmap = data.image.copy(Bitmap.Config.ARGB_8888, true)
            val image = TensorImage.fromBitmap(newBitmap)

            // Runs model inference and gets result.
            val outputs = birdModel.process(image)
                .probabilityAsCategoryList.apply {
                    sortByDescending { it.score }
                }
            val probability = outputs[0]

            // Updates UI state with the result.
            result.invoke(
                UiState.Success(Pair(probability.label, "classificaion is success ..."))
            )
        } catch (e: Exception) {
            // Updates UI state to show error message.
            result.invoke(
                UiState.Failure("Error during bird classification: $e")
            )
        } finally {
            // Releases model resources if no longer used.
            birdModel.close()
        }
    }
}