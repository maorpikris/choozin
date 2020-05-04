package com.choozin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.choozin.R
import com.choozin.infra.adapters.ProfilePostsAdapter
import com.choozin.infra.base.BaseFragment
import com.choozin.managers.AuthenticationManager
import com.choozin.managers.PostsManager
import com.choozin.models.PostItem
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*


class ProfileFragment : BaseFragment() {

    val authManager = AuthenticationManager.getInstance()
    var isLoading: Boolean = false
    var postsList: ArrayList<PostItem?> = arrayListOf()
    lateinit var recyclerViewAdapter: ProfilePostsAdapter
    val bundle: Bundle? = this.arguments
    //val profileId = bundle!!.getString("profileId")

    override fun updateUI() {

        postsList.clear()
        postsList.addAll(PostsManager.getInstance().profilePosts)

        initAdapter()

    }

    private fun populateData() {

        Log.i("dab", AuthenticationManager.getInstance().currentUser._id)
        PostsManager.getInstance()
            .getProfilePosts(authManager.currentUser._id)

    }

    private fun initAdapter() {
        if (swipeContainer != null) {
            swipeContainer.isRefreshing = false
        }
        if (!this::recyclerViewAdapter.isInitialized) {
            recyclerViewAdapter = ProfilePostsAdapter(postsList)
        }
        recyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        username.text = authManager.currentUser.username
        description.text = authManager.currentUser.description
        Glide.with(activity!!.applicationContext)
            .load(AuthenticationManager.buildGlideUrlWithAuth(authManager.currentUser.profileUrl))
            .apply(RequestOptions.circleCropTransform()).into(profile_image)

        profile_image.setOnClickListener {

        }
        if (PostsManager.getInstance().profilePosts != null) {
            postsList = PostsManager.getInstance().profilePosts
        }
        recyclerViewAdapter = ProfilePostsAdapter(postsList)
        profilePostsRecyclerView.adapter = recyclerViewAdapter
        profilePostsRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        swipeContainer.setOnRefreshListener {
            populateData()
        }

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        populateData()
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }


}
