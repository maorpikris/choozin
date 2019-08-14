package com.choozin.infra

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.choozin.R
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.choozin.infra.fragments.HomeFragment
import com.choozin.infra.fragments.ProfileFragment
import com.choozin.infra.fragments.SearchFragment

class MainActivity : AppCompatActivity() {



    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                addFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_add -> {

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_profile -> {
                addFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_search -> {
                addFragment(SearchFragment())
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
        setSupportActionBar(toolbar as android.support.v7.widget.Toolbar)
        supportActionBar?.title = "Choozin"
            fragmentManager = supportFragmentManager
        addFragment(HomeFragment())
    }

    private fun addFragment(fragment: Fragment) {

        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }



}
