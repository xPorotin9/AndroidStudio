package com.example.spotifai

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment

class ImageDetailFragment : Fragment() {

    private var imageResId: Int = 0
    private lateinit var images: IntArray
    private var currentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageResId = arguments?.getInt(ARG_IMAGE_ID) ?: 0
        currentPosition = arguments?.getInt(ARG_POSITION) ?: 0
        images = arguments?.getIntArray(ARG_IMAGES) ?: intArrayOf()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_detail, container, false)

        val imageView = view.findViewById<ImageView>(R.id.imageView)
        imageView.setImageResource(imageResId)

        val btnBack = view.findViewById<Button>(R.id.btnBack)
        btnBack.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        val btnPrevious = view.findViewById<Button>(R.id.btnPrevious)
        val btnNext = view.findViewById<Button>(R.id.btnNext)

        btnPrevious.setOnClickListener {
            if (currentPosition > 0) {
                currentPosition--
                updateImage(imageView)
            }
        }

        btnNext.setOnClickListener {
            if (currentPosition < images.size - 1) {
                currentPosition++
                updateImage(imageView)
            }
        }

        return view
    }

    private fun updateImage(imageView: ImageView) {
        if (currentPosition in images.indices) {
            // Cargar la nueva imagen
            val newImageResId = images[currentPosition]

            // Aplicar la animación de desvanecimiento
            val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
            val fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)

            // Primero, aplicar la animación de salida
            imageView.startAnimation(fadeOut)

            // Cambiar la imagen después de que la animación de salida termine
            fadeOut.setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) {}

                override fun onAnimationEnd(animation: Animation?) {
                    imageView.setImageResource(newImageResId)
                    // Luego aplicar la animación de entrada
                    imageView.startAnimation(fadeIn)
                }

                override fun onAnimationRepeat(animation: Animation?) {}
            })
        } else {
            Log.e("ImageDetailFragment", "Position out of bounds: $currentPosition")
        }
    }


    companion object {
        private const val ARG_IMAGE_ID = "image_id"
        private const val ARG_POSITION = "position"
        private const val ARG_IMAGES = "images"

        fun newInstance(imageResId: Int, position: Int, images: IntArray): ImageDetailFragment {
            val fragment = ImageDetailFragment()
            val args = Bundle()
            args.putInt(ARG_IMAGE_ID, imageResId)
            args.putInt(ARG_POSITION, position)
            args.putIntArray(ARG_IMAGES, images)
            fragment.arguments = args
            return fragment
        }
    }
}
