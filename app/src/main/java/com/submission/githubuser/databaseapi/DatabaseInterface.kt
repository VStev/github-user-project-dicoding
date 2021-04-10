package com.submission.githubuser.databaseapi

import android.content.Context
import com.submission.githubuser.user.SimpleUserData

class DatabaseInterface(context: Context) {
    private val database = AppDatabase.getDatabase(context)
    private val DAO = database.userDAO()

    fun getAll() = DAO.getAll()

    fun getSpecificUser(user: String) = DAO.findUserById(user)

    fun delete(user: String) = DAO.delete(user)

    fun insert(user: SimpleUserData) = DAO.insert(user)
}