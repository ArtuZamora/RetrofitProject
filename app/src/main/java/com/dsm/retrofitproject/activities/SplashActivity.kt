package com.dsm.retrofitproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.dsm.retrofitproject.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_splash)
        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, IniciarSesionActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}