package com.caijingjin.gallery

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import kotlinx.coroutines.InternalCoroutinesApi

/**
 * @ClassName GalleryViewModel
 * @Description
 * @Author cjj
 * @Date 2020-04-21 22:37
 * @Version 1.0
 */
class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    private val _photoListLive = MutableLiveData<List<PhotoItem>>()
    val photoListLive :LiveData<List<PhotoItem>>
    get() = _photoListLive


    @UseExperimental(InternalCoroutinesApi::class)
    fun fetchData(){
        var stringRequest = StringRequest(
            Request.Method.GET,
            getUrl(),
            Response.Listener {
                _photoListLive.value = Gson().fromJson(it,Pixabay::class.java).hits.toList()
            },
            Response.ErrorListener {
                Log.d("Hello ",it.toString())
            }
        )
        //note :需要添加到请求的队列里面去才能触发请求
        VollerySingleton.getInstance(getApplication()).requestQueue.add(stringRequest)

    }
    //查找的主题
    private val keyWords = arrayOf("cat", "dog", "car", "beauty", "phone", "computer", "flower", "animal","beautiful girl","doctor","rose")

    private fun getUrl():String{
        return return "https://pixabay.com/api/?key=12472743-874dc01dadd26dc44e0801d61&q=${keyWords.random()}&per_page=100"
    }
}