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
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


class FirebaseUtil {
    private var db = FirebaseFirestore.getInstance()
    private var auth = FirebaseAuth.getInstance()
    private var userUID = auth.uid

    fun getUserProfile(userName: EditText, userImage: ImageView, context: Context) {
        db.collection(userUID!!).document("userInfo").get()
            .addOnSuccessListener {
                if (it.exists()) {
                    val name = it.data!!.get("userName").toString()
                        userName.setText(name)

                    val userImageBase64String = it.data!!.get("image").toString()
                    val bitmap = ImageTransferUtil.changeStirngToBitmap(userImageBase64String)
                        userImage.setImageBitmap(bitmap)
                        Log.d("log", "SetUserImage")
                }
                else {
                    userName.setText("")
                    userImage.setImageResource(R.drawable.default_profile)
                }
            }
            .addOnFailureListener {
                userImage.setImageResource(R.drawable.default_profile)
            }
    }

    fun setUserProfile(userName: EditText, userImageView: ImageView, success: () -> Unit) {

        // 프로필 사진 인코딩
        val userImageBase64String = ImageTransferUtil.changeImageToString(userImageView)

        Log.d("log", "userImage set = ${userImageBase64String}")
        mapData(userName, userImageBase64String, success)

    }

    private fun mapData(userName: EditText, userImageData: String, success: () -> Unit) {
        val userInfo = hashMapOf(
            "userName" to userName.text.toString(),
            "image" to userImageData
        )


        db.collection(userUID!!).document("userInfo").set(userInfo)
            .addOnSuccessListener {
                success()
            }
            .addOnFailureListener {
                Log.d("Log", "ERROR: Firebase User Profile Upload Failed.")
            }

    }
}