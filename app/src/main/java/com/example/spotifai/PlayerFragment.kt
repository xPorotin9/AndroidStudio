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
    private var isPlaying = false
    private val handler = Handler(Looper.getMainLooper())

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

    companion object {
        private const val ARG_POSITION = "position"

        fun newInstance(position: Int): PlayerFragment {
            val fragment = PlayerFragment()
            val args = Bundle()
            args.putInt(ARG_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_player, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val position = arguments?.getInt(ARG_POSITION, 0) ?: 0

        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val btnBack = view.findViewById<Button>(R.id.btnBack)
        val btnPlayPause = view.findViewById<Button>(R.id.btnPlayPause)
        val seekBar = view.findViewById<SeekBar>(R.id.seekBar)
        val tvElapsedTime = view.findViewById<TextView>(R.id.tvElapsedTime)
        val tvTotalTime = view.findViewById<TextView>(R.id.tvTotalTime)
        val tvSongTitle = view.findViewById<TextView>(R.id.tvSongTitle)

        imageView.setImageResource(images[position])
        tvSongTitle.text = songs[position]

        playAudio(audios[position], seekBar, tvElapsedTime, tvTotalTime)

        btnBack.setOnClickListener {
            stopAudio()
            activity?.supportFragmentManager?.popBackStack()
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
        mediaPlayer = MediaPlayer.create(requireContext(), audioResource)
        mediaPlayer?.start()
        isPlaying = true

        seekBar.max = mediaPlayer?.duration ?: 0
        tvTotalTime.text = formatTime(mediaPlayer?.duration ?: 0)

        updateSeekBar(seekBar, tvElapsedTime)

        mediaPlayer?.setOnCompletionListener {
            isPlaying = false
            view?.findViewById<Button>(R.id.btnPlayPause)?.setBackgroundResource(R.drawable.play_icon)
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

    override fun onDestroy() {
        super.onDestroy()
        stopAudio()
    }
}