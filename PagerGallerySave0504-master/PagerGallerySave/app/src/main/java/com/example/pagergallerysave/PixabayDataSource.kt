package com.example.pagergallerysave

import android.content.Context
import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import javax.xml.transform.ErrorListener

/**
 * @ClassName PixabayDataSource
 * @Description
 * @Author cjj
 * @Date 2020-05-04 10:05
 * @Version 1.0
 */
class PixabayDataSource(var context: Context) :PageKeyedDataSource<Int,PhotoItem>() {

    private val queryWord = arrayOf("rose","love","time","book","beautiful girls","flower","cat", "dog", "car", "beauty", "phone", "computer", "flower", "animal").random()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, PhotoItem>
    ) {
        var url = "https://pixabay.com/api/?key=15794878-07dec1c667d5756c56d42a84b&q=${queryWord}&per_page=60&page=1"
        StringRequest(Request.Method.GET,url, Response.Listener {
            val dataList = Gson().fromJson(it, Pixabay::class.java).hits.toList()
            callback.onResult(dataList,null,2)
        },Response.ErrorListener {
            Log.d("cjj",it.toString())
        }).also { VolleySingleton.getInstance(context).requestQueue.add(it) }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoItem>) {

        var url = "https://pixabay.com/api/?key=15794878-07dec1c667d5756c56d42a84b&q=${queryWord}&per_page=60&page=${params.key}"
        StringRequest(Request.Method.GET,url,Response.Listener {
            val dataList = Gson().fromJson(it,Pixabay::class.java).hits.toList()
            callback.onResult(dataList,params.key+1)
        },Response.ErrorListener {
            Log.d("cjj",it.toString())
        })

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, PhotoItem>) {

    }
}