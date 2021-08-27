package com.example.task_2.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.task_2.R
import com.example.task_2.databinding.ActivityMainBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClickDownloadListeners()

    }

    private fun onClickDownloadListeners() {
        val imageUrl = binding.etEnterImageUrl.text

        binding.btnDownloadImage.setOnClickListener {
            when {
                imageUrl.isEmpty() -> {
                    Toast.makeText(
                        this,
                        getString(R.string.warning_url_empty),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                !imageUrl.contains("http") -> {
                    Toast.makeText(
                        this,
                        getString(R.string.warning_url_not_correct),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    loadImage(imageUrl.toString())
                    imageUrl.clear()
                }
            }
        }
    }

    private fun loadImage(imageUrl: String) {
        binding.pbLoadingImage.isVisible = true
        thread(start = true) {
            val uiHandler = Handler(Looper.getMainLooper())
            val url = URL(imageUrl)
            val urlConnection = url.openConnection() as HttpURLConnection
            try {
                val bitmap = BitmapFactory.decodeStream(urlConnection.inputStream)
                uiHandler.post {
                    binding.ivImageResult.setImageBitmap(bitmap)
                }
            } catch (e: Exception) {
                uiHandler.post {
                    Toast.makeText(
                        this,
                        R.string.warning_error_image_load,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } finally {
                urlConnection.disconnect()
            }
        }
        binding.pbLoadingImage.isGone = true
    }

}