// Descripción: Interfaz con un Spinner para seleccionar imágenes y un botón para navegar a otra actividad.
// Autor: José C. Machaca
// Fecha creación: 15-08
// Fecha última modificación: 15-09

package com.example.spotifai

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var spinner: Spinner
    private val imageNames = arrayOf("Imagen 1", "Imagen 2", "Imagen 3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spinner = findViewById(R.id.spinner)
        val buttonNext: Button = findViewById(R.id.button_next)

        // Poblar el spinner con los nombres de las imágenes
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, imageNames)
        spinner.adapter = adapter

        // Manejar el clic en el botón para ir a la segunda actividad
        buttonNext.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            // Pasar la imagen seleccionada a la segunda actividad
            intent.putExtra("selectedImage", spinner.selectedItemPosition)
            startActivity(intent)
        }

        // Restaurar selección del spinner si se gira la pantalla
        savedInstanceState?.let {
            spinner.setSelection(it.getInt("spinnerSelection"))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Guardar la selección del spinner
        outState.putInt("spinnerSelection", spinner.selectedItemPosition)
    }
}