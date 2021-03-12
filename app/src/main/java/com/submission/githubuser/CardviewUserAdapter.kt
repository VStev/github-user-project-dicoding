package com.submission.githubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

class CardviewUserAdapter(private val userList: ArrayList<UserData>) : RecyclerView.Adapter<CardviewUserAdapter.UserDataHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    inner class UserDataHolder(items: View) : RecyclerView.ViewHolder(items) {
        val avatarImage: ImageView = items.findViewById(R.id.image_profile_thumbnail)
        val username: TextView = items.findViewById(R.id.text_username)
        val company: TextView = items.findViewById(R.id.text_workplace)
        val follower: TextView = items.findViewById(R.id.followers_count)
        val repository: TextView = items.findViewById(R.id.repository_count)
        val followerIcon: ImageView = items.findViewById(R.id.followers_icon)
        val repositoryIcon: ImageView = items.findViewById(R.id.repository_icon)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.cardview_users, parent, false)
        return UserDataHolder(view)
    }

    override fun onBindViewHolder(holder: UserDataHolder, position: Int) {
        val user = userList[position]
        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .apply(RequestOptions().override(250, 250))
            .into(holder.avatarImage)
        Glide.with(holder.itemView.context)
            .load(R.drawable.icon_follower)
            .apply(RequestOptions().override(60, 60))
            .into(holder.followerIcon)
        Glide.with(holder.itemView.context)
            .load(R.drawable.icon_repository)
            .apply(RequestOptions().override(60, 60))
            .into(holder.repositoryIcon)
        holder.username.text = user.username
        holder.company.text = user.company
        holder.repository.text = user.repositoryCount
        holder.follower.text = user.followersCount
        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(userList[holder.adapterPosition])}
    }

    override fun getItemCount(): Int = userList.size

    interface OnItemClickCallback {
        fun onItemClicked(user: UserData)
    }

}