package com.submission.githubuser.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.submission.githubuser.R
import com.submission.githubuser.activities.UserDetailActivity
import com.submission.githubuser.databinding.FragmentHomeBinding
import com.submission.githubuser.user.CardViewUserAdapter
import com.submission.githubuser.user.SimpleUserData
import com.submission.githubuser.webapi.RetrofitInteractable
import com.submission.githubuser.webapi.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var recycleView: RecyclerView
    private val retrofit = RetrofitInteractable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycleView = view.findViewById(R.id.user_list)
        showLayout(view)
    }

    private fun showLayout(view: View) { //MAKE THIS AS VIEWMODEL LATER
        val fetch = retrofit.fetchUserFromGitHub()
        val dataAdapter = CardViewUserAdapter()
        val service = fetch.create(RetrofitInterface::class.java)
        val call = service.fetchUsers() //CALLS GET -> api.github.com/users
        call.enqueue(object: Callback<List<SimpleUserData>> {
            override fun onResponse(call: Call<List<SimpleUserData>>, response: Response<List<SimpleUserData>>) {
                if (response.code() == 200){
                    dataAdapter.setData(response.body() as ArrayList<SimpleUserData>)
                    dataAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<SimpleUserData>>?, t: Throwable?) {
            }

        })
        recycleView.layoutManager = LinearLayoutManager(view.context)
        recycleView.adapter = dataAdapter
        dataAdapter.setOnItemClickCallback(object: CardViewUserAdapter.OnItemClickCallback{
            override fun onItemClicked(user: String?) {
                val toUserDetails = Intent(activity, UserDetailActivity::class.java)
                toUserDetails.putExtra(UserDetailActivity.EXTRA_USER, user)
                startActivity(toUserDetails)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}