package com.src_resources.classThreeApplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.Window
import android.view.WindowManager

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class AppLauncherActivity : AppCompatActivity() {

    @SuppressLint("HandlerLeak")
    private var mHandler = object : Handler() {

        override fun handleMessage(msg: Message?) {
            when (msg?.what) {
                0x1 -> {
                    // 启动 AppMainActivity ，并接收结果。
                    val intent = Intent(this@AppLauncherActivity,
                            AppMainActivity::class.java)
                    startActivityForResult(intent, 0x1)
                }
            }
        }

    }

    /* 启动应用程序的线程。 */
    private var mLauncherThread = Thread({
        // 启动 AppVersionCheckingService 。
        kotlin.run {
            val intent = Intent(this, AppVersionCheckingService::class.java)
            startService(intent)
        }

        try {
            // 1000 毫秒后开始绘制底部滚动动画效果。
            Thread.sleep(1000)
            bottomUncertainBarView.isDrawingMovingBar = true
            // 6000 毫秒后启动 AppMainActivity 。
            Thread.sleep(6000)
            mHandler.sendEmptyMessage(0x1)
        } catch (ex: InterruptedException) {
            Log.i(resources.getString(R.string.log_tag),
                    resources.getString(R.string.log_errorMessage_appExitedBeforeLaunchFinished),
                    ex)
        }
    })

    private lateinit var bottomUncertainBarView: UncertainBarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置 Activity 全屏显示。
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_app_launcher)

        bottomUncertainBarView = findViewById(R.id.bottomUncertainBarView)
        bottomUncertainBarView.isDrawingMovingBar = false

        // 运行启动应用程序的线程。
        mLauncherThread.start()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            // 如果是 AppMainActivity 返回结果。
            0x1 -> {
                // 结束当前 Activity 。
                finishAfterTransition()
            }
        }
    }

    override fun onDestroy() {
        // 结束运行启动应用程序的线程。
        mLauncherThread.interrupt()
        // 结束 Handler 。
        mHandler.removeCallbacksAndMessages(null)

        super.onDestroy()
    }

    override fun onResume() {

        /**
         * 设置为竖屏（强制竖屏）。
         */
        if (requestedOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }

        super.onResume()
    }
}
