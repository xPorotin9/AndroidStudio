// Descripción: Reproductor de Música.
// Autor: José C. Machaca
// Fecha creación: 15-08
// Fecha última modificación: 18-09 : 16:25
package com.example.spotifai

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Lista de imágenes en la galería
    private val images = arrayOf(
        R.drawable.azul, R.drawable.valentin, R.drawable.quepaso,
        R.drawable.chamba, R.drawable.muytarde, R.drawable.colaps,
        R.drawable.prosor, R.drawable.partido, R.drawable.limeno,
        R.drawable.tongo
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al GridView
        val gridView = findViewById<GridView>(R.id.gridViewImages)

        // Adaptador personalizado para el GridView
        val adapter = ImageAdapter(this, images)
        gridView.adapter = adapter

        // Al hacer clic en una imagen, abrir raw.SecondActivity
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("image_id", images[position])
            startActivity(intent)
        }
    }
}


