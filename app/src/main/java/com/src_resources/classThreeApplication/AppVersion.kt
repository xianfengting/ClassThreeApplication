package com.src_resources.classThreeApplication

/**
 * 应用程序版本类。用于解析版本。<br/>
 * <br/>
 * 版本号的结构:<br/>
 * A.B(.C)(beta)(dev)<br/>
 * 其中：<br/>
 * 括号中的内容 - 可有可无。<br/>
 * 括号外的内容 - 必须有。<br/>
 * A - 主版本号。取值：在区间 [0,正无穷大) 中任取一个自然数。<br/>
 * B - 次版本号。取值：在区间 [0,正无穷大) 中任取一个自然数。<br/>
 * C - Bug 修复版本号，一个版本出现 Bug 发布紧急修复版本时递增的版本号。取值：在区间 [0,正无穷大) 中任取一个自然数。<br/>
 * beta - 测试版版本号，一个版本在其正式发布前每发布一个版本时递增的版本号。此类版本号应写作“beta+数字”的类型（没有双引号和加号），其中“数字”可以取任意的非负整数。默认值为 0 。一般而言，要使用这类版本号，最小值应取 1 ，因为 0 是作为缺省值了的。<br/>
 * dev - 开发者版版本号，一个版本在其还在开发时每发布一个版本时递增的版本号。此类版本号应写作“dev+数字”的类型（没有双引号和加号），其中“数字”可以取任意的非负整数。默认值为 0 。一般而言，要使用这类版本号，最小值应取 1 ，因为 0 是作为缺省值了的。<br/>
 * <br/>
 * 举例:<br/>
 * 1.0beta2dev6 - 表示正式版 1.0 的第 2 个测试版、第 6 个开发者版。
 * 1.5dev3 - 表示正式版 1.5 的第 3 个开发者版。
 * 2.5beta4 - 表示正式版 2.5 的第 4 个测试版。
 */
data class AppVersion(
        var mainVersionNumber: Int, var secondaryVersionNumber: Int, var betaVersionNumber: Int,
        var developerVersionNumber: Int) {

    companion object AppVersionFactory {
        fun parse(versionString: String): AppVersion {
            // 创建正则表达式，用来检测传入的版本号是否有误。
            val regex = Regex("\\d+\\.\\d+(beta\\d+)?(dev\\d+)?")
            // 判断传入的版本号是否有误。
            if (regex.matches(versionString)) {
                // 如果无误，创建对应的 AppVersion 对象。
                // 获取到主版本号和次版本号。
                val mainAndSecondaryVersionNumberStrings = Regex("\\d+\\.\\d+").findAll(versionString).elementAt(0).value
                val mainAndSecondaryVersionNumberStringArray = mainAndSecondaryVersionNumberStrings.split(".")
                val mainVersionNumberString = mainAndSecondaryVersionNumberStringArray[0]
                val mainVersionNumber = Integer.parseInt(mainVersionNumberString)
                val secondaryVersionNumberString = mainAndSecondaryVersionNumberStringArray[1]
                val secondaryVersionNumber = Integer.parseInt(secondaryVersionNumberString)
                // 获取到 beta 版本号。
                val betaVersionString = kotlin.run {
                    val betaVersionString: String
                    // 如果有 beta 版本号。
                    if (Regex("beta\\d+").containsMatchIn(versionString)) {
                        // 获取到 beta 版本号。
                        val betaVersionStringUnhandled = Regex("beta\\d+").findAll(versionString).elementAt(0).value
                        betaVersionString = betaVersionStringUnhandled.substring("beta".length)
                    } else {
                        // 返回缺省 beta 版本号 (0) 。
                        betaVersionString = "0"
                    }
                    betaVersionString
                }
                val betaVersionNumber = Integer.parseInt(betaVersionString)
                // 获取到 dev 版本号。
                val devVersionString = kotlin.run {
                    val devVersionString: String
                    // 如果有 dev 版本号。
                    if (Regex("dev\\d+").containsMatchIn(versionString)) {
                        // 获取到 dev 版本号。
                        val devVersionStringUnhandled = Regex("dev\\d+").findAll(versionString).elementAt(0).value
                        devVersionString = devVersionStringUnhandled.substring("dev".length)
                    } else {
                        // 返回缺省 dev 版本号 (0) 。
                        devVersionString = "0"
                    }
                    devVersionString
                }
                val devVersionNumber = Integer.parseInt(devVersionString)

                // 创建对应的 AppVersion 对象。
                val appVersionObj = AppVersion(mainVersionNumber, secondaryVersionNumber, betaVersionNumber, devVersionNumber)
                // 返回该 AppVersion 对象。
                return appVersionObj
            } else {
                // 如果有误，抛出异常。
                throw IllegalArgumentException("The version string is wrong: $versionString, see the javadoc of AppVersion for the usage.")
            }
        }
    }

}
