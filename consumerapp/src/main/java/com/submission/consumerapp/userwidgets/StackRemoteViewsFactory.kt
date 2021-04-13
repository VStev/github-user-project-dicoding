package com.submission.consumerapp.userwidgets

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.submission.consumerapp.R
import com.submission.consumerapp.user.SimpleUserData

class StackRemoteViewsFactory(private val context: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private val imageArray = ArrayList<SimpleUserData>()

    override fun onCreate() {
        imageArray.addAll(ImageProvider.getImages(context))
    }

    override fun onDataSetChanged() {
        imageArray.clear()
        imageArray.addAll(ImageProvider.getImages(context))
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = imageArray.size

    override fun getViewAt(position: Int): RemoteViews {
        val rvImage = RemoteViews(context.packageName, R.layout.widget_item)
        val rvUser = RemoteViews(context.packageName, R.layout.favourite_stack)
        try {
            val bitmap =
                Glide.with(context).asBitmap().load(imageArray[position].avatarUrl).submit().get()
            rvImage.setImageViewBitmap(R.id.widgetImage, bitmap)
            rvUser.setTextViewText(R.id.ID_text, imageArray[position].username)
        } catch (e: Exception) {
            Log.d(TAG, "getViewAt: $e")
        }
        return rvImage
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int = 1

    override fun getItemId(position: Int): Long = 0

    override fun hasStableIds(): Boolean = false
}