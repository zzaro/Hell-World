package com.example.porte.Shared

import androidx.room.*

@Dao
interface UserInfoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserInfo(vararg userInfo: UserInfoEntity)

    @Query("SELECT * FROM UserInfoEntity WHERE userID = :userID")
    fun selectUserInfo(userID: String): UserInfoEntity

    @Update
    fun updateUserInfo(vararg userInfo: UserInfoEntity)

    @Delete
    fun deleteAllUserInfo(vararg userInfo: UserInfoEntity)

    @Query("DELETE FROM UserInfoEntity WHERE userID = :userID")
    fun deleteUserInfo(vararg userID: String)
}

@Dao
interface UserFlightInfoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUserFlightInfo(vararg userFlightInfo: UserFlightInfoEntity)

    @Query("SELECT * FROM UserFlightInfoEntity WHERE flightInfoIdx = :flightInfoIdx")
    fun selectUserFlightInfo(flightInfoIdx: Long): UserFlightInfoEntity

    @Update
    fun updateUserFlightInfo(vararg userFlightInfo: UserFlightInfoEntity)

    @Delete
    fun deleteAllUserFlightInfo(vararg userFlightInfo: UserFlightInfoEntity)

    @Query("DELETE FROM UserFlightInfoEntity WHERE flightInfoIdx = :flightInfoIdx")
    fun deleteUserFlightInfo(vararg flightInfoIdx: Long)
}