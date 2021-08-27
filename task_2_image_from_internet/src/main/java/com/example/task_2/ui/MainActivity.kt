package com.example.task_2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.task_2.R
import com.example.task_2.databinding.ActivityMainBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onClickDownloadListeners()

    }

    private fun onClickDownloadListeners() {
        val url = binding.etEnterImageUrl.text

        binding.btnDownloadImage.setOnClickListener {
            when {
                url.isEmpty() -> {
                    Toast.makeText(this,
                        getString(R.string.warning_url_empty),
                        Toast.LENGTH_SHORT).show()
                }
                !url.contains("http") -> {
                    Toast.makeText(this,
                        getString(R.string.warning_url_not_correct),
                        Toast.LENGTH_SHORT).show()
                }
                else -> {
                    loadImage(binding.etEnterImageUrl.text.toString())
                    url.clear()
                }
            }
        }
    }

    private fun loadImage(url: String) {
        binding.pbLoadingImage.visibility = View.VISIBLE
        Picasso.get().load(url).into(binding.ivImageResult,
            object : Callback {
            override fun onSuccess() {
            }
            override fun onError(e: Exception?) {
                Toast.makeText(this@MainActivity,
                    getString(R.string.warning_error_image_load),
                    Toast.LENGTH_SHORT).show()
            }
        })
        binding.pbLoadingImage.visibility = View.GONE
    }

}