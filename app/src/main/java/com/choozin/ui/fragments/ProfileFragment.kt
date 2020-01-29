package com.choozin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.choozin.R
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {




    override fun onStart() {
        super.onStart()
        Glide.with(activity!!.applicationContext).load(R.drawable.p1).apply(RequestOptions.circleCropTransform()).into(profile_image)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }



}
