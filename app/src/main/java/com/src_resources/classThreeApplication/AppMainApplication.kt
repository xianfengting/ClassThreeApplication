package com.src_resources.classThreeApplication

import android.app.Application
import android.os.StrictMode

class AppMainApplication : Application() {

    /** 应用程序当前的版本号。 */
    lateinit var mCurrentVersion: AppVersion

    override fun onCreate() {
        super.onCreate()

        // 启动线程的 StrictMode （严格模式）。
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build())

        // 获取应用程序当前的版本号。
        mCurrentVersion = AppVersion.parse(resources.getString(R.string.app_currentVersionNumber))
    }
}
