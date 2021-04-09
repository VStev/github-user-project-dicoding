package com.submission.githubuser.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class SimpleUserData(
    @PrimaryKey(autoGenerate = true)
    var uid: Int,
    @ColumnInfo
    @SerializedName("login")
    var username : String? = "",
    @ColumnInfo
    @SerializedName("avatar_url")
    var avatarUrl: String? = ""
)