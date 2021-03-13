package com.submission.githubuser.user

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserData (
    var username: String? = "",
    var name: String? = "",
    var location: String? = "",
    var repositoryCount: String? = "",
    var company: String? = "",
    var followersCount: String? = "",
    var followingCount: String? = "",
    var avatar: Int? = 0
    ) : Parcelable
