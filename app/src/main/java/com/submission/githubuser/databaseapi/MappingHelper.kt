package com.submission.githubuser.databaseapi

import android.database.Cursor
import com.submission.githubuser.user.SimpleUserData
import java.util.*

object MappingHelper {
    fun mapCursorToArrayList(notesCursor: Cursor?): ArrayList<SimpleUserData> {
        val userList = ArrayList<SimpleUserData>()

        notesCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("uid"))
                val username = getString(getColumnIndexOrThrow("username"))
                val avatarUrl = getString(getColumnIndexOrThrow("avatarUrl"))
                userList.add(SimpleUserData(id, username, avatarUrl))
            }
        }
        return userList
    }
}