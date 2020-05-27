package com.choozin.infra.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.choozin.ChoozinApplication
import com.choozin.R
import com.choozin.infra.base.BaseFragment
import com.choozin.infra.base.FragmentUIManager
import com.choozin.managers.ProfileManager
import com.choozin.models.User
import com.choozin.ui.fragments.ProfileFragment
import kotlinx.android.synthetic.main.user_search_item.view.*

class UserSearchAdapter(val users: ArrayList<User?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    // function the inflates the view of the user item
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(ChoozinApplication.getAppContext())
            .inflate(R.layout.user_search_item, parent, false)
        return MyViewHolder(view)
    }

    // Returning the current size of the given list
    override fun getItemCount(): Int {
        return users.size
    }

    // calling the setdata for the current loaded item
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is UserSearchAdapter.MyViewHolder) {
            val user = users[position]
            holder.setData(user, position)
        }

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(user: User?, pos: Int) {
            // Setting the ui and loading images with glide
            itemView.search_username.text = user!!.username
            Glide.with(itemView.context)
                .load(user.profileUrl)
                .apply(RequestOptions.circleCropTransform()).into(itemView.search_profile_picture)
            // item click listener that opens the profile fragment with the clicked user
            itemView.setOnClickListener {
                val profileFragment = ProfileFragment()
                ProfileManager().instance.idCurrentProfileUser = user._id
                FragmentUIManager().instance.setFragment(profileFragment as BaseFragment)
                val fragmentTransaction: FragmentTransaction =
                    FragmentUIManager().instance.fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, profileFragment)
                fragmentTransaction.commit()
            }
        }
    }
}