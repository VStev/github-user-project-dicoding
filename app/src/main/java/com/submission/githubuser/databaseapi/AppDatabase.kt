package com.submission.githubuser.databaseapi

import androidx.room.Database
import androidx.room.RoomDatabase
import com.submission.githubuser.user.SimpleUserDataEntity

@Database(entities = [SimpleUserDataEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
    }
    abstract fun userDAO() : RoomDAO
}