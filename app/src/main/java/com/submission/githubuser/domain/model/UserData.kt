package com.submission.githubuser.domain.model

data class UserData(
    var username: String? = "",
    var name: String? = "",
    var location: String? = "",
    var repositoryCount: String? = "",
    var company: String? = "",
    var followersCount: String? = "",
    var followingCount: String? = "",
    var avatarUrl: String? = ""
)
