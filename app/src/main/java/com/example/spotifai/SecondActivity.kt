package com.example.spotifai

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SecondActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnPlayPause = findViewById<Button>(R.id.btnPlayPause)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val tvElapsedTime = findViewById<TextView>(R.id.tvElapsedTime)
        val tvTotalTime = findViewById<TextView>(R.id.tvTotalTime)
        val tvSongTitle = findViewById<TextView>(R.id.tvSongTitle)

        // Obtener la imagen, el audio y el título de la canción de la Intent
        val selectedImage = intent.getIntExtra("image_id", 0)
        val selectedAudio = intent.getIntExtra("audio_id", 0)
        val selectedTitle = intent.getStringExtra("song_title") ?: ""

        // Establecer la imagen y el título de la canción
        imageView.setImageResource(selectedImage)
        tvSongTitle.text = selectedTitle

        // Reproducir el audio
        playAudio(selectedAudio, seekBar, tvElapsedTime, tvTotalTime)

        btnBack.setOnClickListener {
            stopAudio()
            finish()
        }

        btnPlayPause.setOnClickListener {
            if (isPlaying) {
                pauseAudio()
                btnPlayPause.setBackgroundResource(R.drawable.play_icon)
            } else {
                resumeAudio()
                btnPlayPause.setBackgroundResource(R.drawable.pause_icon)
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun playAudio(audioResource: Int, seekBar: SeekBar, tvElapsedTime: TextView, tvTotalTime: TextView) {
        stopAudio() // Detener cualquier audio que esté reproduciéndose
        mediaPlayer = MediaPlayer.create(this, audioResource)
        mediaPlayer?.start()
        isPlaying = true

        seekBar.max = mediaPlayer?.duration ?: 0
        tvTotalTime.text = formatTime(mediaPlayer?.duration ?: 0)

        updateSeekBar(seekBar, tvElapsedTime)

        mediaPlayer?.setOnCompletionListener {
            isPlaying = false
            findViewById<Button>(R.id.btnPlayPause).setBackgroundResource(R.drawable.play_icon)
        }
    }

    private fun formatTime(milliseconds: Int): String {
        val seconds = (milliseconds / 1000) % 60
        val minutes = (milliseconds / 1000) / 60
        return String.format("%02d:%02d", minutes, seconds)
    }

    private fun updateSeekBar(seekBar: SeekBar, tvElapsedTime: TextView) {
        mediaPlayer?.let {
            seekBar.progress = it.currentPosition
            tvElapsedTime.text = formatTime(it.currentPosition)

            if (isPlaying) {
                handler.postDelayed({ updateSeekBar(seekBar, tvElapsedTime) }, 1000)
            }
        }
    }

    private fun pauseAudio() {
        mediaPlayer?.pause()
        isPlaying = false
    }

    private fun resumeAudio() {
        mediaPlayer?.start()
        isPlaying = true
    }

    private fun stopAudio() {
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false
    }
}
