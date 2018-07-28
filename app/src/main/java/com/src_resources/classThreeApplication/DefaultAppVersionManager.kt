package com.src_resources.classThreeApplication

/**
 * 该类提供抽象类 IAppVersionManager.Stub 的默认实现。
 * @param mLatestAppVersion 最新的 AppVersion 。
 * @param mCurrentAppVersion 当前的 AppVersion 。
 * @param mNeedsUpdate 是否需要更新。
 * @param mUpdateDownloadUrl 下载更新的 URL 地址。
 */
data class DefaultAppVersionManager(
        var mLatestAppVersion: AppVersion?,
        var mCurrentAppVersion: AppVersion?,
        var mNeedsUpdate: Boolean,
        var mUpdateDownloadUrl: String?) : IAppVersionManager.Stub() {

    override fun getLatestAppVersion(): AppVersion? {
        return mLatestAppVersion
    }

    override fun getCurrentAppVersion(): AppVersion? {
        return mCurrentAppVersion
    }

    override fun isUpdateNeeded(): Boolean {
        return mNeedsUpdate
    }

    override fun getUpdateDownloadUrl(): String? {
        return mUpdateDownloadUrl
    }
}
