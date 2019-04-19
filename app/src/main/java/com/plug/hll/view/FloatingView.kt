package com.plug.hll.view


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.Toast
import com.plug.hll.R

/*********************************************
 * Author  JokerFish
 * Create   2019-03-15
 * Description
 * Email fengzhengbiao@vcard100.com
 */
class FloatingView(private val mContext: Context) : LinearLayout(mContext) {
    private val mWindowManager: WindowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager


    var preP: Point? = null
    var curP: Point? = null


    init {
        initView()
    }

    private fun initView() {
        View.inflate(mContext, R.layout.layout_floating, this)
        findViewById<LinearLayout>(R.id.btn_aces).setOnClickListener {
            Toast.makeText(mContext, "开始抢单", Toast.LENGTH_SHORT).show()
            mContext.sendBroadcast(Intent("ACTION_CHANGE_WORK").apply { putExtra("WORK", true) })
        }
        findViewById<LinearLayout>(R.id.btn_close).setOnClickListener {
            Toast.makeText(mContext, "关闭悬浮框", Toast.LENGTH_SHORT).show()
            mContext.sendBroadcast(Intent("ACTION_CHANGE_WORK").apply { putExtra("WORK", false) })
            mWindowManager.removeView(this@FloatingView)
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> preP = Point(event.rawX.toInt(), event.rawY.toInt())
            MotionEvent.ACTION_MOVE -> {
                curP = Point(event.rawX.toInt(), event.rawY.toInt())
                val dx = preP?.x ?: 0 - (curP?.x ?: 0)
                val dy = curP?.y ?: 0 - (preP?.y ?: 0)
                val layoutParams = this.layoutParams as WindowManager.LayoutParams
                layoutParams.x += dx
                layoutParams.y += dy
                mWindowManager.updateViewLayout(this, layoutParams)
                preP = curP
            }
        }
        return false
    }

    companion object {
        val TAG = "FloatingView"
    }
}
