package com.example.porte.Util

import android.R.drawable
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.Image
import android.util.Base64
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import com.example.porte.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


object FirebaseUtil {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userUID = auth.currentUser!!.uid.toString()

    fun getUserProfile(userName: EditText, userImage: ImageView, context: Context) {
        db.collection(userUID).document("userInfo").get()
            .addOnSuccessListener {

                // 이름 가져오기
                val name: String?
                if (it["name"].toString() == "null") { name = "" }
                else { name = it["name"].toString() }
                userName.setText(name)

                // 프로필사진 디코딩
                val userImageBase64String = it["image"].toString()
                Log.d("log", userImageBase64String)
                if (userImageBase64String == "null") {
                    val defaultProfile = context.resources.getDrawable(R.drawable.default_profile)
                    userImage.setImageDrawable(defaultProfile)
                    Log.d("log", "SetDefaultImage")
                } else {
                    val bitmap = ImageTransferUtil.changeStirngToBitmap(userImageBase64String)
                    userImage.setImageBitmap(bitmap)
                    Log.d("log", "SetUserImage")
                }
            }
            .addOnFailureListener {
                userImage.setImageResource(R.drawable.default_profile)
            }
    }

    fun setUserProfile(userName: EditText, userImageView: ImageView, context: Context) {

        // 프로필 사진 인코딩
        val userImageBase64String = ImageTransferUtil.changeImageToString(userImageView)

        Log.d("log", "userImage set = ${userImageBase64String}")
        mapData(userName, userImageBase64String)

    }

    private fun mapData(userName: EditText, userImageData: String) {
        val userInfo = hashMapOf(
            "name" to userName.text.toString(),
            "image" to userImageData
        )


        db.collection(userUID).document("userInfo").set(userInfo)
            .addOnFailureListener {
                Log.d("Log", "ERROR: Firebase User Profile Upload Failed.")
            }

    }
}