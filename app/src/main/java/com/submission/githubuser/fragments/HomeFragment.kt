package com.submission.githubuser.fragments

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.submission.githubuser.R
import com.submission.githubuser.activities.SearchResultActivity
import com.submission.githubuser.activities.UserDetailActivity
import com.submission.githubuser.databinding.FragmentHomeBinding
import com.submission.githubuser.user.CardViewUserAdapter
import com.submission.githubuser.viewmodelproviders.MainViewModel

class HomeFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private val viewBind get() = binding!!
    private lateinit var recycleView: RecyclerView
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return viewBind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView = view.findViewById(R.id.user_list)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        showLoading(true)
        showLayout()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.searchbar, menu)
        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                val intent = Intent(activity, SearchResultActivity::class.java)
                intent.putExtra(SearchResultActivity.EXTRA_QUERY, query)
                startActivity(intent)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_change_language -> {
                val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLayout() {
        val dataAdapter = CardViewUserAdapter()
        mainViewModel.fetchUser()
        mainViewModel.getUsers().observe(viewLifecycleOwner, { SimpleUserData ->
            if (SimpleUserData != null) {
                dataAdapter.setData(SimpleUserData)
                showLoading(false)
            }
        })
        recycleView.layoutManager = LinearLayoutManager(view?.context)
        recycleView.adapter = dataAdapter
        dataAdapter.setOnItemClickCallback(object : CardViewUserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: String?) {
                val toUserDetails = Intent(activity, UserDetailActivity::class.java)
                toUserDetails.putExtra(UserDetailActivity.EXTRA_USER, user)
                startActivity(toUserDetails)
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

    override fun onDestroy() {
        Log.d("ERROR SEARCH", "HOME FRAGMENT PWNED")
        super.onDestroy()
        binding = null
    }

}