package com.submission.consumerapp.activities

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.submission.consumerapp.R
import com.submission.consumerapp.databinding.ActivityMainBinding
import com.submission.consumerapp.user.CardViewUserAdapter
import com.submission.consumerapp.user.SimpleUserData.Companion.accessibleArray
import com.submission.consumerapp.viewmodelproviders.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerview : RecyclerView
    private lateinit var viewBind : ActivityMainBinding
    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBind.root)
        recyclerview = viewBind.userList
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        showLoading(true)
        showLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_change_language -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
            R.id.refresh -> {
                viewBind.root.invalidate()
                showLayout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLayout() {
        recyclerview.layoutManager = LinearLayoutManager(this)
        val dataAdapter = CardViewUserAdapter()
        recyclerview.adapter = dataAdapter
        viewModel.getfav(this, null).observe(this, {SimpleUserData ->
            if (SimpleUserData != null){
                dataAdapter.setData(SimpleUserData)
                accessibleArray.addAll(SimpleUserData)
                showLoading(false)
            }else{
                viewBind.userList.visibility = View.GONE
                viewBind.constraintLayout.visibility = View.VISIBLE
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            viewBind.progressBar.visibility = View.VISIBLE
        } else {
            viewBind.progressBar.visibility = View.GONE
        }
    }
}