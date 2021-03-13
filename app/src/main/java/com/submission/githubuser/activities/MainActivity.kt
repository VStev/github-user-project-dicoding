package com.submission.githubuser.activities

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.submission.githubuser.R
import com.submission.githubuser.user.CardviewUserAdapter
import com.submission.githubuser.user.UserData

class MainActivity : AppCompatActivity() {
    private lateinit var recycleView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycleView = findViewById(R.id.user_list)
        recycleView.setHasFixedSize(true)
        showLayout()
    }

    private fun fetchUser(): ArrayList<UserData> {
        val list = arrayListOf<UserData>()
        var images: TypedArray = resources.obtainTypedArray(R.array.avatar)
        for (i in resources.getStringArray(R.array.username).indices) {
            val user = UserData(
                resources.getStringArray(R.array.username)[i],
                resources.getStringArray(R.array.name)[i],
                resources.getStringArray(R.array.location)[i],
                resources.getStringArray(R.array.repository)[i],
                resources.getStringArray(R.array.company)[i],
                resources.getStringArray(R.array.following)[i],
                resources.getStringArray(R.array.followers)[i],
                images.getResourceId(i, 0)
            )
            list.add(user)
        }
        return list
    }

    private fun showLayout() {
        supportActionBar?.title = R.string.homepage_header.toString()
        recycleView.layoutManager = LinearLayoutManager(this)
        val dataAdapter = CardviewUserAdapter(fetchUser())
        recycleView.adapter = dataAdapter
        dataAdapter.setOnItemClickCallback(object: CardviewUserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: UserData) {
                val moveIntent = Intent(this@MainActivity, UserDetailActivity::class.java)
                moveIntent.putExtra(UserDetailActivity.EXTRA_USER, data)
                startActivity(moveIntent)
            }
        })
    }
}