package com.src_resources.classThreeApplication

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.util.AttributeSet
import android.util.Log
import android.view.View

class UncertainBarView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    companion object {
        const val MOVING_BAR_WIDTH = 500
        const val MOVING_BAR_HEIGHT = 50
    }

    private var mHandler = Handler()
    private var mXPosition: Int = 0
    private var mCounterThread: Thread
    private var mCounterThreadRunning = false
    private var mPaint = Paint()

    var isDrawingMovingBar = true
        set(value) {
            mXPosition = 0 - MOVING_BAR_WIDTH
            field = value
            Log.d(resources.getString(R.string.log_tag),
                    "UncertainBarView: Set isDrawingMovingBar to: $value")
        }

    init {
        // 最小高度为 50 px 。
        minimumHeight = MOVING_BAR_HEIGHT

        mCounterThread = object : Thread() {
            override fun run() {
                Log.d(resources.getString(R.string.log_tag),
                        "UncertainBarView: mCounterThread is starting...")
                while (mCounterThreadRunning) {
                    mXPosition += 10
                    if (mXPosition > width) {
                        mXPosition = 0 - MOVING_BAR_WIDTH
                    }
                    mHandler.post({
                        invalidate()
                        //Log.v(resources.getString(R.string.log_tag),
                        //        "UncertainBarView: Updated moving bar, mXPosition: $mXPosition")
                    })
                    Thread.sleep(25)
                }
                Log.d(resources.getString(R.string.log_tag),
                        "UncertainBarView: mCounterThread is stopping...")
            }
        }
        mCounterThread.name = "UncertainBarView-CounterThread-${mCounterThread.id}"

        mPaint.color = Color.BLUE
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mCounterThreadRunning = true
        mCounterThread.start()
    }

    override fun onDraw(canvas: Canvas?) {
        if (isDrawingMovingBar) {
            // 绘制外部的条状。
            val dOuter = resources.getDrawable(R.drawable.ic_uncettain_bar_view_outer_bar)
            dOuter.bounds = Rect(0, height - 50, width, height)
            dOuter.draw(canvas)

            // 绘制内部的条状。
            //canvas?.drawRect(Rect(mXPosition, height - 50, mXPosition + MOVING_BAR_WIDTH, height), mPaint)
            val dInner = resources.getDrawable(R.drawable.ic_uncettain_bar_view_inner_bar)
            dInner.bounds = Rect(mXPosition, height - 50, mXPosition + MOVING_BAR_WIDTH, height)
            dInner.draw(canvas)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mCounterThreadRunning = false
    }

}
