package com.submission.consumerapp.user

data class SimpleUserData(
    var username : String,
    var avatarUrl: String? = ""
){
    companion object {
        const val user = "username"
        const val avatar = "avatar_url"
        val accessibleArray = arrayListOf<SimpleUserData>()
    }
}