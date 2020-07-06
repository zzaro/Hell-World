package com.example.porte.ui.signInUp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import android.widget.VideoView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.example.porte.MainActivity
import com.example.porte.R
import com.example.porte.Shared.SharedData
import com.example.porte.Shared.UserFlightInfoDatabase
import com.example.porte.Shared.UserInfoDatabase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val userDao by lazy { UserInfoDatabase.getDatabase(this).userInfoDAO() }
//    private val flightDao by lazy { UserFlightInfoDatabase.getDatabase(this).userFlightInfoDAO() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 상단 타이틀바 제거
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()


        userDao.selectAllUserInfo().observe(this, Observer {
            if (it != null && auth.currentUser != null) {
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            else {
                auth.signOut()
            }
        })


//        if (auth.currentUser != null ) {
//            val intent = Intent(this, MainActivity::class.java)
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            startActivity(intent)
//        }



        signIn_btn.setOnClickListener {
            signIn_progressBar.isVisible = true
            Log.d("test", "로그인 버튼")
            val email = email_tv.text.toString()
            val password = password_tv.text.toString()

            if (email.isNotEmpty()) {
                if (password.isNotEmpty()) {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
                        if (task.isSuccessful) {
                            signIn_progressBar.isVisible = false

                            val intent = Intent(this, Profile::class.java)
                            intent.putExtra("isFromSignIn", true)
                            startActivity(intent)
                        }
                        else {
                            signIn_progressBar.isVisible = false
                            Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    signIn_progressBar.isVisible = false
                    Toast.makeText(this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                signIn_progressBar.isVisible = false
                Toast.makeText(this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

        }


        signUp_btn.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            startActivity(intent)
        }
    }


    override fun onResume() {
        super.onResume()

        val videoView: VideoView = findViewById(R.id.signIn_video_view)
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.porte_bg_video)
        videoView.setOnPreparedListener {
            it.setLooping(true)
        }
        videoView.setVideoURI(videoUri)
        videoView.start()
    }
}
