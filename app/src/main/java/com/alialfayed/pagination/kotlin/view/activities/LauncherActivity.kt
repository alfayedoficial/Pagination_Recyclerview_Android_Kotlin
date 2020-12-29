package com.alialfayed.pagination.kotlin.view.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.Slide
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.alialfayed.pagination.kotlin.R
import com.alialfayed.pagination.kotlin.databinding.ActivityLauncherBinding

class LauncherActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLauncherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_launcher)
        binding.activity = this

        setupWindowAnimations()
    }

    private fun setupWindowAnimations() {
        val slide = Slide()
        slide.duration = 1000
        window.exitTransition = slide
        val slideDown = AnimationUtils.loadAnimation(this, R.anim.slide_down)
        val fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        binding.txtTitle.startAnimation(slideDown)
        binding.txtDescription.startAnimation(fadeIn)
        binding.txtLicense.startAnimation(fadeIn)
    }

    fun openHomeActivity() {
        val move = AnimationUtils.loadAnimation(this, R.anim.move)
        val backMove = AnimationUtils.loadAnimation(this, R.anim.back_move)
        binding.btnLetsGo.startAnimation(move)
        val handler = Handler(Looper.myLooper()!!)
        handler.postDelayed({
            startActivity(Intent(this@LauncherActivity, HomeActivity::class.java))
            binding.btnLetsGo.startAnimation(backMove)
        }, 800)
    }
}