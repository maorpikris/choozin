package com.choozin.ui.fragments


import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.choozin.ChoozinApplication
import com.choozin.R
import com.choozin.infra.CropActivity
import com.choozin.infra.base.BaseFragment
import com.choozin.managers.PostsManager
import kotlinx.android.synthetic.main.fragment_create_post.*
import kotlinx.android.synthetic.main.fragment_create_post.view.*


class CreatePostFragment : BaseFragment() {

    private val RIGHT = 1
    private val LEFT = 2
    private val CROP_RIGHT = 3
    private val CROP_LEFT = 4


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_post, container, false)
    }

    override fun onStart() {
        super.onStart()
        // Setting a watcher on the title field.
        title_create_post.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Setting the preview title to the text on the field.
                if (s.isNullOrEmpty()) {
                    include.post_title.text = "Text will be showen here"
                    return
                }
                include.post_title.text = s.toString()
            }
        })

        include.post_rimage.setOnClickListener {
            pickImage(RIGHT)
        }
        include.post_limage.setOnClickListener {
            pickImage(LEFT)
        }
        createPostButton.setOnClickListener {
            createPost()
        }
    }

    // On create post asking the manager to send a request to the server.
    fun createPost() {
        val drawableRight = post_rimage.drawable as Drawable
        val drawableLeft = post_limage.drawable as Drawable
        val bitmapRight = (drawableRight as BitmapDrawable).bitmap
        val bitmapLeft = (drawableLeft as BitmapDrawable).bitmap

        PostsManager.getInstance()
            .createPost(bitmapRight, bitmapLeft, title_create_post.text.toString())
    }

    // Opening the cropimage activity with wait for result.
    fun cropImage(side: Int, uri: Uri) {
        val intent = Intent(context, CropActivity::class.java)
        intent.putExtra("image", uri)
        startActivityForResult(intent, side)
    }

    // Opening the gallery for result.
    fun pickImage(side: Int) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        if (side == LEFT) {
            startActivityForResult(intent, LEFT)
        } else if (side == RIGHT) {
            startActivityForResult(intent, RIGHT)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // checking the result and performing actions according to it.
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        val image = data!!.data
        if (requestCode == RIGHT) {
            cropImage(CROP_RIGHT, image!!)
        } else if (requestCode == LEFT) {
            cropImage(CROP_LEFT, image!!)
        } else if (requestCode == CROP_RIGHT) {
            post_rimage.setImageURI(data.getParcelableExtra("croppedImage"))
        } else if (requestCode == CROP_LEFT) {
            post_limage.setImageURI(data.getParcelableExtra("croppedImage"))
        }


    }

    override fun updateUI() {
        // show post created toast.
        Toast.makeText(ChoozinApplication.getAppContext(), "Post created", Toast.LENGTH_LONG).show()


    }


}
