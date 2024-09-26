package com.example.spotifai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.Spinner
import androidx.fragment.app.Fragment

class MainFragment : Fragment() {

    // Array de portadas de canciones
    private val images = arrayOf(
        R.drawable.azul,
        R.drawable.valentin,
        R.drawable.quepaso,
        R.drawable.chamba,
        R.drawable.muytarde,
        R.drawable.colaps,
        R.drawable.prosor,
        R.drawable.partido,
        R.drawable.limeno,
        R.drawable.tongo
    )

    // Array de nombres de canciónes
    private val songs = arrayOf(
        "Azul - Zoé",
        "Mi Valentín - William Luna",
        "Qué Paso - Papillón",
        "Reflexion en la Chamba - Banowsky",
        "Ya es muy Tarde - Smoky,Zinaloka",
        "Universal Collapse - DM DOKURO",
        "Cumbia de Smash - Justin Weaver",
        "Partido en Dos - La Unica Tropical",
        "Limeñito Rap - Gabs",
        "Numb - Tongo"
    )

    // Método que infla el layout para el fragmento
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Infla el layout 'fragment_main' para este fragmento
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    // Método que se llama cuando la vista ha sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura el GridView para mostrar las imágenes
        val gridView = view.findViewById<GridView>(R.id.gridViewImages)
        val adapter = ImageAdapter(requireContext(), images) // Usa un adaptador personalizado para las imágenes
        gridView.adapter = adapter

        // Establece un listener para manejar clics en los elementos del GridView
        gridView.setOnItemClickListener { _, _, position, _ ->
            // Llama al método 'navigateToPlayer' de MainActivity, pasando la posición de la imagen seleccionada
            (activity as? MainActivity)?.navigateToPlayer(position)
        }

        // Configura el Spinner para mostrar las canciones
        val spinner = view.findViewById<Spinner>(R.id.spinner_songs)
        val adapterSongs = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, songs) // Crea un adaptador para el Spinner
        adapterSongs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // Define el layout para la lista desplegable
        spinner.adapter = adapterSongs // Asigna el adaptador al Spinner
    }
}
