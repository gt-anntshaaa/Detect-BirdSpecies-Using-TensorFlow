package com.example.objectdetectusingtensorflow.ui

import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.objectdetectusingtensorflow.data.Bird
import com.example.objectdetectusingtensorflow.databinding.ActivityMainBinding
import com.example.objectdetectusingtensorflow.ml.BirdModel
import com.example.objectdetectusingtensorflow.util.UiState
import com.example.objectdetectusingtensorflow.util.toast
import dagger.hilt.android.AndroidEntryPoint
import org.tensorflow.lite.support.image.TensorImage

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModels: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pickFromGallery = registerForActivityResult(ActivityResultContracts.GetContent()){uri ->
            if (uri != null){
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                viewModels.setImage(bitmap)
                viewModels.running(Bird("", bitmap))
//                outputGenerator(bitmap)
            }else{
                toast("Can't pick the image")
            }
        }

        viewModels.birdClassification.observe(this, Observer {
            when(it){
                is UiState.Success  -> {
                    binding.textView.text = it.value.first.toString()
                }
                is UiState.Failure -> {
                    toast("${it.message}")
                }
            }
        })


        viewModels.image.observe(this, Observer { bitmap ->
            binding.imageView.setImageBitmap(bitmap)
        })


        binding.loadImageBtn.setOnClickListener {
            pickFromGallery.launch("image/*")
        }
    }



}
