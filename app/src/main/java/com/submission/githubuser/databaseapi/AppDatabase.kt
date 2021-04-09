package com.submission.githubuser.databaseapi

import android.content.Context
import android.net.Uri
import android.os.Build
import android.service.notification.Condition.SCHEME
import androidx.annotation.RequiresApi
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.RoomMasterTable.TABLE_NAME
import com.submission.githubuser.user.SimpleUserData

@Database(entities = arrayOf(SimpleUserData::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDAO() : RoomDAO

    companion object {
        const val AUTHORITY = "com.submission.githubuser"
        @RequiresApi(Build.VERSION_CODES.N)
        val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
            .authority(AUTHORITY)
            .appendPath(TABLE_NAME)
            .build()
        private var INSTANCE: AppDatabase? = null
        private const val DB_NAME = "users.db"

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