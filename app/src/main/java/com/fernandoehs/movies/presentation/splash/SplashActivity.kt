package com.fernandoehs.movies.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.fernandoehs.movies.R
import com.fernandoehs.movies.databinding.ActivitySplashBinding
import com.fernandoehs.movies.presentation.MainActivity


class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val zoom = AnimationUtils.loadAnimation(this, R.anim.zoom);
        binding.image.startAnimation(zoom)

        val h = Handler()
        h.postDelayed({
            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)
            finish()
        }, 4000)

    }
}
