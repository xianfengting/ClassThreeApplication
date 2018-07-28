package com.src_resources.classThreeApplication

import android.os.Parcel
import android.os.Parcelable

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
 * C - Bug 修复版本号（ Debug 版本号），一个版本出现 Bug 发布紧急修复版本时递增的版本号。取值：在区间 [0,正无穷大) 中任取一个自然数。一般而言，要使用这类版本号，最小值应取 1 ，因为 0 是作为缺省值了的。<br/>
 * beta - 测试版版本号，一个版本在其正式发布前每发布一个版本时递增的版本号。此类版本号应写作“beta+数字”的类型（没有双引号和加号），其中“数字”可以取任意的非负整数。默认值为 0 。一般而言，要使用这类版本号，最小值应取 1 ，因为 0 是作为缺省值了的。<br/>
 * dev - 开发者版版本号，一个版本在其还在开发时每发布一个版本时递增的版本号。此类版本号应写作“dev+数字”的类型（没有双引号和加号），其中“数字”可以取任意的非负整数。默认值为 0 。一般而言，要使用这类版本号，最小值应取 1 ，因为 0 是作为缺省值了的。<br/>
 * <br/>
 * 举例:<br/>
 * 1.0beta2dev6 - 表示正式版 1.0 的第 2 个测试版、第 6 个开发者版。
 * 1.5dev3 - 表示正式版 1.5 的第 3 个开发者版。
 * 2.5beta4 - 表示正式版 2.5 的第 4 个测试版。
 */
data class AppVersion(
        var mainVersionNumber: Int, var secondaryVersionNumber: Int, var debugVersionNumber: Int,
        var betaVersionNumber: Int, var developerVersionNumber: Int) : Comparable<AppVersion>, Parcelable {

    companion object AppVersionFactory {
        @JvmField
        final val CREATOR: Parcelable.Creator<AppVersion> = object : Parcelable.Creator<AppVersion> {
            override fun createFromParcel(source: Parcel?): AppVersion {
                if (source == null) {
                    throw IllegalArgumentException("The Parcel object is null.")
                } else {
                    return AppVersion(source)
                }
            }

            override fun newArray(size: Int): Array<AppVersion?> {
                return arrayOfNulls(size)
            }
        }

        fun parse(versionString: String): AppVersion {
            // 创建正则表达式，用来检测传入的版本号是否有误。
            val regex = Regex("\\d+\\.\\d+(\\.\\d+)?(beta\\d+)?(dev\\d+)?")
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
                // 获取到 Debug 版本号。
                val debugVersionString = kotlin.run {
                    var debugVersionString: String
                    // 如果有 Debug 版本号或次版本号。
                    if (Regex("\\.\\d+").containsMatchIn(versionString)) {
                        try {
                            // 获取到 Debug 版本号。
                            val debugVersionStringUnhandled = Regex("\\.\\d+").findAll(versionString).elementAt(1).value
                            debugVersionString = debugVersionStringUnhandled.substring(".".length)
                        } catch (ex: IndexOutOfBoundsException) {
                            // 如果获取 Debug 版本号失败（没有 Debug 版本号）。
                            // 返回缺省 Debug 版本号 (0) 。
                            debugVersionString = "0"
                        }
                    } else {
                        // 返回缺省 Debug 版本号 (0) 。
                        debugVersionString = "0"
                    }
                    debugVersionString
                }
                val debugVersionNumber = Integer.parseInt(debugVersionString)
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
                val appVersionObj = AppVersion(mainVersionNumber, secondaryVersionNumber, debugVersionNumber, betaVersionNumber, devVersionNumber)
                // 返回该 AppVersion 对象。
                return appVersionObj
            } else {
                // 如果有误，抛出异常。
                throw IllegalArgumentException("The version string is wrong: $versionString, see the javadoc of AppVersion for the usage.")
            }
        }
    }

    constructor() : this(0, 0, 0, 0, 0)
    constructor(source: Parcel) : this(source.readInt(), source.readInt(), source.readInt(),
            source.readInt(), source.readInt())

    override fun compareTo(other: AppVersion): Int {
        // 比较主版本号。
        when {
            this.mainVersionNumber > other.mainVersionNumber -> return 1
            this.mainVersionNumber < other.mainVersionNumber -> return -1
            else -> {
                // 比较次版本号。
                when {
                    this.secondaryVersionNumber > other.secondaryVersionNumber -> return 1
                    this.secondaryVersionNumber < other.secondaryVersionNumber -> return -1
                    else -> {
                        // 比较 Debug 版本号。
                        when {
                            this.debugVersionNumber > other.debugVersionNumber -> return 1
                            this.debugVersionNumber < other.debugVersionNumber -> return -1
                            else -> {
                                // 比较 Beta 版本号。
                                when {
                                    this.betaVersionNumber > other.betaVersionNumber -> return 1
                                    this.betaVersionNumber < other.betaVersionNumber -> return -1
                                    else -> {
                                        // 比较 Dev 版本号。
                                        when {
                                            this.developerVersionNumber > other.developerVersionNumber -> return 1
                                            this.developerVersionNumber < other.developerVersionNumber -> return -1
                                            else -> return 0
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        if (dest == null) {
            throw IllegalArgumentException("The Parcel object is null.")
        } else {
            // 将属性写入 Parcel 对象。
            dest.writeInt(mainVersionNumber)
            dest.writeInt(secondaryVersionNumber)
            dest.writeInt(debugVersionNumber)
            dest.writeInt(betaVersionNumber)
            dest.writeInt(developerVersionNumber)
        }
    }

    /**
     * 从 Parcel 对象中读取数据。
     * 这个方法并没有定义在 Parcelable 接口中。
     * 需要自己写。
     * 并且不用 override 。
     * @param source Parcel 对象。
     */
    fun readFromParcel(source: Parcel?) {
        if (source == null) {
            throw IllegalArgumentException("The Parcel object is null.")
        } else {
            // 注意，此处的读值顺序应当是和 writeToParcel() 方法中一致的！
            mainVersionNumber = source.readInt()
            secondaryVersionNumber = source.readInt()
            debugVersionNumber = source.readInt()
            betaVersionNumber = source.readInt()
            developerVersionNumber = source.readInt()
        }
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        val builder = StringBuilder()
        builder.append(mainVersionNumber)
        builder.append(".")
        builder.append(secondaryVersionNumber)
        builder.append(".")
        builder.append(debugVersionNumber)
        builder.append("beta")
        builder.append(betaVersionNumber)
        builder.append("dev")
        builder.append(developerVersionNumber)
        return builder.toString()
    }
}
