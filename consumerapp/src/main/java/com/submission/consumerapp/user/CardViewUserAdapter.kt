package com.submission.consumerapp.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.submission.consumerapp.R
import com.submission.consumerapp.databinding.CardviewUsersBinding

class CardViewUserAdapter : RecyclerView.Adapter<CardViewUserAdapter.UserDataHolder>() {

    private val mData = ArrayList<SimpleUserData>()

    fun setData(items: List<SimpleUserData>) {
        mData.clear()
        mData.addAll(items)
        notifyDataSetChanged()
    }

    inner class UserDataHolder(items: View) : RecyclerView.ViewHolder(items) {
        private val binding = CardviewUsersBinding.bind(itemView)
        fun bind(userData: SimpleUserData) {
            Glide.with(binding.root)
                .load(userData.avatarUrl)
                .apply(RequestOptions().override(250, 250))
                .into(binding.imageProfileThumbnail)
            binding.textUsername.text = userData.username
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserDataHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.cardview_users, parent, false)
        return UserDataHolder(view)
    }

    override fun onBindViewHolder(holder: UserDataHolder, position: Int) {
        holder.bind(mData[position])
    }

    override fun getItemCount(): Int = mData.size
}