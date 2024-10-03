// Descripción: Reproductor de Música versión RecyclerView
// Autor: José C. Machaca
// Fecha creación: 26-09
// Fecha última modificación: 02-10

package com.example.spotifai

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Método que se ejecuta cuando se crea la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Establece el layout de la actividad principal

        // Verifica si el estado guardado es nulo, lo que significa que la actividad se está creando por primera vez
        if (savedInstanceState == null) {
            // Inicia una transacción de fragmentos y reemplaza el contenedor con el fragmento MainFragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment()) // Carga MainFragment en el contenedor
                .commit()
        }
    }

    // Función para navegar hacia el reproductor de música, pasando la posición seleccionada
    fun navigateToPlayer(position: Int) {
        // Crea una nueva instancia de PlayerFragment, pasando la posición como argumento
        val playerFragment = PlayerFragment.newInstance(position)

        // Inicia una transacción de fragmentos y reemplaza el fragmento actual con PlayerFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, playerFragment) // Reemplaza el fragmento actual por PlayerFragment
            .addToBackStack(null) // Añade la transacción a la pila de retroceso, para permitir volver atrás
            .commit()
    }
}