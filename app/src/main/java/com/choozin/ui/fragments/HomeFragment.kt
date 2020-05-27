package com.choozin.ui.fragments

import android.os.Bundle
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

    override fun updateUI() {
        // on update ui clearing the old list and adding the items from the manager list.
        postsList.clear()
        postsList.addAll(manager.homePosts)
        initAdapter()


    }

    // Asking the manager to get the posts from the server.
    private fun populateData() {
        manager.getHomePosts()
    }

    // initializing the adapter and notifying about the change.
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
        // initializing all the critic things
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
                        //calling load more when in bottom of the list.
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
        // calling populate data and inflating the fragment.
        populateData()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
