package com.example.spotifai

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AudioPlayerActivity : AppCompatActivity() {

    private val audioResources = arrayOf(
        R.raw.azul, R.raw.valentin, R.raw.quepaso,
    )
    private var mediaPlayer: MediaPlayer? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio_player)

        val audioTitle = findViewById<TextView>(R.id.audioTitle)
        findViewById<ImageView>(R.id.imageViewAudio)
        val buttonPlay = findViewById<Button>(R.id.button_play)
        val buttonPause = findViewById<Button>(R.id.button_pause)
        val buttonStop = findViewById<Button>(R.id.button_stop)

        val selectedAudio = intent.getIntExtra("selectedAudio", 0)

        // Mostrar información del audio seleccionado
        audioTitle.text = "Reproduciendo: Audio ${selectedAudio + 1}"

        // Configurar la reproducción del audio
        mediaPlayer = MediaPlayer.create(this, audioResources[selectedAudio])

        buttonPlay.setOnClickListener {
            mediaPlayer?.start()
        }

        buttonPause.setOnClickListener {
            mediaPlayer?.pause()
        }

        buttonStop.setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer = MediaPlayer.create(this, audioResources[selectedAudio]) // Reiniciar audio
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Liberar recursos cuando la actividad sea destruida
        mediaPlayer = null
    }
}
