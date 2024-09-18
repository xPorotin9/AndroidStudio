package com.example.spotifai

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private val images = arrayOf(R.drawable.dogi, R.drawable.sublimeow, R.drawable.spinlog, R.drawable.bruh, R.drawable.calc, R.drawable.claz, R.drawable.door, R.drawable.fondo, R.drawable.plop)
    private var currentIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val btnPrevious = findViewById<Button>(R.id.btnPrevious)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnBack = findViewById<Button>(R.id.btnBack)

        val selectedImage = intent.getIntExtra("image_id", 0)
        currentIndex = images.indexOf(selectedImage)
        imageView.setImageResource(images[currentIndex])

        btnPrevious.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                imageView.setImageResource(images[currentIndex])
            }
        }

        btnNext.setOnClickListener {
            if (currentIndex < images.size - 1) {
                currentIndex++
                imageView.setImageResource(images[currentIndex])
            }
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
