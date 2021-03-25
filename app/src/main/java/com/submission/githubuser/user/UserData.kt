package com.submission.githubuser.user

import com.google.gson.annotations.SerializedName

data class UserData (
    @SerializedName ("login")
    var username: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("location")
    var location: String? = "",
    @SerializedName("public_repos")
    var repositoryCount: String? = "",
    @SerializedName("company")
    var company: String? = "",
    @SerializedName("followers")
    var followersCount: String? = "",
    @SerializedName("following")
    var followingCount: String? = "",
    @SerializedName("avatar_url")
    var avatarUrl: String? = ""
    )
