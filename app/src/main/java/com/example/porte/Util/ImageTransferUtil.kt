package com.example.porte.Util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import java.io.ByteArrayOutputStream

object ImageTransferUtil {

    fun changeImageToString(imageView: ImageView): String {
        val bitmap = imageView.drawable.toBitmap()
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 30, stream)
        val imageBytes = stream.toByteArray()
        val userImageBase64String = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        return userImageBase64String
    }

    fun changeStirngToBitmap(stringData: String): Bitmap {
        val imageBytes = Base64.decode(stringData, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        return bitmap
    }
}