package com.choozin.infra

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.choozin.R
import com.choozin.infra.base.BaseActivity
import com.choozin.infra.base.BaseFragment
import com.choozin.infra.base.FragmentUIManager
import com.choozin.managers.AuthenticationManager
import com.choozin.ui.fragments.CreatePostFragment
import com.choozin.ui.fragments.HomeFragment
import com.choozin.ui.fragments.ProfileFragment
import com.choozin.ui.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private val fragmentUIManager: FragmentUIManager = FragmentUIManager.getInstance()
    private val homeFragment = HomeFragment()

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    addFragment(homeFragment)
                    fragmentUIManager.setFragment(homeFragment as BaseFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_add -> {
                    val createPostFragment = CreatePostFragment()
                    addFragment(createPostFragment)
                    fragmentUIManager.setFragment(createPostFragment as BaseFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    val profileFragment = ProfileFragment()
                    //val bundle = Bundle()
                    //bundle.putSerializable("profileUser", AuthenticationManager.getInstance().currentUser)
                    //profileFragment.arguments = bundle
                    addFragment(profileFragment)
                    fragmentUIManager.setFragment(profileFragment as BaseFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_search -> {
                    val searchFragment = SearchFragment()
                    addFragment(searchFragment)
                    fragmentUIManager.setFragment(searchFragment as BaseFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
    private lateinit var fragmentManager: FragmentManager

    override fun onStart() {
        super.onStart()
        AuthenticationManager.getInstance().setBackToInit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setSupportActionBar(toolbar as androidx.appcompat.widget.Toolbar)
        supportActionBar?.title = "Choozin"

        fragmentManager = supportFragmentManager
        addFragment(homeFragment)
    }

    private fun addFragment(fragment: Fragment) {
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }


}
