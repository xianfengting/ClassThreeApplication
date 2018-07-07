package com.src_resources.classThreeApplication

import org.junit.Test

import org.junit.Assert.*

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
        assertEquals(appVersionObj.betaVersionNumber, 5)
        assertEquals(appVersionObj.developerVersionNumber, 18)
    }
}
