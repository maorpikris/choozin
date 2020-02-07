package com.choozin.ui.fragments


import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        if (side == LEFT) {
            startActivityForResult(Intent.createChooser(intent, "Select Image from Gallery"), LEFT)
        } else if (side == RIGHT) {
            startActivityForResult(intent, RIGHT)
        }
    }

    fun cropImage(side: Int, uri: Uri) {
        try {
            val intent = Intent("com.android.camera.action.CROP")
            intent.setDataAndType(uri, "image/*")
            intent.putExtra("crop", "true")
            intent.putExtra("outputX", 180)
            intent.putExtra("outputY", 180)
            intent.putExtra("aspectX", 3)
            intent.putExtra("aspectY", 4)
            intent.putExtra("scaleUpIfNeeded", true)
            intent.putExtra("return-data", true)

            startActivityForResult(intent, side)
        } catch (ex: ActivityNotFoundException) {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (data != null) {
            val imageURI = data.data
            if (requestCode == RIGHT) {
                if (imageURI != null) {
                    cropImage(CROP_RIGHT, imageURI)
                }
            } else if (requestCode == LEFT) {
                if (imageURI != null) {
                    cropImage(CROP_LEFT, imageURI)
                }
            } else {
                val bundle = data.extras
                val bitmap: Bitmap? = bundle?.getParcelable("data")
                if (requestCode == CROP_RIGHT) {
                    post_rimage.setImageBitmap(bitmap)
                } else if (requestCode == CROP_LEFT) {
                    post_limage.setImageBitmap(bitmap)
                }
            }

        }


    }




}
