package com.example.spotifai

import android.annotation.SuppressLint
import android.widget.TextView
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity


class SecondActivity : AppCompatActivity() {

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
    private val songTitles = arrayOf(
        "Azul - Zoé", "Mi Valentín - William Luna", "Qué Paso - Papillón",
        "Reflexion en la Chamba - Banowsky", "Ya es muy Tarde - Smoky,Zinaloka", "Universal Collapse- DM DOKURO",
        "Cumbia de Smash - Justin Weaver", "Partido en Dos - La Unica Tropical", "Limeñito Rap - Gabs",
        "Numb - Tongo"
    )

    private var currentIndex = 0
    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val btnPrevious = findViewById<Button>(R.id.btnPrevious)
        val btnNext = findViewById<Button>(R.id.btnNext)
        val btnBack = findViewById<Button>(R.id.btnBack)
        val btnPlayPause = findViewById<Button>(R.id.btnPlayPause)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val tvElapsedTime = findViewById<TextView>(R.id.tvElapsedTime)
        val tvTotalTime = findViewById<TextView>(R.id.tvTotalTime)
        val tvSongTitle = findViewById<TextView>(R.id.tvSongTitle)

        val selectedImage = intent.getIntExtra("image_id", 0)
        currentIndex = images.indexOf(selectedImage)
        imageView.setImageResource(images[currentIndex])
        tvSongTitle.text = songTitles[currentIndex] // Actualiza el TextView con el nombre de la canción

        playAudio(currentIndex, seekBar, tvElapsedTime, tvTotalTime)

        btnBack.setOnClickListener {
            stopAudio()
            finish()
        }

        btnPrevious.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                imageView.setImageResource(images[currentIndex])
                tvSongTitle.text = songTitles[currentIndex] // Actualiza el TextView con el nombre de la canción
                playAudio(currentIndex, seekBar, tvElapsedTime, tvTotalTime)
            }
        }

        btnNext.setOnClickListener {
            if (currentIndex < images.size - 1) {
                currentIndex++
                imageView.setImageResource(images[currentIndex])
                tvSongTitle.text = songTitles[currentIndex] // Actualiza el TextView con el nombre de la canción
                playAudio(currentIndex, seekBar, tvElapsedTime, tvTotalTime)
            }
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

    private fun playAudio(index: Int, seekBar: SeekBar, tvElapsedTime: TextView, tvTotalTime: TextView) {
        stopAudio()
        mediaPlayer = MediaPlayer.create(this, audios[index % audios.size])
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
        val minutes = (milliseconds / (1000 * 60)) % 60
        return String.format("%02d:%02d", minutes, seconds)
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

    private fun updateSeekBar(seekBar: SeekBar, tvElapsedTime: TextView) {
        handler.postDelayed(object : Runnable {
            override fun run() {
                mediaPlayer?.let {
                    seekBar.progress = it.currentPosition
                    tvElapsedTime.text = formatTime(it.currentPosition)
                    handler.postDelayed(this, 500)
                }
            }
        }, 500)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopAudio()
        handler.removeCallbacksAndMessages(null)
    }
}
