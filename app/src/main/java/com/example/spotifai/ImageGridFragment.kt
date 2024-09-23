package com.example.spotifai

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import androidx.fragment.app.Fragment

class ImageGridFragment : Fragment() {

    // Cambiar de Array<Int> a IntArray
    private val images = intArrayOf(
        R.drawable.dogi, R.drawable.sublimeow, R.drawable.spinlog,
        R.drawable.bruh, R.drawable.calc, R.drawable.claz,
        R.drawable.door, R.drawable.fondo, R.drawable.plop
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_grid, container, false)

        val gridView = view.findViewById<GridView>(R.id.gridViewImages)
        val adapter = ImageAdapter(requireContext(), images) // No debería dar error aquí
        gridView.adapter = adapter

        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val imageDetailFragment = ImageDetailFragment.newInstance(images[position], position, images)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, imageDetailFragment)
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}
