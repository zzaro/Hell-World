package com.example.porte.ui.signInUp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import android.widget.VideoView
import com.example.porte.MainActivity
import com.example.porte.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 상단 타이틀바 제거
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_sign_in)


        val videoView: VideoView = findViewById(R.id.signIn_video_view)
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.bg_video)
        videoView.setOnPreparedListener {
            it.setLooping(true)
        }
        videoView.setVideoURI(videoUri)
        videoView.start()

        auth = FirebaseAuth.getInstance()

        signIn_btn.setOnClickListener {
            Log.d("test", "로그인 버튼")
            val email = email_tv.text.toString()
            val password = password_tv.text.toString()

            if (email.isNotEmpty() && email != null) {
                if (password.isNotEmpty() && password != null) {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                        else {
                            Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else {
                    Toast.makeText(this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                Toast.makeText(this, "아이디와 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

        }


        signUp_btn.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Activity 시작 시 사용자가 로그인이 되어있는지 확인.
        val currentUser = auth.currentUser

    }
}
