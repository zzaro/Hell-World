package com.example.porte.ui.signInUp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import android.widget.VideoView
import androidx.core.view.isVisible
import com.example.porte.MainActivity
import com.example.porte.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private var isEmailConfirmed = false
    private var isPasswordConfirmed = false
    private var isPasswordVerificationConfirmed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 상단 타이틀바 제거
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_sign_up)


        val videoView: VideoView = findViewById(R.id.signUp_video_view)
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.porte_bg_video_blur)
        videoView.setOnPreparedListener {
            it.setLooping(true)
        }
        videoView.setVideoURI(videoUri)
        videoView.start()


        auth = FirebaseAuth.getInstance()

        // 이메일 입력 완료 시 중복되는 이메일이 있는지 확인.
        email_edit_tv.setOnFocusChangeListener { v: View?, hasFocus: Boolean ->
            if (!hasFocus) { // 입력을 마쳤을 경우
                val email = email_edit_tv.text.toString()
                if (email.isNotEmpty() && email != null) {
                    auth.fetchSignInMethodsForEmail(email).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val isNewUser = task.getResult()?.signInMethods?.isEmpty()

                            if (isNewUser!!) {
                                email_error_tv.visibility = View.INVISIBLE
                                email_confirmed_img.visibility = View.VISIBLE
                                Log.d("test", "신규 유저입니다.")
                                isEmailConfirmed = true
                            }
                            else {
                                email_error_tv.text = "이미 존재하는 아이디 입니다."
                                email_error_tv.visibility = View.VISIBLE
                                Log.d("test", "이미 존재하는 유저입니다.")
                                isEmailConfirmed = false
                            }
                            checkAllEditConfirmed()
                        }
                        else {
                            email_error_tv.text = "잘못된 이메일 형식입니다."
                            email_error_tv.visibility = View.VISIBLE
                            email_confirmed_img.visibility = View.INVISIBLE
                        }// END OF if(task.isSuccessful)
                    }//END OF auth.fetchSignInMethodsForEmail
                } //END OF if (email.isNotEmpty() && email != null)
            }
            else { // 입력을 시작할 경우
                email_error_tv.visibility = View.INVISIBLE
                email_confirmed_img.visibility = View.INVISIBLE
            } //END OF if(!hasFocus)
        }

        // 비밀번호가 6자리 이상인지 확인
        password_edit_tv.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus) { // 입력을 마쳤을 경우
                // 비밀번호 6자리 이상
                if (password_edit_tv.text.length >= 6) {
                    Log.d("test", "${password_edit_tv.text.length}")
                    password_error_tv.visibility = View.INVISIBLE
                    password_confirmed_img.visibility = View.VISIBLE
                    isPasswordConfirmed = true
                }
                // 비밀번호 6자리 이하
                else {
                    password_error_tv.visibility = View.VISIBLE
                    password_confirmed_img.visibility = View.INVISIBLE
                    isPasswordConfirmed = false
                }
                checkAllEditConfirmed()
            }
            else {
                password_confirmed_img.visibility = View.INVISIBLE
                password_error_tv.visibility = View.INVISIBLE
                password_verification_error_tv.visibility = View.INVISIBLE
                password_verification_confirmed_img.visibility = View.INVISIBLE
            } // END OF if(!hasFocus)
        }// END OF password_verification_error_tv.setOnFocusChangeListener


        // 비밀번호 확인란 입력 완료 시 비밀번호가 동일한지 확인
        password_verification_edit_tv.setOnFocusChangeListener { v, hasFocus ->
            // 입력을 마쳤을 경우
            if(!hasFocus) {
                Log.d("test", "비밀번호확인 입력완료.")
                confirmPasswordVerification()
            }
            else {
                password_verification_error_tv.visibility = View.INVISIBLE
                password_verification_confirmed_img.visibility = View.INVISIBLE
            }// END OF if(!hasFocus)
        }// END OF password_verification_error_tv.setOnFocusChangeListener


        password_verification_edit_tv.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                confirmPasswordVerification()
            }
            false
        }


        submit_btn.setOnClickListener {
            signUp_progressBar.isVisible = true

            val email = email_edit_tv.text.toString()
            val password = password_edit_tv.text.toString()

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {
                    Log.d("AUTH", "회원가입 완료.")
                    signUp_progressBar.isVisible = false
                    Toast.makeText(this, "회원가입이 완료되었습니다.\n다시 로그인해주세요.", Toast.LENGTH_LONG).show()
//                    val intent = Intent(this, MainActivity::class.java)
//                    startActivity(intent)
                    finish()
                }
                else {
                    Log.d("AUTH", "회원가입 실패.", task.exception)
                    signUp_progressBar.isVisible = true
                    Toast.makeText(this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            } // END OF auth.createUserWithEmailAndPassword
        } // END OF submit_btn.setOnClickListener
    } // END OF onCreate

    fun checkAllEditConfirmed() {
        if (isEmailConfirmed == true
            && isPasswordConfirmed == true
            && isPasswordVerificationConfirmed == true) {
            submit_btn.isEnabled = true
        }
        else {
            submit_btn.isEnabled = false
        }
    }

    fun confirmPasswordVerification() {
        // 비밀번호 일치
        if (password_edit_tv.text.toString() == password_verification_edit_tv.text.toString()) {
            password_verification_error_tv.visibility = View.INVISIBLE
            password_verification_confirmed_img.visibility = View.VISIBLE
            isPasswordVerificationConfirmed = true
        }
        // 비밀번호 불일치
        else {
            password_verification_error_tv.visibility = View.VISIBLE
            password_verification_confirmed_img.visibility = View.INVISIBLE
            isPasswordVerificationConfirmed = false
        }
        checkAllEditConfirmed()
    }

    override fun onResume() {
        super.onResume()


    }

}
