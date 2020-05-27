package com.choozin.infra.adapters

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.choozin.ChoozinApplication
import com.choozin.R
import com.choozin.infra.base.BaseFragment
import com.choozin.infra.base.FragmentUIManager
import com.choozin.managers.AuthenticationManager
import com.choozin.managers.HomeManager
import com.choozin.managers.PostsManager
import com.choozin.managers.ProfileManager
import com.choozin.models.PostItem
import com.choozin.ui.fragments.HomeFragment
import com.choozin.ui.fragments.ProfileFragment
import com.choozin.utils.DoubleClickListener
import kotlinx.android.synthetic.main.post_item.view.*
import java.util.*


class PostsAdapter(val posts: ArrayList<PostItem?>) :

    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        // Infalting the view, returning new holder for the view
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
        // counting the items of the current list.
        return posts.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // Calling the setData to determine the data of the current post
        if (holder is MyViewHolder) {
            val post = posts[position]
            holder.setData(post, position)
        } else if (holder is LoadingViewHolder) {
            holder.showLoading()
        }

    }

    inner class LoadingViewHolder(loadingView: View) : RecyclerView.ViewHolder(loadingView) {
        fun showLoading() {

        }
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Updating the votes in the ui.
        fun updateVotes(post: PostItem?) {
            itemView.votesl.text = post!!.getlVoters().size.toString()
            itemView.votesr.text = post.getrVoters().size.toString()
        }

        // Moving to profile fragment, setting the id of the profile owner in the manager to the current post creator.
        private fun moveToProfile(post: PostItem?) {
            if (FragmentUIManager().instance.foregroundFragment.get() !is ProfileFragment) {
                val profileFragment = ProfileFragment()
                ProfileManager().instance.idCurrentProfileUser = post!!.creator._id
                FragmentUIManager().instance.setFragment(profileFragment as BaseFragment)
                val fragmentTransaction: FragmentTransaction =
                    FragmentUIManager().instance.fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.fragment_container, profileFragment)
                fragmentTransaction.commit()
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun setData(post: PostItem?, pos: Int) {
            // Setting up the ui of the post.
            itemView.post_title.text = post!!.title
            itemView.post_username.text = post.creator!!.username
            itemView.post_desc.text = post.creator.description
            itemView.votesl.text = post.getlVoters().size.toString()
            itemView.votesr.text = post.getrVoters().size.toString()

            // Loading the images with glide.
            Glide.with(itemView.context)
                .load(post.creator?.profileUrl)
                .apply(RequestOptions.circleCropTransform()).into(itemView.post_profile_image)
            Glide.with(itemView.context)
                .load(AuthenticationManager.buildGlideUrlWithAuth(post.getrImage()))
                .into(itemView.post_rimage)
            Glide.with(itemView.context)
                .load(AuthenticationManager.buildGlideUrlWithAuth(post.getlImage()))
                .into(itemView.post_limage)

            // Turning on and off the favorite button based on whether the current user marked this post as favorite or not.
            if (post.favs.contains(AuthenticationManager.getInstance().currentUser._id)) {
                itemView.post_fav.setImageDrawable(
                    ContextCompat.getDrawable(
                        ChoozinApplication.getAppContext(),
                        R.drawable.ic_star_black_24dp
                    )
                )
            } else {
                itemView.post_fav.setImageDrawable(
                    ContextCompat.getDrawable(
                        ChoozinApplication.getAppContext(),
                        R.drawable.ic_star_border_black_24dp
                    )
                )
            }

            // If the current user is the owner of the post showing the remove button.
            if (AuthenticationManager.getInstance().currentUser._id.equals(post.creator._id)) {
                itemView.post_remove.visibility = View.VISIBLE
            } else {
                itemView.post_remove.visibility = View.GONE
            }

            // remove post button listener.
            itemView.post_remove.setOnClickListener {
                // when clicked opening a dialog
                val alertDialog =
                    AlertDialog.Builder(FragmentUIManager().instance.foregroundFragment.get()!!.context)
                        .create()
                alertDialog.setTitle("Are you sure that you want to remove this post?")
                alertDialog.setMessage("this is my app")
                alertDialog.setButton(
                    Dialog.BUTTON_POSITIVE,
                    "REMOVE",
                    DialogInterface.OnClickListener { dialog, which ->
                        // if the user clicked on the remove calling the remove function from the manager that belongs to the current fragment.
                        if (FragmentUIManager().instance.foregroundFragment.get() is ProfileFragment) {
                            ProfileManager().instance.profilePosts.remove(post)
                        } else if (FragmentUIManager().instance.foregroundFragment.get() is HomeFragment) {
                            HomeManager().instance.homePosts.remove(post)
                        }
                        // calling the remove post function to ask the server to remove the post from the database.
                        PostsManager.getInstance().removePost(post._id)
                    })
                alertDialog.setButton(
                    Dialog.BUTTON_NEGATIVE,
                    "CANCEL",
                    DialogInterface.OnClickListener { dialog, which ->

                    })
                alertDialog.show()
            }

            // fav button listener
            itemView.post_fav.setOnClickListener {
                // Determines whether the button is on or off.
                if (itemView.post_fav.drawable.constantState!!.equals(
                        ContextCompat.getDrawable(
                            ChoozinApplication.getAppContext(),
                            R.drawable.ic_star_border_black_24dp
                        )!!.constantState
                    )
                ) {
                    // updating the current user data and asking the posts manager to send a request to manipulate the post.
                    itemView.post_fav.setImageResource(R.drawable.ic_star_black_24dp)
                    AuthenticationManager.getInstance().currentUser.favorites.add(post._id)
                    PostsManager.getInstance().postsActions(post._id, "fav")
                } else {
                    // updating the current user data and asking the posts manager to send a request to manipulate the post.
                    itemView.post_fav.setImageResource(R.drawable.ic_star_border_black_24dp)
                    AuthenticationManager.getInstance().currentUser.favorites.remove(post._id)
                    PostsManager.getInstance().postsActions(post._id, "unfav")
                }
            }
            // click listener to the right image
            itemView.post_rimage.setOnClickListener(object : DoubleClickListener(500) {
                override fun onDoubleClick() {
                    // updating the ui, the current item on list, and asking the manger to send a request to the server based on the current voting state of the user.
                    val user = AuthenticationManager.getInstance().currentUser._id
                    if (user in post.getlVoters()) {

                        val newR = posts[pos]!!.getrVoters()
                        newR.add(user)
                        posts[pos]!!.setrVoters(newR)

                        val newL = posts[pos]!!.getlVoters()
                        newL.remove(user)
                        posts[pos]!!.setlVoters(newL)
                        updateVotes(post)
                        PostsManager.getInstance().postsActions(post._id, "r+l-")
                    } else if (user in post.getrVoters()) {

                        val newR = posts[pos]!!.getrVoters()
                        newR.remove(user)
                        posts[pos]!!.setrVoters(newR)
                        updateVotes(post)
                        PostsManager.getInstance().postsActions(post._id, "r-")
                    } else {

                        val newR = posts[pos]!!.getrVoters()
                        newR.add(user)
                        posts[pos]!!.setrVoters(newR)
                        updateVotes(post)
                        PostsManager.getInstance().postsActions(post._id, "r+")
                    }
                    Thread.sleep(200)
                }


            })
            // click listener for thet left image.
            itemView.post_limage.setOnClickListener(object : DoubleClickListener(500) {
                override fun onDoubleClick() {
                    // updating the ui, the current item on list, and asking the manger to send a request to the server based on the current voting state of the user.
                    val user = AuthenticationManager.getInstance().currentUser._id
                    if (user in post.getrVoters()) {
                        val newR = posts[pos]!!.getrVoters()
                        newR.remove(user)
                        posts[pos]!!.setrVoters(newR)
                        val newL = posts[pos]!!.getlVoters()
                        newL.add(user)
                        posts[pos]!!.setlVoters(newL)
                        updateVotes(post)
                        PostsManager.getInstance().postsActions(post._id, "l+r-")
                    } else if (user in post.getlVoters()) {
                        val newL = posts[pos]!!.getlVoters()
                        newL.remove(user)
                        posts[pos]!!.setlVoters(newL)
                        updateVotes(post)
                        PostsManager.getInstance().postsActions(post._id, "l-")
                    } else {
                        val newL = posts[pos]!!.getlVoters()
                        newL.add(user)
                        posts[pos]!!.setlVoters(newL)
                        updateVotes(post)
                        PostsManager.getInstance().postsActions(post._id, "l+")
                    }
                }
            })

            // making that clicking the profileimage, title or description load up the profile fragment.
            itemView.post_profile_image.setOnClickListener { moveToProfile(post) }
            itemView.post_title.setOnClickListener { moveToProfile(post) }
            itemView.post_desc.setOnClickListener { moveToProfile(post) }
        }
    }
}