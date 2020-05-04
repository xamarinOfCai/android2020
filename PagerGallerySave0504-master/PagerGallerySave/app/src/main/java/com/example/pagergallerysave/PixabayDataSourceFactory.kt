package com.example.pagergallerysave

import android.content.Context
import androidx.paging.DataSource


/**
 * @ClassName PixabayDataSourceFactory
 * @Description
 * @Author cjj
 * @Date 2020-05-04 10:19
 * @Version 1.0
 */
class PixabayDataSourceFactory(var context: Context) : DataSource.Factory<Int,PhotoItem>(){
    override fun create(): DataSource<Int, PhotoItem> {
        return PixabayDataSource(context)
    }

}