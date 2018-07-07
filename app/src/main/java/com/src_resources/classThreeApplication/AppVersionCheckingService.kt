package com.src_resources.classThreeApplication

import android.app.Application
import android.app.Service
import android.content.Intent
import android.os.IBinder
import org.json.JSONObject
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.os.StrictMode
import android.util.Log


class AppVersionCheckingService : Service() {

    private lateinit var mApplicationObj: AppMainApplication
    private lateinit var mAppVersionCheckingThread: Thread

    override fun onCreate() {
        super.onCreate()

        // 启动线程的 StrictMode （严格模式）。
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build())

        mApplicationObj = application as AppMainApplication
        mAppVersionCheckingThread = object : Thread("AppVersionCheckingService-mAppVersionCheckingThread") {
            override fun run() {
                // 启动当前线程的 StrictMode （严格模式）。
                kotlin.run {
                    StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder()
                            .detectDiskReads()
                            .detectDiskWrites()
                            .detectNetwork()
                            .penaltyLog()
                            .build())
                }

                // 创建 URL ，以便从 Github 上获取最新版本的 JSON 数据。
                val latestVersionJsonUrl = URL("https://api.github.com/repos/xianfengting/ClassThreeApplication/releases/latest")
                // 打开该 URL 的 HttpURLConnection 。
                val latestVersionJsonUrlConnection = latestVersionJsonUrl.openConnection() as HttpURLConnection
                // 设置 HttpURLConnection 的请求方法。
                latestVersionJsonUrlConnection.requestMethod = "GET"
                // 设置 HttpURLConnection 的请求属性。
                // 修复了此处的 Bug ，参考自 https://my.oschina.net/u/133352/blog/96582 。
                latestVersionJsonUrlConnection.setRequestProperty("Accept-Encoding", "identity")
                // 设置 HttpURLConnection 的请求属性。
                latestVersionJsonUrlConnection.setRequestProperty("content-type", "text/html")
                // 连接该 HttpURLConnection （连接到服务器）。
                latestVersionJsonUrlConnection.connect()
                // 获取该 HttpURLConnection 的输入流。
                val latestVersionJsonInputStream = latestVersionJsonUrlConnection.inputStream
                // 创建一个足够大的 Byte 类型的数组。
                val latestVersionJsonByteArray = ByteArray(latestVersionJsonUrlConnection.contentLength)
                // 定义一个字符串变量，用于存储 JSON 数据字符串。
                var latestVersionJsonString = ""
                try {
                    // 通过循环，分步读取 JSON 数据。
                    // 已经成功读取的字节的个数
                    var readCount = 0
                    while (readCount < latestVersionJsonUrlConnection.contentLength) {
                        readCount += latestVersionJsonInputStream.read(latestVersionJsonByteArray,
                                readCount, latestVersionJsonUrlConnection.contentLength - readCount)
                    }
                    // 根据实际读取的内容及长度创建 JSON 数据字符串。
                    latestVersionJsonString = String(latestVersionJsonByteArray, 0,
                            latestVersionJsonUrlConnection.contentLength)
                } finally {
                    // 关闭输入流。
                    latestVersionJsonInputStream.close()
                }

                // 根据 JSON 数据字符串创建 JSONObject 对象。
                val jsonObject = JSONObject(latestVersionJsonString)
                // 获取到最新版本的版本号。
                val latestVersionNumberString = jsonObject.getString("tag_name")
                // 根据获取到的版本号创建 AppVersion 对象。
                val latestVersionNumber = AppVersion.parse(latestVersionNumberString)
                // 判断两个版本号的大小。
                when (latestVersionNumber.compareTo(mApplicationObj.mCurrentVersion)) {
                    // 如果最新版本的版本号比当前版本的版本号大。
                    1 -> {
                        // TODO：执行操作。
                        Log.i(resources.getString(R.string.log_tag),
                                "AppVersionCheckingService checked: There is a newer version than current version. Updating needed.")
                    }
                    // 如果最新版本的版本号和当前版本的版本号相同或比当前版本的版本号小。
                    else -> {
                        // TODO：执行操作。
                        Log.i(resources.getString(R.string.log_tag),
                                "AppVersionCheckingService checked: There isn't a newer version than current version. Updating not needed.")
                    }
                }
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // 启动应用程序版本检查线程。
        mAppVersionCheckingThread.start()
        // 返回 START_STICKY ，保证 Service 被杀死后重启。
        return START_STICKY
    }
}
