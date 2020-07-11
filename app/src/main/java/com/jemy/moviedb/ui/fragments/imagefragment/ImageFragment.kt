package com.jemy.moviedb.ui.fragments.imagefragment


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.jemy.moviedb.R
import com.jemy.moviedb.utils.Constants
import com.jemy.moviedb.utils.extensions.load
import com.jemy.moviedb.utils.extensions.toastLong
import kotlinx.android.synthetic.main.fragment_image.*
import java.io.*

class ImageFragment : Fragment() {

    companion object {
        private const val REQUEST_PERMISSION_WRITE_STORAGE = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_image, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getImageUrl()
        setupSaveButtonClickListener()
    }

    private fun getImageUrl() {
        val imageUrl = arguments?.getString(Constants.IMAGE_URL)
        imageUrl.let { completeImage.load(it!!) }
    }

    private fun saveImageToGallery() {
        completeImage.let { image ->
            val draw: BitmapDrawable = image.drawable as BitmapDrawable
            val bitmap = draw.bitmap
            val filePath = Environment.getExternalStorageDirectory()
            val dir = File("${filePath.absolutePath}/MovieDb")
            dir.mkdir()
            val outFile = File(dir, "${System.currentTimeMillis()}.jpg")
            val outputStream = FileOutputStream(outFile)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            requireActivity().toastLong(getString(R.string.image_saved))
            try {
                outputStream.flush()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            try {
                outputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun setupSaveButtonClickListener() {
        saveImageButton.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION_WRITE_STORAGE
                )

            } else {
                saveImageToGallery()
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_PERMISSION_WRITE_STORAGE) {
            saveImageToGallery()
        } else {
            requireActivity().toastLong(getString(R.string.permission_not_granted))
        }
    }
}