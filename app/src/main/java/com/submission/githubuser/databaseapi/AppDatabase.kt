package com.submission.githubuser.databaseapi

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.submission.githubuser.user.SimpleUserData

@Database(entities = arrayOf(SimpleUserData::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDAO() : RoomDAO

    companion object {
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "movies.db"

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
                            .build()
                    }
                }
            }

            return INSTANCE!!
        }
    }
}