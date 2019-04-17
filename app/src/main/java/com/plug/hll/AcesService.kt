package com.plug.hll

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.GestureDescription
import android.content.Context
import android.content.Intent
import android.graphics.Path
import android.media.AudioManager
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast


/*********************************************
 *  Author  JokerFish
 *  Create   2019-04-08
 *  Description
 *  Email fengzhengbiao@vcard100.com
 **********************************************/
class AcesService : AccessibilityService() {
    val TAG = "AcesService"
    override fun onInterrupt() {
        Log.i(TAG, "onInterrupt")
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }


    var catchVolum = false
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        Log.i(TAG, "onAccessibilityEvent")
        if (catchVolum.not()) {
            catchVolum = true
            volumeCatch()
            Log.i(TAG, "catchVolum ")
        }
    }


    private fun volumeCatch() {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val result = audioManager.requestAudioFocus(audioListener, AudioManager.STREAM_NOTIFICATION,
                AudioManager.AUDIOFOCUS_GAIN)
        if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            Log.i(TAG, "获取音频焦点失败")
        } else {
            Log.i(TAG, "获取音频焦点成功")
        }
    }


    private val audioListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        Log.i(TAG, "onAudioFocusChange:  $focusChange")
        Toast.makeText(this, "货拉拉新单来了", Toast.LENGTH_LONG).show()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun swipe() {
        val builder = GestureDescription.Builder().addStroke(GestureDescription.StrokeDescription(Path().apply {
            moveTo(380F, 1400F)
            lineTo(700F, 800F)
        }, 0, 300))
        dispatchGesture(builder.build(), object : AccessibilityService.GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription?) {
                Log.i("Xxqg", "swipe completed")
            }

            override fun onCancelled(gestureDescription: GestureDescription?) {
                Log.i("Xxqg", "swipe Cancelled")

            }
        }, null)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun click() {
        val builder = GestureDescription.Builder().addStroke(GestureDescription.StrokeDescription(Path().apply {
            moveTo(450F, 460F)
        }, 0, 100))
        dispatchGesture(builder.build(), object : GestureResultCallback() {
            override fun onCompleted(gestureDescription: GestureDescription?) {
                Log.i("Xxqg", "click completed")
            }

            override fun onCancelled(gestureDescription: GestureDescription?) {
                Log.i("Xxqg", "click cancelled")

            }
        }, null)
    }

}