package com.caijingjin.viewpager

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

/**
 * @ClassName VollerySingleton
 * @Description
 * @Author cjj
 * @Date 2020-04-21 22:42
 * @Version 1.0
 */
class VollerySingleton private constructor(context:Context) {

    companion object{
        private var INSTANCE : VollerySingleton ? = null
        @InternalCoroutinesApi
        fun getInstance(context: Context)=
            INSTANCE ?: synchronized(this){
                VollerySingleton(context).also { INSTANCE = it }
            }
    }

    val requestQueue:RequestQueue by lazy{
        Volley.newRequestQueue(context.applicationContext)
    }
}