package com.submission.githubuser.user

import com.google.gson.annotations.SerializedName

data class SearchUserData(
    @SerializedName("total_count")
    var count : String? = "",
    @SerializedName("items")
    var items: ArrayList<SimpleUserDataEntity>? = null
)