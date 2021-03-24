package com.submission.githubuser.user

import com.google.gson.annotations.SerializedName

data class SimpleUserData(
    @SerializedName("login")
    var username : String? = "",
    @SerializedName("avatar_url")
    var avatarUrl: String? = ""
)