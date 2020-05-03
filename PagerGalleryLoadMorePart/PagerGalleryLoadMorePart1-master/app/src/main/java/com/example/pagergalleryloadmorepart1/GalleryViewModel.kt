package com.example.pagergalleryloadmorepart1

import android.app.Application
import android.icu.util.IslamicCalendar
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import kotlin.math.ceil

class GalleryViewModel(application: Application) : AndroidViewModel(application) {

    private val _photoListLive = MutableLiveData<List<PhotoItem>>()
    val photoListLive : LiveData<List<PhotoItem>> get() = _photoListLive
    private val _dataStatusLive = MutableLiveData<DataStatus>()
    val dataStatusLive:LiveData<DataStatus> get() = _dataStatusLive
    var needToScrollToTop = true
    private val perPage  = 50
    private val keyWords = arrayOf("beautiful+girls","rose","cat", "dog", "car", "beauty", "phone", "computer", "flower", "animal","life","sea","coffee","love","tree")
    private var currentPage = 1
    private var totalPage = 1
    private var currentKey = "rose"
    private var isNeedQuery = true
    private var isLoading = false
    init{
        resetQuery()
    }
    fun resetQuery(){
        currentPage = 1
        totalPage = 1
        currentKey = keyWords.random()
        isNeedQuery = true
        needToScrollToTop = true
        fetchData()
    }
    fun fetchData() {
        if (isLoading) {
            return
        }
        if(currentPage > totalPage){
            _dataStatusLive.value= DataStatus.DATA_STATUS_NO_MORE
            return
        }
        isLoading = true
        val stringRequest = StringRequest(
            Request.Method.GET,
            getUrl(),
            Response.Listener {
                with(Gson().fromJson(it,Pixabay::class.java)){
                    totalPage = ceil(totalHits.toDouble()/perPage).toInt()
                    if (isNeedQuery) {
                        _photoListLive.value = hits.toList()
                    }else{
                        //fix me 不太明白这个flattern的语法
                        _photoListLive.value = arrayListOf(_photoListLive.value!!,hits.toList()).flatten()
                    }
                }
                _dataStatusLive.value = DataStatus.DATA_STATUS_CAN_LOAD_MORE
                isLoading = false
                isNeedQuery = false
                currentPage++
            },
            Response.ErrorListener {
                isLoading = false
                _dataStatusLive.value = DataStatus.DATA_STATUS_NETWORK_ERROR
            }
        )
        VolleySingleton.getInstance(getApplication()).requestQueue.add(stringRequest)
    }

    private fun getUrl():String {

        return "https://pixabay.com/api/?key=15794878-07dec1c667d5756c56d42a84b&q=${currentKey}&per_page=${perPage}&&page=${currentPage}"
    }


}