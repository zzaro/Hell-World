package com.example.porte

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import com.example.porte.ui.signInUp.SignIn

class SplashActivity : AppCompatActivity() {


    val SPLASH_TIME_OUT: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            //어떤 액티비티로 넘어 갈지 설정
            startActivity(Intent(this, SignIn::class.java))
            finish()
        },SPLASH_TIME_OUT)
    }
}
