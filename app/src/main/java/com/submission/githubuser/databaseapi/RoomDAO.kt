package com.submission.githubuser.databaseapi

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.submission.githubuser.user.SimpleUserDataEntity
import io.reactivex.Flowable

@Dao
interface RoomDAO {
    @Query("SELECT * FROM SimpleUserDataEntity")
    fun getAll(): Flowable<List<SimpleUserDataEntity>>

    @Query("SELECT * FROM SimpleUserDataEntity WHERE username = :user")
    fun findUserById(user: String): Flowable<List<SimpleUserDataEntity>>

    @Insert
    fun insert(user: SimpleUserDataEntity)

    @Query("DELETE FROM SimpleUserDataEntity WHERE username = :user")
    fun delete(user: String)
}