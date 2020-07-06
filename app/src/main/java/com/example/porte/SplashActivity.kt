package com.example.porte

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer

import com.example.porte.Shared.UserInfoDatabase
import com.example.porte.ui.signInUp.SignIn
import com.google.firebase.auth.FirebaseAuth


class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val userDao by lazy { UserInfoDatabase.getDatabase(this).userInfoDAO() }
    val SPLASH_TIME_OUT: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        // 상단 바 숨기
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = FirebaseAuth.getInstance()


        Handler().postDelayed({
            //어떤 액티비티로 넘어 갈지 설정
            userDao.selectAllUserInfo().observe(this, Observer {
                if (it != null && auth.currentUser != null) {
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                else {
                    auth.signOut()
                    startActivity(Intent(this, SignIn::class.java))
                }
            })

            finish()
        },SPLASH_TIME_OUT)
    }
}
