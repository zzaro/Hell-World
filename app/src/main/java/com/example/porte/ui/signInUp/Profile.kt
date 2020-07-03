package com.example.porte.ui.signInUp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import androidx.core.widget.addTextChangedListener
import com.example.porte.MainActivity
import com.example.porte.R
import com.example.porte.Shared.UserInfoDatabase
import com.example.porte.Shared.UserInfoEntity
import com.example.porte.Util.FirebaseUtil
import com.example.porte.Util.ImageTransferUtil
import com.google.firebase.auth.FirebaseAuth
import com.kroegerama.imgpicker.BottomSheetImagePicker
import com.kroegerama.imgpicker.ButtonType
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Profile : AppCompatActivity(), BottomSheetImagePicker.OnImagesSelectedListener {

    private var userInfo = UserInfoEntity("", "", null)
    private val dao by lazy { UserInfoDatabase.getDatabase(this).userInfoDAO() }
    val userProfileImgView by lazy {
        profile_image_view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 상단 타이틀바 제거
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()

        setContentView(R.layout.activity_profile)

        val submitBtn = profile_submit_btn
        val userNameTextView = profile_user_name_text_view


        userProfileImgView.clipToOutline = true
        userProfileImgView.setOnClickListener {
            buildImagePicker()
        }

        FirebaseUtil.getUserProfile(userNameTextView, userProfileImgView, this)


        userNameTextView.addTextChangedListener {
            submitBtn.isEnabled = !it.isNullOrBlank()
        }

        submitBtn.setOnClickListener {
            FirebaseUtil.setUserProfile(userNameTextView, userProfileImgView, this)

            userInfo.userID = FirebaseAuth.getInstance().currentUser?.email.toString()
            userInfo.userName = userNameTextView.text.toString()
            userInfo.userImg = ImageTransferUtil.changeImageToString(userProfileImgView)

            CoroutineScope(Dispatchers.IO).launch {
                dao.insertUserInfo(userInfo)
            }

            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

    override fun onImagesSelected(uris: List<Uri>, tag: String?) {
        if (uris.isNotEmpty()) {
            val bitmap =
                MediaStore.Images.Media.getBitmap(this.contentResolver, uris[0])
            userProfileImgView.setImageBitmap(bitmap)
        }
    }


    fun setBackgroundVideo() {
        val videoView: VideoView = findViewById(R.id.profile_video_view)
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.porte_bg_video_blur)
        videoView.setOnPreparedListener {
            it.setLooping(true)
        }
        videoView.setVideoURI(videoUri)
        videoView.start()
    }

    override fun onResume() {
        super.onResume()

        setBackgroundVideo()
    }

    fun buildImagePicker() {
        BottomSheetImagePicker.Builder("빌드")
            .cameraButton(ButtonType.Button)            //style of the camera link (Button in header, Image tile, None)
            .galleryButton(ButtonType.Button)           //style of the gallery link
            .singleSelectTitle(R.string.select_title)    //header text
            .requestTag("single")                       //tag can be used if multiple pickers are used
            .show(supportFragmentManager)
    }



}
