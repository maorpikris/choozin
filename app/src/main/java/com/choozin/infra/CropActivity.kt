package com.choozin.infra

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.choozin.R
import kotlinx.android.synthetic.main.activity_crop.*
import java.io.ByteArrayOutputStream


class CropActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop)
        cropView.of(intent.getParcelableExtra("image")).withAspect(3, 5).withOutputSize(650, 1000)
            .initialize(this)

    }

    fun cropImage(view: View) {
        Log.v("a", "avdas ")
        val croppedBitmap = cropView.output
        if (croppedBitmap != null) {
            val returnIntent = Intent()
            val uri = getImageUriFromBitmap(this, croppedBitmap)
            returnIntent.putExtra("croppedImage", uri)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        } else {
            val returnIntent = Intent()
            setResult(Activity.RESULT_CANCELED, returnIntent)
            finish()
        }
    }

    fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path: String =
            MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
        return Uri.parse(Uri.decode(path))
    }


}
