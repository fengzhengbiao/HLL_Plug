package com.plug.hll

import android.app.ActivityManager
import android.content.Context
import android.util.Log


/*********************************************
 *  Author  JokerFish
 *  Create   2019-04-18
 *  Description
 *  Email fengzhengbiao@vcard100.com
 **********************************************/
fun Context.getActivityInRunningTask() {
    val TAG = "Helper"
    val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val tasks = activityManager.runningAppProcesses
    Log.i(TAG, "get running task proccess-${tasks.size}")
    for (info in tasks) {
        Log.i(TAG, "task-----${info.processName}")

    }
}




