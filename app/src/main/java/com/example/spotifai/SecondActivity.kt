package com.example.spotifai

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val imageView: ImageView = findViewById(R.id.image_view)
        val buttonBack: Button = findViewById(R.id.button_back)

        // Obtener la imagen seleccionada desde la primera actividad
        val selectedImage = intent.getIntExtra("selectedImage", 0)
        val images = arrayOf(R.drawable.dogi, R.drawable.spinlog, R.drawable.sublimeow)

        // Mostrar la imagen seleccionada
        imageView.setImageResource(images[selectedImage])

        // Manejar el clic en el botón para volver
        buttonBack.setOnClickListener {
            // Volver a la actividad anterior
            finish()
        }
    }
}