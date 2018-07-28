package com.src_resources.classThreeApplication

import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.ActivityInfo
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.support.v7.app.AlertDialog
import android.util.AndroidRuntimeException
import android.util.Log
import android.widget.Button
import android.widget.Toast
import java.io.InputStreamReader

class AppMainActivity : AppCompatActivity() {

    private inner class MyServiceConnection : ServiceConnection {

        var isServiceConnected: Boolean = false

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            if (name == null || service == null) {
                throw NullPointerException("The arguments must not be null.")
            } else {
                if (service is IAppVersionManager) {
                    mAppVersionManager = service
                    val latestAppVersion = mAppVersionManager?.latestAppVersion
                    // 如果需要更新。
                    if (mAppVersionManager?.isUpdateNeeded == true) {
                        AlertDialog.Builder(this@AppMainActivity)
                                .setTitle("有更新")
                                .setMessage("你的应用有更新，新版本：${latestAppVersion.toString()}\n是否更新")
                                .setPositiveButton("我要更新！", { _, _ ->
                                    if (mAppVersionManager?.updateDownloadUrl == null) {
                                        // 如果下载 URL 地址为 null 。
                                        Toast.makeText(this@AppMainActivity,
                                                resources.getText(R.string.log_errorMessage,
                                                        "无法下载更新：下载链接未知。"),
                                                Toast.LENGTH_SHORT).show()
                                    } else {
                                        // 如果下载 URL 地址不为 null 。
                                        // 跳转至下载页面。
                                        val intent = Intent(Intent.ACTION_VIEW,
                                                Uri.parse(mAppVersionManager?.updateDownloadUrl))
                                        startActivity(intent)
                                    }
                                })
                                .setNegativeButton("以后再说！", { _, _ ->
                                    /*Toast.makeText(this@AppMainActivity,
                                            "暂未实现",
                                            Toast.LENGTH_SHORT).show()*/
                                })
                                .show()
                    }
                }
                isServiceConnected = true
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            if (name == null) {
                throw NullPointerException("The argument must not be null.")
            } else {
                isServiceConnected = false
            }
        }

    }

    private var mAppVersionManager: IAppVersionManager? = null
    private lateinit var mServiceConnection : MyServiceConnection

    private lateinit var mClassIntroductionButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_main)

        mClassIntroductionButton = findViewById(R.id.classIntroductionButton)
        mClassIntroductionButton.setOnClickListener {
            /*Toast.makeText(this,
                    resources.getString(R.string.function_targetNotImplementedYet,
                            resources.getString(R.string.class_introduction)),
                    Toast.LENGTH_SHORT).show()*/

            /*Toast.makeText(this,
                    resources.getString(R.string.warningWithMessage,
                            "注意！此功能有 Bug ，尚未找到解决的方法。此 Bug 可能使应用崩溃。请小心使用。"),
                    Toast.LENGTH_LONG).show()*/

            // 启动 BrowserActivity ，显示班级介绍 html 文件。
            // 这种方法有 Bug 暂时不能使用。
            /*
            val intent = Intent(this, BrowserActivity::class.java)
            //intent.putExtra(INTENT_EXTRA_WEB_ADDRESS, "file:///android_asset/class_introduction.html")
            intent.putExtra(INTENT_EXTRA_WEB_ADDRESS, "http://www.bilibili.com/")
            startActivity(intent)
            */
            /*
            val intent2 = Intent(Intent.ACTION_VIEW, Uri.parse("file://android_asset/class_introduction.html"))
            startActivity(intent2)
            */
            kotlin.run {
                // 打开文件输入流，以读取班级简介（class_introduction.txt）内容。
                val inStream = assets.open("class_introduction.txt")
                // 创建 InputStreamReader 对象。
                val reader = InputStreamReader(inStream)
                // 创建一个足够大的 char 数组。
                val contentCharArray = CharArray(8192)
                // 把文件内容读入 char 数组，并记录读取的字符数。
                val contentLength = reader.read(contentCharArray)
                // 根据实际读取的长度和读取的内容创建 String 。
                val contentString = String(contentCharArray, 0, contentLength)
                // 关闭流。
                reader.close()
                inStream.close()

                // 把读到的内容显示出来。
                val intent = Intent(this, TextShowingActivity::class.java)
                intent.putExtra(INTENT_EXTRA_CONTENT_STRING, contentString)
                startActivity(intent)
            }
        }

        // 创建 MyServiceConnection 对象。
        mServiceConnection = MyServiceConnection()

        // 绑定到 AppVersionCheckingService 。
        kotlin.run {
            val intent = Intent(this, AppVersionCheckingService::class.java)
            if (bindService(intent, mServiceConnection, 0)) {
                // 绑定成功。
                Log.i(resources.getString(R.string.log_tag),
                        "AppMainActivity binded to AppVersionCheckingService successfully.")
            } else {
                // 绑定失败。
                // 抛出异常。
                val exception = AndroidRuntimeException("AppMainActivity binded to AppVersionCheckingService failed.")
                Log.e(resources.getString(R.string.log_tag),
                        resources.getString(R.string.log_errorMessage,
                                "AppMainActivity binded to AppVersionCheckingService failed."),
                        exception)
                throw exception
            }
        }
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

    override fun onDestroy() {
        // 解除绑定 Service 。
        unbindService(mServiceConnection)
        super.onDestroy()
    }
}
