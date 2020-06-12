package com.example.porte

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignIn : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        auth = FirebaseAuth.getInstance()

        signIn_btn.setOnClickListener {
            val email = email_tv.text.toString()
            val password = password_tv.toString()

            if email.isNotEmpty() && email != null {
                if password.isNotEmpty() && password != null {
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
                        if (task.isSuccessful) {
                            val user = task.result.user
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
                else {

                }
            }
            else {

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
