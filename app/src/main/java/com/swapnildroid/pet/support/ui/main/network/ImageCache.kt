package com.swapnildroid.pet.support.ui.main.network

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.util.LruCache
import android.webkit.URLUtil
import android.widget.ImageView
import com.swapnildroid.pet.support.R
import okhttp3.*
import java.io.File
import java.io.IOException
import java.io.InputStream

object ImageCache {

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .cache(
            Cache(
                directory = File(com.swapnildroid.pet.support.MyApp.instance.cacheDir, "web_image_cache"),
                maxSize = 10L * 1024L * 1024L // 10 MiB
            )
        )
        .build()

    private var memoryCache: LruCache<String, Bitmap>

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()

        // Use 1/8th of the available memory for this memory cache.
        val cacheSize = maxMemory / 8

        memoryCache = object : LruCache<String, Bitmap>(cacheSize) {

            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.byteCount / 1024
            }
        }
    }

    fun loadBitmap(url: String?, imageView: ImageView) {
        memoryCache.get(url)?.also {
            imageView.setImageBitmap(it)
        } ?: run {
            imageView.setImageResource(R.drawable.ic_baseline_pets_24)
            loadBitmap(url)
            null
        }
    }

    private fun loadBitmap(url: String?) {
        if (URLUtil.isValidUrl(url)) {
            val request = Request.Builder().url(url!!).build()
            val newCall = okHttpClient.newCall(request)
            newCall.enqueue(object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    if (response.isSuccessful) {
                        val byteStream: InputStream? = response.body?.byteStream()
                        val decodeStream = BitmapFactory.decodeStream(byteStream)
                        if (decodeStream != null) {
                            val extractThumbnail =
                                ThumbnailUtils.extractThumbnail(decodeStream, 100, 100)
                            memoryCache.put(url, extractThumbnail)
                        }
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                    println()
                }
            })
        }
    }


}