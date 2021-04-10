package com.submission.githubuser.databaseapi

import android.content.ContentValues
import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RoomDAO {
    @Query("SELECT * FROM SimpleUserData")
    fun getAll(): Cursor

    @Query("SELECT * FROM SimpleUserData WHERE username = :user")
    fun findUserById(user: String): Cursor

    @Insert
    fun insert(user: ContentValues)

    @Query("DELETE FROM SimpleUserData WHERE username = :user")
    fun delete(user: String) : Int
}