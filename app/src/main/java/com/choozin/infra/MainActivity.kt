package com.choozin.infra

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.choozin.R
import com.choozin.infra.base.BaseFragment
import com.choozin.infra.base.FragmentUIManager
import com.choozin.ui.fragments.CreatePostFragment
import com.choozin.ui.fragments.HomeFragment
import com.choozin.ui.fragments.ProfileFragment
import com.choozin.ui.fragments.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val fragmentUIManager = FragmentUIManager.getInstance()

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    addFragment(HomeFragment())
                    fragmentUIManager.setFragment(HomeFragment() as BaseFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_add -> {
                    addFragment(CreatePostFragment())
                    fragmentUIManager.setFragment(CreatePostFragment() as BaseFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_profile -> {
                    addFragment(ProfileFragment())
                    fragmentUIManager.setFragment(ProfileFragment() as BaseFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_search -> {
                    addFragment(SearchFragment())
                    fragmentUIManager.setFragment(SearchFragment() as BaseFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
    lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setSupportActionBar(toolbar as androidx.appcompat.widget.Toolbar)
        supportActionBar?.title = "Choozin"
        fragmentManager = supportFragmentManager
        addFragment(HomeFragment())
    }

    private fun addFragment(fragment: Fragment) {
        if (fragment is ProfileFragment) {
            val args = Bundle()
            args.putString("currentUser", "currentUser")
            fragment.arguments = args
        }
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }


}
