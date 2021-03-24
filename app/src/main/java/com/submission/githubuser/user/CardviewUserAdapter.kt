package com.submission.githubuser.user

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.submission.githubuser.R
import com.submission.githubuser.databinding.CardviewUsersBinding

class CardviewUserAdapter : RecyclerView.Adapter<CardviewUserAdapter.UserDataHolder>() {

    private val mData = ArrayList<SimpleUserData>()
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setData(items: ArrayList<SimpleUserData>){
        mData.clear()
        mData.addAll(items)
        Log.v("QUEEK", mData.size.toString())
        notifyDataSetChanged()
    }

    inner class UserDataHolder(items: View) : RecyclerView.ViewHolder(items) {
        private val binding = CardviewUsersBinding.bind(itemView)
        fun bind(userData: SimpleUserData){
            Glide.with(binding.root)
                .load(userData.avatarUrl)
                .apply(RequestOptions().override(250, 250))
                .into(binding.imageProfileThumbnail)
            binding.textUsername.text = userData.username
            binding.textUsername.setOnClickListener{onItemClickCallback.onItemClicked(binding.textUsername.toString())}
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.cardview_users, parent, false)
        return UserDataHolder(view)
    }

    override fun onBindViewHolder(holder: UserDataHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size

    interface OnItemClickCallback {
        fun onItemClicked(user: String?)
    }

}