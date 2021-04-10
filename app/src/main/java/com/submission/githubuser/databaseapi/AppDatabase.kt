package com.submission.githubuser.databaseapi

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.submission.githubuser.user.SimpleUserData

@Database(entities = [SimpleUserData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val AUTHORITY = "com.submission.githubuser"
        @RequiresApi(Build.VERSION_CODES.N)
        val CONTENT_URI: Uri = Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath("SimpleUserData")
            .build()
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(context) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "users")
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
    abstract fun userDAO() : RoomDAO
}