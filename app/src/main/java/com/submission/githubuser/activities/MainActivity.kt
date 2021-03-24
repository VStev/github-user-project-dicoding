package com.submission.githubuser.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.submission.githubuser.R
import com.submission.githubuser.user.CardviewUserAdapter
import com.submission.githubuser.user.SimpleUserData
import com.submission.githubuser.webapi.RetrofitInterface
import com.submission.githubuser.webapi.RetrofitInteractable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recycleView: RecyclerView
    private val retrofit = RetrofitInteractable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycleView = findViewById(R.id.user_list)
        supportActionBar?.title = resources.getString(R.string.app_name)
        showLayout()
    }

    private fun showLayout() {
        val fetch = retrofit.fetchUserFromGitHub()
        val dataAdapter = CardviewUserAdapter()
        val service = fetch.create(RetrofitInterface::class.java)
        val call = service.fetchUsers() //CALLS GET -> api.github.com/users
        call.enqueue(object: Callback<List<SimpleUserData>> {
            override fun onResponse(call: Call<List<SimpleUserData>>, response: Response<List<SimpleUserData>>) {
                if (response?.code() == 200){
                    dataAdapter.setData(response.body() as ArrayList<SimpleUserData>)
                    dataAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<SimpleUserData>>?, t: Throwable?) {
            }

        })
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.adapter = dataAdapter
        dataAdapter.setOnItemClickCallback(object: CardviewUserAdapter.OnItemClickCallback{
            override fun onItemClicked(user: String?) {
                val moveIntent = Intent(this@MainActivity, UserDetailActivity::class.java)
                moveIntent.putExtra(UserDetailActivity.EXTRA_USER, user)
                startActivity(moveIntent)
            }
        })
    }
}