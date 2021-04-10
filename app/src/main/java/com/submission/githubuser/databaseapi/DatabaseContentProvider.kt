package com.submission.githubuser.databaseapi

import android.content.ContentProvider
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.util.Log
import com.submission.githubuser.databaseapi.AppDatabase.Companion.AUTHORITY
import com.submission.githubuser.databaseapi.AppDatabase.Companion.CONTENT_URI
import com.submission.githubuser.user.SimpleUserData

class DatabaseContentProvider : ContentProvider() {

    companion object {
        private const val FAVOURITE = 1
        private const val FAVOURITE_BY_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var databaseInterface: DatabaseInterface
        private const val TABLE_NAME = "SimpleUserData"

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, FAVOURITE)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/*", FAVOURITE_BY_ID)
        }
    }

    override fun onCreate(): Boolean {
        databaseInterface = DatabaseInterface(context!!)
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (sUriMatcher.match(uri)) {
            FAVOURITE -> {
                Log.d("REMINGTON", "I AM CALLED AND MY URI IS $uri AND ITS its ${sUriMatcher.match(uri)}")
                databaseInterface.getAll()
            }
            FAVOURITE_BY_ID -> {
                Log.d("REMINGTON", "I HAVE 1911 AND ITS ${uri.lastPathSegment.toString()} AND MY URI IS $uri and its ${sUriMatcher.match(uri)}")
                databaseInterface.getSpecificUser(uri.lastPathSegment.toString())
            }
            else -> {
                Log.d("REMINGTON", "PROJECT BUNNY 19C HAVE CONFISCATED OUR M40! THE URI IS $uri and its ${sUriMatcher.match(uri)}")
                null
            }
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val status: Long = when (sUriMatcher.match(uri)) {
            FAVOURITE -> {
                values?.let { databaseInterface.insert(
                    SimpleUserData(
                        values.getAsString(SimpleUserData.user),
                        values.getAsString(SimpleUserData.avatar)
                    )
                ) }
                1
            }
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$status")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val status: Int = when (sUriMatcher.match(uri)) {
            FAVOURITE_BY_ID -> {
                databaseInterface.delete(uri.lastPathSegment.toString())
                Log.d(TAG, "delete: IM DELETING SOMETHING")
                1
            }
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return status
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String {
        return uri.toString()
    }
}