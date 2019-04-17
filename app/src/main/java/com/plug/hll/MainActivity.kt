package com.plug.hll

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }


    fun onViewClicked(view: View) {
        when (view) {
            btn_start -> {
                if (AccessibilityHelper.isAccessibilitySettingsOn(this, AcesService::class.java)) {
                    Toast.makeText(this, "服务已打开", Toast.LENGTH_LONG).show()
                } else {
                    startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
                }
            }
            btn_open -> startActivity(Intent().apply { component = ComponentName(HLL_PKG, HLL_CLZ) })
        }
    }


}
