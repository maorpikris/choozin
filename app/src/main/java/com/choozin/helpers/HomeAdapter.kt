package com.choozin.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.choozin.ChoozinApplication
import com.choozin.R
import com.choozin.managers.AuthenticationManager
import com.choozin.models.PostItem
import kotlinx.android.synthetic.main.post_item.view.*

class HomeAdapter(val posts: List<PostItem>) : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(ChoozinApplication.getAppContext())
            .inflate(R.layout.post_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val post = posts[position]
        holder.setData(post, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(post: PostItem?, pos: Int) {
            itemView.post_username.text = post!!.creator.username
            itemView.postTitle.text = post.title
            itemView.post_desc.text = post.creator.description
            if (AuthenticationManager.getInstance().currentUser.favorites.contains(post.postId)) {
                +
            }
        }
    }
}