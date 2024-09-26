package com.example.spotifai

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MainFragment())
                .commit()
        }
    }

    fun navigateToPlayer(position: Int) {
        val playerFragment = PlayerFragment.newInstance(position)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, playerFragment)
            .addToBackStack(null)
            .commit()
    }
}