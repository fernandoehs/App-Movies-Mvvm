package com.fernandoehs.movies.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.ImageView
import java.io.*
import java.util.*

object Utils {
    var TAG = Utils.javaClass.simpleName

    private const val PATH_POSTER_MOVIES = "poster_movies"
    private const val SUFFIX_JPG = "jpg"


    fun isConnected(mContext: Context): Boolean {
        val cm = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }


    fun saveImageToStorage(bitmap: Bitmap?, fileName: String, context: Context) {
        val filename = "$fileName.$SUFFIX_JPG"
        val fos: OutputStream?
        val imagesDir = getInnerDir(context)
        val image = File(imagesDir, filename)
        if(!image.exists()){
            fos = FileOutputStream(image)
            fos.use {
                bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, it)
            }
        }
    }


    private fun getInnerDir(context: Context): String? {
        val storageDir = File(getDir(context) + PATH_POSTER_MOVIES)
        if (!storageDir.exists()) {
            storageDir.mkdir()
        }
        return getDir(context) + PATH_POSTER_MOVIES
    }

    private fun getDir(context: Context): String {
        return (Objects.requireNonNull(context.getExternalFilesDir(null as String?)) as File).absolutePath + "/"
    }

    fun showPosterFromDevice(
        idMovie: String,
        posterImageView: ImageView,
        context: Context
    ) {
        val myBitmap = BitmapFactory.decodeFile(getDir(context) + "$PATH_POSTER_MOVIES/$idMovie.$SUFFIX_JPG")
        posterImageView.setImageBitmap(myBitmap)
    }

}
