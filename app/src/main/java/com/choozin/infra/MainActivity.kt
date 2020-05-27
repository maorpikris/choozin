package com.choozin.infra

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.choozin.R
import com.choozin.infra.base.BaseActivity
import com.choozin.infra.base.BaseFragment
import com.choozin.infra.base.FragmentUIManager
import com.choozin.managers.AuthenticationManager
import com.choozin.managers.ProfileManager
import com.choozin.ui.fragments.CreatePostFragment
import com.choozin.ui.fragments.HomeFragment
import com.choozin.ui.fragments.ProfileFragment
import com.choozin.ui.fragments.RandomFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private val fragmentUIManager: FragmentUIManager = FragmentUIManager().instance
    private val homeFragment = HomeFragment()

    private val mOnNavigationItemSelectedListener =
        // opening each fragment according to the selected item in the bottom navigation.
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
                    // setting the profile id to the current user id.
                    val profileFragment = ProfileFragment()
                    ProfileManager().instance.idCurrentProfileUser =
                        AuthenticationManager.getInstance().currentUser._id
                    addFragment(profileFragment)
                    fragmentUIManager.setFragment(profileFragment as BaseFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_search -> {
                    val randomFragment = RandomFragment()
                    addFragment(randomFragment)
                    fragmentUIManager.setFragment(randomFragment as BaseFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }


    override fun onStart() {
        super.onStart()
        // Setting the login state back to init.
        AuthenticationManager.getInstance().setBackToInit()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setting the top bar and its title.
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        setSupportActionBar(toolbar as androidx.appcompat.widget.Toolbar)
        supportActionBar?.title = "Choozin"

        // Setting the fragment manager on the fragment ui manager so it can be accessed from other places.
        fragmentUIManager.fragmentManager = supportFragmentManager
        // Adding the home fragment as the first one.
        addFragment(homeFragment)
        // Setting the current fragment on the fragment ui manager.
        fragmentUIManager.setFragment(homeFragment as BaseFragment)
    }

    private fun addFragment(fragment: Fragment) {
        // Replacing the current fragment with the new one.
        val fragmentTransaction: FragmentTransaction =
            fragmentUIManager.fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_container, fragment, "A")
        fragmentTransaction.commit()
    }
}
