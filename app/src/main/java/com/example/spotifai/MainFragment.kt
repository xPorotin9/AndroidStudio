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
    private val images = arrayOf(
        R.drawable.azul, R.drawable.valentin, R.drawable.quepaso,
        R.drawable.chamba, R.drawable.muytarde, R.drawable.colaps,
        R.drawable.prosor, R.drawable.partido, R.drawable.limeno,
        R.drawable.tongo
    )

    private val songs = arrayOf(
        "Azul - Zoé", "Mi Valentín - William Luna", "Qué Paso - Papillón",
        "Reflexion en la Chamba - Banowsky", "Ya es muy Tarde - Smoky,Zinaloka",
        "Universal Collapse- DM DOKURO", "Cumbia de Smash - Justin Weaver",
        "Partido en Dos - La Unica Tropical", "Limeñito Rap - Gabs", "Numb - Tongo"
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gridView = view.findViewById<GridView>(R.id.gridViewImages)
        val adapter = ImageAdapter(requireContext(), images)
        gridView.adapter = adapter

        gridView.setOnItemClickListener { _, _, position, _ ->
            (activity as? MainActivity)?.navigateToPlayer(position)
        }

        val spinner = view.findViewById<Spinner>(R.id.spinner_songs)
        val adapterSongs = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, songs)
        adapterSongs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterSongs
    }
}