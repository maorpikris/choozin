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


    override fun updateUI() {
        if (PostsManager.getInstance().firstLoadProfilePosts) {
            postsList.addAll(PostsManager.getInstance().profilePosts)
            initAdapter()
            //initScrollListener()
        } else {
            postsList.addAll(PostsManager.getInstance().profilePosts)
        }

    }

    private fun populateData(current: Int) {

        Log.i("dab", AuthenticationManager.getInstance().currentUser._id)
        PostsManager.getInstance()
            .getProfilePosts(AuthenticationManager.getInstance().currentUser._id, current)

    }

    private fun initAdapter() {
        recyclerViewAdapter.notifyDataSetChanged()
    }

//    private fun initScrollListener() {
//        profilePostsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
//
//                if (!isLoading) {
//                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == postsList.size - 1) {
//                        //bottom of list!
//                        loadMore()
//                        isLoading = true
//                    }
//                }
//            }
//        })
//    }

    private fun loadMore() {
        postsList.add(null)
        recyclerViewAdapter.notifyItemInserted(postsList.size - 1)


        postsList.removeAt(postsList.size - 1)
        val scrollPosition = postsList.size
        recyclerViewAdapter.notifyItemRemoved(scrollPosition)
        PostsManager.getInstance().firstLoadProfilePostsToFalse()
        populateData(scrollPosition)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        username.text = authManager.currentUser.username
        description.text = authManager.currentUser.description
        Glide.with(activity!!.applicationContext)
            .load("http://choozinserver-git-choozinapi.apps.us-east-1.starter.openshift-online.com" + authManager.currentUser.profileUrl)
            .apply(RequestOptions.circleCropTransform()).into(profile_image)

        profile_image.setOnClickListener {

        }
        recyclerViewAdapter = ProfilePostsAdapter(postsList)
        profilePostsRecyclerView.adapter = recyclerViewAdapter
        profilePostsRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        populateData(0)
        return inflater.inflate(R.layout.fragment_profile, container, false)

    }


}
