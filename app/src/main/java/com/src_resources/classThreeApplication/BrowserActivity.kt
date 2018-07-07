package com.src_resources.classThreeApplication

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.view.ViewGroup.FOCUS_BLOCK_DESCENDANTS
import android.view.Window
import android.webkit.WebView
import java.io.File

const val INTENT_EXTRA_WEB_ADDRESS = "com.src_resources.classThreeApplication.WEB_ADDRESS"
const val INTENT_EXTRA_WEB_FILE = "com.src_resources.classThreeApplication.WEB_FILE"

class BrowserActivity : AppCompatActivity() {

    // TODO：修复 android.view.inputmethod 的 Bug 。
    private lateinit var mBrowserWebView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 以下代码摘录自 https://blog.csdn.net/hardworkingant/article/details/71910731 。
        // 启用窗口特征，启用带进度和不带进度的进度条
        // 【以下为摘录代码】
        requestWindowFeature(Window.FEATURE_PROGRESS)
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS)
        // 【以上为摘录代码】

        setContentView(R.layout.activity_browser)

        mBrowserWebView = findViewById(R.id.browserWebView)
        // 修复 WebView 导致的 android.view.inputmethod （ Android 输入法）的 StackOverflowError 。
        // 通过设置FOCUS_BLOCK_DESCENDANTS，可以屏蔽子控件获取焦点。页面将不会弹出输入键盘，从而实现不可编辑。
        // 通过不可编辑，来实现屏蔽输入法，从而避免输入法相关的 Exception 和 Error 。
        mBrowserWebView.descendantFocusability = FOCUS_BLOCK_DESCENDANTS

        // 获取到要用 WebView 显示的网址/文件地址。
        val webAddress = intent.getStringExtra(INTENT_EXTRA_WEB_ADDRESS)
        // 如果没有获取到网址
        if (webAddress == null || webAddress == "") {
            // 尝试获取要用 WebView 显示的文件。
            val webFile = intent.getSerializableExtra(INTENT_EXTRA_WEB_FILE) as File?
            // 如果没有获取到文件
            if (webFile == null)
            {
                // 抛出异常
                throw IllegalArgumentException(
                        "BrowserActivity didn't find any kinds of the intent arguments like " +
                                "INTENT_EXTRA_WEB_ADDRESS or INTENT_EXTRA_WEB_FILE.")
            }
            else
            {
                // 将该文件显示在 WebView 中。
                mBrowserWebView.loadUrl("file:///" + webFile.absolutePath)
            }
        }
        else
        {
            // 将该地址对应的内容显示在 WebView 中。
            mBrowserWebView.loadUrl(webAddress)
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
        // 以下代码借鉴自 https://www.jianshu.com/p/3c94ae673e2a ： 3.4.2 节
        // 在 Activity 销毁（ WebView ）的时候，先让 WebView 加载null内容，然后移除 WebView，再销毁 WebView。
        // 【以下为借鉴代码】
        mBrowserWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null)
        mBrowserWebView.clearCache(true)
        mBrowserWebView.clearHistory()
        (mBrowserWebView.parent as ViewGroup).removeView(mBrowserWebView)
        mBrowserWebView.destroy()
        // 【以上为借鉴代码】

        super.onDestroy()
    }
}
