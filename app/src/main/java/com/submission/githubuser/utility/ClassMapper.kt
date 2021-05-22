package com.submission.githubuser.utility

import com.submission.githubuser.domain.model.SimpleUserData
import com.submission.githubuser.domain.model.UserData
import com.submission.githubuser.user.SearchUserData
import com.submission.githubuser.user.SimpleUserDataEntity
import com.submission.githubuser.user.UserDataEntity

object ClassMapper {

    fun mapSearchResultToEntity(input: SearchUserData): ArrayList<SimpleUserDataEntity>? {
        return input.items
    }

    fun mapSimpleEntityToDomain(input: List<SimpleUserDataEntity>): ArrayList<SimpleUserData>{
        val result = ArrayList<SimpleUserData>()
        input.map{
            result.add(
                SimpleUserData(
                    it.username,
                    it.avatarUrl
                )
            )
        }
        return result
    }

    fun mapUserEntityToDomain(input: UserDataEntity): UserData {
        return UserData(
            input.username,
            input.name,
            input.location,
            input.repositoryCount,
            input.company,
            input.followersCount,
            input.followingCount,
            input.avatarUrl
        )
    }
}