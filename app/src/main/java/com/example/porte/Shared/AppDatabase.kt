package com.example.porte.Shared

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(UserInfoEntity::class), version = 1)
abstract class UserInfoDatabase: RoomDatabase() {
    abstract fun userInfoDAO(): UserInfoDAO

    companion object {
        private var database: UserInfoDatabase? = null

        private const val ROOM_DB = "userInfo.db"

        fun getDatabase(context: Context): UserInfoDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    UserInfoDatabase::class.java, ROOM_DB
                ).fallbackToDestructiveMigration().build()
            }
            return database!!
        }
    }
}


@Database(entities = arrayOf(UserFlightInfoEntity::class), version = 1)
abstract class UserFlightInfoDatabase: RoomDatabase() {
    abstract fun userFlightInfoDAO(): UserFlightInfoDAO

    companion object {
        private var database: UserFlightInfoDatabase? = null

        private const val ROOM_DB = "userFlightInfo.db"

        fun getDatabase(context: Context): UserFlightInfoDatabase {
            if (database == null) {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    UserFlightInfoDatabase::class.java, ROOM_DB
                ).fallbackToDestructiveMigration().build()
            }
            return database!!
        }
    }
}