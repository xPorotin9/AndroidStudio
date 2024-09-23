// Descripción: Interfaz con un Spinner para seleccionar imágenes y un botón para navegar a otra actividad.
// Autor: José C. Machaca
// Fecha creación: 15-08
// Fecha última modificación: 15-09

package com.example.spotifai

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val images = arrayOf(R.drawable.dogi, R.drawable.sublimeow, R.drawable.spinlog, R.drawable.bruh, R.drawable.calc, R.drawable.claz, R.drawable.door, R.drawable.fondo, R.drawable.plop)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gridView = findViewById<GridView>(R.id.gridViewImages)
        val adapter = ImageAdapter(this, images)
        gridView.adapter = adapter

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("image_id", images[position])
            startActivity(intent)
        }
    }
}
