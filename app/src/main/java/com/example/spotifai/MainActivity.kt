// Descripción: Interfaz con un Spinner para seleccionar imágenes y un botón para navegar a otra actividad.
// Autor: José C. Machaca
// Fecha creación: 21-08
// Fecha última modificación: 22-09

package com.example.spotifai

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, ImageGridFragment())
                .commit()
        }
    }
}
