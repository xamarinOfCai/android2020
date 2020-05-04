package com.example.pagergallerysave

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.toLiveData
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson

class GalleryViewModel(application: Application) : AndroidViewModel(application) {


    var pageDataList = PixabayDataSourceFactory(application).toLiveData(10)

    fun fetchData() {
        pageDataList.value?.dataSource?.invalidate()
    }
}