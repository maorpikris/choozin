package com.choozin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.choozin.R
import com.choozin.infra.adapters.PostsAdapter
import com.choozin.infra.base.BaseFragment
import com.choozin.infra.base.FragmentUIManager
import com.choozin.managers.ExploreManager
import com.choozin.models.PostItem
import kotlinx.android.synthetic.main.fragment_random.*
import java.util.*


class RandomFragment : BaseFragment() {

    var isLoading: Boolean = false
    val manager = ExploreManager().instance
    private var postsList: ArrayList<PostItem?> = arrayListOf()
    lateinit var recyclerViewAdapter: PostsAdapter

    override fun updateUI() {
        postsList.clear()
        postsList.addAll(manager.explorePosts)

        initAdapter()
    }

    private fun populateData() {
        Log.v("dav", "its running yo")
        manager.allowInteraction = false
        manager.getExplorePosts()
    }

    private fun initAdapter() {
        if (swipeContainer != null) {
            swipeContainer.isRefreshing = false
        }
        if (!this::recyclerViewAdapter.isInitialized) {
            recyclerViewAdapter = PostsAdapter(manager.explorePosts)
        }
        recyclerViewAdapter.notifyDataSetChanged()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        if (manager.explorePosts != null) {
            Log.v("work", "work")
            postsList.addAll(manager.explorePosts)
        }
        recyclerViewAdapter = PostsAdapter(postsList)
        randomPosts.adapter = recyclerViewAdapter
        randomPosts.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        swipeContainer.setOnRefreshListener {
            manager.refresh = true
            populateData()
        }
        searchField.setOnFocusChangeListener { view: View, b: Boolean ->
            val searchFragment = SearchFragment()
            FragmentUIManager().instance.setFragment(searchFragment as BaseFragment)
            val fragmentTransaction: FragmentTransaction =
                FragmentUIManager().instance.fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragment_container, searchFragment)
            fragmentTransaction.commit()
        }

        initScrollListener()

        super.onViewCreated(view, savedInstanceState)

    }

    private fun initScrollListener() {
        randomPosts.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == manager.explorePosts.size - 1) {
                        //bottom of list!
                        Log.v("Dab", "load more")
                        manager.loadMore = true
                        populateData()
                        isLoading = true
                    }
                }
                isLoading = false
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        populateData()
        return inflater.inflate(R.layout.fragment_random, container, false)
    }
}
