package com.choozin.infra.adapters

import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.choozin.ChoozinApplication
import com.choozin.R
import com.choozin.managers.AuthenticationManager
import com.choozin.models.PostItem
import com.choozin.utils.BitmapManipulation
import kotlinx.android.synthetic.main.post_item.view.*
import java.util.*


class ProfilePostsAdapter(val posts: ArrayList<PostItem?>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ITEM) {
            val view = LayoutInflater.from(ChoozinApplication.getAppContext())
                .inflate(R.layout.post_item, parent, false)
            return MyViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_loading, parent, false)
            return LoadingViewHolder(view)
        }

    }

    override fun getItemCount(): Int {
        if (posts == null) {
            return 0
        }
        return posts.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val post = posts[position]
            holder.setData(post, position)
        } else if (holder is LoadingViewHolder) {
            holder.showLoading()
        }

    }

    inner class LoadingViewHolder(loadingView: View) : RecyclerView.ViewHolder(loadingView) {
        fun showLoading() {}
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun setData(post: PostItem?, pos: Int) {
            itemView.post_username.text = post!!.creator
            itemView.title_preview.text = post.title
            itemView.post_rimage.setImageBitmap(BitmapManipulation.StringToBitMap(post.getrImage()))
            itemView.post_limage.setImageBitmap(BitmapManipulation.StringToBitMap(post.getlImage()))
            Log.v("rimgaefromserver", post.getrImage())
            if (AuthenticationManager.getInstance().currentUser.favorites.contains(post._id)) {

            }


        }
    }
}