package com.example.spotifai

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment

class PlayerFragment : Fragment() {
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false // Booleano que indica si el audio está en reproducción
    private val handler = Handler(Looper.getMainLooper()) // Manejador para actualizar la barra de progreso

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

    // Array de archivos de audio mp3
    private val audios = arrayOf(
        R.raw.azul,
        R.raw.valentin,
        R.raw.quepaso,
        R.raw.chamba,
        R.raw.muytarde,
        R.raw.colaps,
        R.raw.prosor,
        R.raw.partido,
        R.raw.limeno,
        R.raw.tongo
    )

    // Array de nombres de canciones
    private val songs = arrayOf(
        "Azul - Zoé",
        "Mi Valentín - William Luna",
        "Qué Paso - Papillón",
        "Reflexion en la Chamba - Banowsky",
        "Ya es muy Tarde - Smoky, Zinaloka",
        "Universal Collapse - DM DOKURO",
        "Cumbia de Smash - Justin Weaver",
        "Partido en Dos - La Unica Tropical",
        "Limeñito Rap - Gabs",
        "Numb - Tongo"
    )

    // Método para crear una nueva instancia del fragmento con la posición seleccionada
    companion object {
        private const val ARG_POSITION = "position"

        fun newInstance(position: Int): PlayerFragment {
            val fragment = PlayerFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position) // Guarda la posición seleccionada en los argumentos
            fragment.arguments = args
            return fragment
        }
    }

    // Método para inflar el layout del fragmento
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    // Se ejecuta una vez que la vista ha sido creada
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(ARG_POSITION, 0) ?: 0 // Obtiene la posición seleccionada

        // Inicializa los elementos de la UI
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val btnBack = view.findViewById<Button>(R.id.btnBack)
        val btnPlayPause = view.findViewById<Button>(R.id.btnPlayPause)
        val seekBar = view.findViewById<SeekBar>(R.id.seekBar)
        val tvElapsedTime = view.findViewById<TextView>(R.id.tvElapsedTime)
        val tvTotalTime = view.findViewById<TextView>(R.id.tvTotalTime)
        val tvSongTitle = view.findViewById<TextView>(R.id.tvSongTitle)

        imageView.setImageResource(images[position]) // Establece la portada de la canción
        tvSongTitle.text = songs[position] // Establece el título de la canción

        // Inicia la reproducción del audio seleccionado
        playAudio(audios[position], seekBar, tvElapsedTime, tvTotalTime)

        // Configura el botón 'Volver' para detener el audio y regresar al fragmento anterior
        btnBack.setOnClickListener {
            stopAudio() // Detiene el audio
            activity?.supportFragmentManager?.popBackStack() // Vuelve al fragmento anterior
        }

        // Configura el botón de 'Play/Pause'
        btnPlayPause.setOnClickListener {
            if (isPlaying) {
                pauseAudio() // Pausa el audio
                btnPlayPause.setBackgroundResource(R.drawable.play_icon) // Cambia el ícono a 'Play'
            } else {
                resumeAudio() // Reanuda el audio
                btnPlayPause.setBackgroundResource(R.drawable.pause_icon) // Cambia el ícono a 'Pause'
            }
        }

        // Listener para la barra de progreso del audio
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress) // Mueve el audio a la posición seleccionada por el usuario
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    // Método para reproducir el audio
    private fun playAudio(audioResource: Int, seekBar: SeekBar, tvElapsedTime: TextView, tvTotalTime: TextView) {
        stopAudio() // Detiene cualquier audio en reproducción anterior
        mediaPlayer = MediaPlayer.create(requireContext(), audioResource) // Crea el MediaPlayer
        mediaPlayer?.start() // Inicia la reproducción
        isPlaying = true // Marca el audio como reproduciéndose

        seekBar.max = mediaPlayer?.duration ?: 0 // Establece el máximo de la barra de progreso
        tvTotalTime.text = formatTime(mediaPlayer?.duration ?: 0) // Muestra el tiempo total del audio

        updateSeekBar(seekBar, tvElapsedTime) // Actualiza la barra de progreso

        mediaPlayer?.setOnCompletionListener {
            isPlaying = false // Marca como no reproduciendo al finalizar el audio
            view?.findViewById<Button>(R.id.btnPlayPause)?.setBackgroundResource(R.drawable.play_icon) // Cambia el ícono a 'Play'
        }
    }

    // Método para formatear el tiempo en minutos y segundos
    private fun formatTime(milliseconds: Int): String {
        val seconds = (milliseconds / 1000) % 60
        val minutes = (milliseconds / 1000) / 60
        return String.format("%02d:%02d", minutes, seconds) // Devuelve el formato mm:ss
    }

    // Método para actualizar la barra de progreso y el tiempo transcurrido
    private fun updateSeekBar(seekBar: SeekBar, tvElapsedTime: TextView) {
        mediaPlayer?.let {
            seekBar.progress = it.currentPosition // Actualiza el progreso de la barra
            tvElapsedTime.text = formatTime(it.currentPosition) // Actualiza el tiempo transcurrido

            if (isPlaying) {
                handler.postDelayed({ updateSeekBar(seekBar, tvElapsedTime) }, 1000) // Actualiza cada segundo
            }
        }
    }

    // Método para pausar el audio
    private fun pauseAudio() {
        mediaPlayer?.pause() // Pausa el MediaPlayer
        isPlaying = false // Marca como no reproduciendo
    }

    // Método para reanudar el audio
    private fun resumeAudio() {
        mediaPlayer?.start() // Reanuda el MediaPlayer
        isPlaying = true // Marca como reproduciendo
    }

    // Método para detener y liberar el MediaPlayer
    private fun stopAudio() {
        mediaPlayer?.release() // Libera el MediaPlayer
        mediaPlayer = null
        isPlaying = false // Marca como no reproduciendo
    }

    // Detiene el audio cuando el fragmento es destruido
    override fun onDestroy() {
        super.onDestroy()
        stopAudio() // Detiene cualquier reproducción de audio activa
    }
}
