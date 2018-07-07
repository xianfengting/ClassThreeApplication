package com.src_resources.classThreeApplication

import org.junit.Test

import org.junit.Assert.*
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class AppMainUnitTest {

    /**
     * 测试 AppVersion 是否有 bug 。
     */
    @Test
    fun debug_1_appVersionTools_appVersionClass() {
        //assertEquals(4, 2 + 2)

        // 创建 AppVersion 对象。
        val appVersionObj = AppVersion.parse("1.0")
        // 进行断言。
        assertEquals(appVersionObj.mainVersionNumber, 1)
        assertEquals(appVersionObj.secondaryVersionNumber, 0)
        assertEquals(appVersionObj.debugVersionNumber, 0)
        assertEquals(appVersionObj.betaVersionNumber, 0)
        assertEquals(appVersionObj.developerVersionNumber, 0)
    }

    /**
     * 测试 AppVersion 是否有 bug 。
     */
    @Test
    fun debug_2_appVersionTools_appVersionClass() {
        //assertEquals(4, 2 + 2)

        // 创建 AppVersion 对象。
        val appVersionObj = AppVersion.parse("1.1beta2")
        // 进行断言。
        assertEquals(appVersionObj.mainVersionNumber, 1)
        assertEquals(appVersionObj.secondaryVersionNumber, 1)
        assertEquals(appVersionObj.debugVersionNumber, 0)
        assertEquals(appVersionObj.betaVersionNumber, 2)
        assertEquals(appVersionObj.developerVersionNumber, 0)
    }

    /**
     * 测试 AppVersion 是否有 bug 。
     */
    @Test
    fun debug_3_appVersionTools_appVersionClass() {
        //assertEquals(4, 2 + 2)

        // 创建 AppVersion 对象。
        val appVersionObj = AppVersion.parse("1.5dev4")
        // 进行断言。
        assertEquals(appVersionObj.mainVersionNumber, 1)
        assertEquals(appVersionObj.secondaryVersionNumber, 5)
        assertEquals(appVersionObj.debugVersionNumber, 0)
        assertEquals(appVersionObj.betaVersionNumber, 0)
        assertEquals(appVersionObj.developerVersionNumber, 4)
    }

    /**
     * 测试 AppVersion 是否有 bug 。
     */
    @Test
    fun debug_4_appVersionTools_appVersionClass() {
        //assertEquals(4, 2 + 2)

        // 创建 AppVersion 对象。
        val appVersionObj = AppVersion.parse("2.3beta5dev18")
        // 进行断言。
        assertEquals(appVersionObj.mainVersionNumber, 2)
        assertEquals(appVersionObj.secondaryVersionNumber, 3)
        assertEquals(appVersionObj.debugVersionNumber, 0)
        assertEquals(appVersionObj.betaVersionNumber, 5)
        assertEquals(appVersionObj.developerVersionNumber, 18)
    }

    /**
     * 测试 AppVersion 是否有 bug 。
     */
    @Test
    fun debug_5_appVersionTools_appVersionClass() {
        //assertEquals(4, 2 + 2)

        // 创建 AppVersion 对象。
        val appVersionObj = AppVersion.parse("2.3.5beta5dev18")
        // 进行断言。
        assertEquals(appVersionObj.mainVersionNumber, 2)
        assertEquals(appVersionObj.secondaryVersionNumber, 3)
        assertEquals(appVersionObj.debugVersionNumber, 5)
        assertEquals(appVersionObj.betaVersionNumber, 5)
        assertEquals(appVersionObj.developerVersionNumber, 18)
    }

    /**
     * 测试 AppVersion 是否有 bug 。
     */
    @Test
    fun debug_6_appVersionTools_appVersionClass() {
        //assertEquals(4, 2 + 2)

        // 创建 AppVersion 对象。
        val appVersionObj = AppVersion.parse("2.3.5beta5dev18")
        val appVersionObj2 = AppVersion.parse("2.3.5beta5dev15")
        // 进行断言。
        assertEquals(appVersionObj.compareTo(appVersionObj2), 1)
    }

    /**
     * 测试 AppVersion 是否有 bug 。
     */
    @Test
    fun debug_1_appVersionCheckingService_checkAppVersion() {
        //assertEquals(4, 2 + 2)

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

        System.out.println(latestVersionJsonString)

        // 断言。
        // 这里不用断言。
        // assertEquals(latestVersionJsonString.length, latestVersionJsonUrlConnection.contentLength)
    }
}
