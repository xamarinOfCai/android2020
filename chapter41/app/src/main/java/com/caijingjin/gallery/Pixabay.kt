package com.caijingjin.gallery

import android.net.UrlQuerySanitizer
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @ClassName Pixabay
 * @Description
 * @Author cjj
 * @Date 2020-04-21 22:29
 * @Version 1.0
 */

@Parcelize data class PhotoItem(
    @SerializedName("webformatURL")val previewUrl:String,
    @SerializedName("id") val photoId:Int,
    @SerializedName("largeImageURL") val largeImageURL:String
):Parcelable

data class Pixabay(
    val totalHits:Int,
    val hits:Array<PhotoItem>,
val total:Int
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Pixabay

        if (totalHits != other.totalHits) return false
        if (!hits.contentEquals(other.hits)) return false
        if (total != other.total) return false

        return true
    }

    override fun hashCode(): Int {
        var result = totalHits
        result = 31 * result + hits.contentHashCode()
        result = 31 * result + total
        return result
    }
}


