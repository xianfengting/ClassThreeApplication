package com.src_resources.classThreeApplication

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import java.io.InputStreamReader

class AppMainActivity : AppCompatActivity() {

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
