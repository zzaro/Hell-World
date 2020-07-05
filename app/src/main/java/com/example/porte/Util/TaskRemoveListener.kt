package com.example.porte.Util

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.porte.MainActivity
import com.example.porte.Shared.UserInfoDatabase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


public class TaskRemoveListener: Service() {

    private val userDao by lazy { UserInfoDatabase.getDatabase(this).userInfoDAO() }

    override  fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("ClearFromRecentService", "Service Started")
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ClearFromRecentService", "Service Destroyed")
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.e("ClearFromRecentService", "END")

        var auth =  FirebaseAuth.getInstance()

        CoroutineScope(Dispatchers.IO).launch {
            if (auth.currentUser == null || userDao.selectAllUserInfo().value == null) {
                FirebaseAuth.getInstance().signOut()
            }
        }
        stopSelf()
    }
}