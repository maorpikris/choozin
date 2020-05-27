package com.choozin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.choozin.R
import com.choozin.infra.adapters.PostsAdapter
import com.choozin.infra.base.BaseFragment
import com.choozin.managers.HomeManager
import com.choozin.models.PostItem
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class HomeFragment : BaseFragment() {

    var isLoading: Boolean = false
    val manager = HomeManager().instance
    var postsList: ArrayList<PostItem?> = arrayListOf()
    lateinit var recyclerViewAdapter: PostsAdapter


    val TAG: String = "Home"
    override fun updateUI() {

        postsList.clear()
        postsList.addAll(manager.homePosts)
        initAdapter()


    }

    private fun populateData() {
        Log.v("dav", "its running yo")
        manager.getHomePosts()
    }

    private fun initAdapter() {
        if (swipeContainer != null) {
            swipeContainer.isRefreshing = false
        }
        if (!this::recyclerViewAdapter.isInitialized) {
            recyclerViewAdapter = PostsAdapter(manager.homePosts)
        }
        recyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (manager.homePosts != null && manager.homePosts.size > 0) {
            postsList = manager.homePosts
        }
        recyclerViewAdapter = PostsAdapter(postsList)
        homePostsRecyclerView.adapter = recyclerViewAdapter
        homePostsRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        swipeContainer.setOnRefreshListener {
            manager.setPullToRefreshActiveTrue()
            populateData()
        }

        initScrollListener()

        super.onViewCreated(view, savedInstanceState)

    }

    private fun initScrollListener() {
        homePostsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == manager.homePosts.size - 1) {
                        //bottom of list!
                        Log.v("Dab", "load more")
                        loadMore()
                        isLoading = true
                    }
                }
                isLoading = false
            }
        })
    }

    private fun loadMore() {
        populateData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        populateData()
        Log.v("dav", "its running yo")
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
