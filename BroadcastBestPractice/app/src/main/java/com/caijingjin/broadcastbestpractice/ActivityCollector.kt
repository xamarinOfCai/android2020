package com.caijingjin.broadcastbestpractice

import android.app.Activity

/**
 * @ClassName ActivityCollector
 * @Description
 * @Author cjj
 * @Date 2020-05-03 21:53
 * @Version 1.0
 */
object ActivityCollector {
    private val activities = ArrayList<Activity>()

    fun addActivity(activity: Activity){
        activities.add(activity)
    }
    fun removeActivity(activity:Activity){
        activities.remove(activity)
    }

    fun finishAll(){
        for (activity in activities) {
            if(!activity.isFinishing){
                activity.finish()
            }
        }
        activities.clear()
    }
}