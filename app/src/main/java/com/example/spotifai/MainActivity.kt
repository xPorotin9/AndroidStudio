// Descripción: Reproductor de Música.
// Autor: José C. Machaca
// Fecha creación: 15-08
// Fecha última modificación: 18-09 : 16:25
package com.example.spotifai

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Lista de imágenes en la galería
    private val images = arrayOf(
        R.drawable.azul, R.drawable.valentin, R.drawable.quepaso,
        R.drawable.chamba, R.drawable.muytarde, R.drawable.colaps,
        R.drawable.prosor, R.drawable.partido, R.drawable.limeno,
        R.drawable.tongo
    )

    private val audios = arrayOf(
        R.raw.azul, R.raw.valentin, R.raw.quepaso,
        R.raw.chamba, R.raw.muytarde, R.raw.colaps,
        R.raw.prosor, R.raw.partido, R.raw.limeno,
        R.raw.tongo
    )

    private val songs = arrayOf(
        "Azul - Zoé", "Mi Valentín - William Luna", "Qué Paso - Papillón",
        "Reflexion en la Chamba - Banowsky", "Ya es muy Tarde - Smoky,Zinaloka",
        "Universal Collapse- DM DOKURO", "Cumbia de Smash - Justin Weaver",
        "Partido en Dos - La Unica Tropical", "Limeñito Rap - Gabs", "Numb - Tongo"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencia al GridView
        val gridView = findViewById<GridView>(R.id.gridViewImages)
        val adapter = ImageAdapter(this, images)
        gridView.adapter = adapter

        // Al hacer clic en una imagen, abrir SecondActivity
        gridView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra("image_id", images[position])
            intent.putExtra("audio_id", audios[position]) // Pasa el audio correspondiente
            intent.putExtra("song_title", songs[position]) // Pasa el título de la canción
            startActivity(intent)
        }

        // Configurar el Spinner
        val spinner = findViewById<Spinner>(R.id.spinner_songs)
        val adapterSongs = ArrayAdapter(this, android.R.layout.simple_spinner_item, songs)
        adapterSongs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterSongs
    }
}






