package com.choozin.ui.fragments


import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.choozin.R
import com.choozin.infra.adapters.UserSearchAdapter
import com.choozin.infra.base.BaseFragment
import com.choozin.managers.SearchManager
import com.choozin.models.User
import kotlinx.android.synthetic.main.fragment_search.*
import java.util.*


class SearchFragment : BaseFragment() {

    var usersList: ArrayList<User?> = arrayListOf()
    val manager = SearchManager().instance
    lateinit var recyclerViewAdapter: UserSearchAdapter


    override fun updateUI() {
        usersList.clear()
        usersList.addAll(manager.users)
        initAdapter()
    }

    private fun populateData(word: String) {
        manager.getSearchUsers(word)
    }

    private fun initAdapter() {
        if (!this::recyclerViewAdapter.isInitialized) {
            recyclerViewAdapter = UserSearchAdapter(manager.users)
        }
        recyclerViewAdapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        searchField.requestFocus()
        val imm = context!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        searchField.setOnFocusChangeListener { view: View, b: Boolean ->
            if (!b) {
                imm.hideSoftInputFromWindow(searchField.windowToken, 0)

            }
        }
        searchField.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (searchField.text != null && searchField.text.isNotEmpty() && searchField.text.isNotBlank()) {
                    populateData(searchField.text.toString())
                } else {
                    manager.users.clear()
                    updateUI()
                }
            }
        })
        recyclerViewAdapter = UserSearchAdapter(usersList)
        userSearch.adapter = recyclerViewAdapter
        userSearch.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        userSearch.addItemDecoration(
            DividerItemDecoration(
                userSearch.context,
                DividerItemDecoration.VERTICAL
            )
        )
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }
}
