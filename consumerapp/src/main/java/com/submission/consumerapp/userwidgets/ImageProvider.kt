package com.submission.consumerapp.userwidgets

import android.content.Context
import com.submission.consumerapp.contentreceiver.DatabaseContract
import com.submission.consumerapp.contentreceiver.MappingHelper
import com.submission.consumerapp.user.SimpleUserData

class ImageProvider {
    companion object {

        private val mapper = MappingHelper
        private val favList = arrayListOf<SimpleUserData>()

        fun getImages(context: Context): ArrayList<SimpleUserData> {
            favList.clear()
            val cursor = context.contentResolver.query(
                DatabaseContract.CONTENT_URI,
                null,
                null,
                null,
                null
            )
            val deferredList = mapper.mapCursorToArrayList(cursor)
            favList.addAll(deferredList)
            return favList
        }
    }
}