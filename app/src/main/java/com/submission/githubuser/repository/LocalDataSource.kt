package com.submission.githubuser.repository

import com.submission.githubuser.databaseapi.RoomDAO
import com.submission.githubuser.user.SimpleUserDataEntity
import io.reactivex.Flowable

class LocalDataSource (private val roomDao: RoomDAO){

    fun getAllUser(): Flowable<List<SimpleUserDataEntity>> = roomDao.getAll()

    fun findUserById(user: String): Flowable<List<SimpleUserDataEntity>> = roomDao.findUserById(user)

    fun insert(user: SimpleUserDataEntity) = roomDao.insert(user)

    fun delete(user: String) = roomDao.delete(user)
}