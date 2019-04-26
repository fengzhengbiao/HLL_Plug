package com.plug.hll.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.WindowManager


/*********************************************
 *  Author  JokerFish
 *  Create   2019-03-15
 *  Description
 *  Email fengzhengbiao@vcard100.com
 **********************************************/
class TrackerWindowManager(context: Context) {
    private var mContext: Context? = context
    private var mWindowManager: WindowManager? = null

    init {
        mWindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        var instance: TrackerWindowManager? = null

        fun getInstance(c: Context): TrackerWindowManager {
            if (instance == null) {
                synchronized(TrackerWindowManager::class) {
                    if (instance == null) {
                        instance = TrackerWindowManager(c)
                    }
                }
            }
            return instance!!
        }
    }

    private var mFloatingView: View? = null
    private val LAYOUT_PARAMS: WindowManager.LayoutParams by lazy {
        WindowManager.LayoutParams().apply {
            x = 0
            y = 0
            width = WindowManager.LayoutParams.WRAP_CONTENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.RIGHT.or(Gravity.TOP)
            if (Build.VERSION.SDK_INT >= 26) {
                type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                type = WindowManager.LayoutParams.TYPE_PHONE
            }
            format = PixelFormat.RGBA_8888
            flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL.or(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
        }
    }


    fun addView() {
        addViewF()
    }

    private fun addViewF() {
        if (!Settings.canDrawOverlays(mContext!!.applicationContext)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            mContext!!.startActivity(intent)
        } else {
            if (mFloatingView == null) {
                mFloatingView = FloatingView(mContext!!)
                mFloatingView!!.layoutParams = LAYOUT_PARAMS
                mWindowManager!!.addView(mFloatingView, LAYOUT_PARAMS)
            }
        }

    }


    fun removeView() {
        if (mFloatingView != null) {
            mWindowManager?.removeView(mFloatingView)
            mFloatingView = null
        }
    }


}