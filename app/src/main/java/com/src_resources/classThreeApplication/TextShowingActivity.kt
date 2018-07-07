package com.src_resources.classThreeApplication

import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText

const val INTENT_EXTRA_CONTENT_STRING = "com.src_resources.classThreeApplication.CONTENT_STRING"

class TextShowingActivity : AppCompatActivity() {

    private lateinit var mContentEditText: EditText
    private var mContentString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_showing)

        mContentEditText = findViewById(R.id.contentEditText)

        // 获取到要显示的文本。
        mContentString = intent.getStringExtra(INTENT_EXTRA_CONTENT_STRING)
        // 把文本显示出来。
        mContentEditText.text = Editable.Factory.getInstance().newEditable(mContentString)
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
