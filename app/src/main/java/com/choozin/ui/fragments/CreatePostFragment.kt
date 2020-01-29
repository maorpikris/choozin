package com.choozin.ui.fragments


import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.choozin.R
import kotlinx.android.synthetic.main.fragment_create_post.*
import kotlinx.android.synthetic.main.fragment_create_post.view.*


class CreatePostFragment : Fragment() {

    private val RIGHT = 1
    private val LEFT = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_post, container, false)
    }

    override fun onStart() {
        super.onStart()
        title_create_post.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    include.title_preview.text = "Text will be showen here"
                    return
                }
                include.title_preview.text = s.toString()
            }
        })

        include.post_rimage.setOnClickListener {
            pickImage(RIGHT)
        }
        include.post_limage.setOnClickListener {
            pickImage(LEFT)
        }
    }

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

        if (resultCode != Activity.RESULT_OK) {
            return
        }
        val image = data!!.data
        if (requestCode == RIGHT) {
            include.post_rimage.setImageURI(image)
        } else if (requestCode == LEFT) {
            include.post_limage.setImageURI(image)
        }

    }




}
