package com.submission.githubuser.databaseapi

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.submission.githubuser.user.SimpleUserData

@Dao
interface RoomDAO {
    @Query("SELECT * FROM SimpleUserData")
    fun getAll(): LiveData<List<SimpleUserData>>

    @Query("SELECT * FROM SimpleUserData where username = :user")
    fun findUserById(user: String): LiveData<List<SimpleUserData>>

    @Insert
    fun insert(vararg user: SimpleUserData)

    @Query("DELETE FROM SimpleUserData where username = :user")
    fun delete(user: String)
}