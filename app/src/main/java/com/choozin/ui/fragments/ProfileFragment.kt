package com.choozin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.choozin.R
import com.choozin.managers.AuthenticationManager
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {

    val authManager = AuthenticationManager.getInstance()

    override fun onStart() {
        super.onStart()
        if (arguments != null) {

        }
        username.text = authManager.currentUser.username
        description.text = authManager.currentUser.description
        followers.text = authManager.currentUser.followers.size.toString()
        following.text = authManager.currentUser.following.size.toString()
        Glide.with(activity!!.applicationContext)
            .load("http://choozinserver-git-choozinapi.apps.us-east-2.starter.openshift-online.com" + authManager.currentUser.profileUrl)
            .apply(RequestOptions.circleCropTransform()).into(profile_image)

        profile_image.setOnClickListener {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


}
