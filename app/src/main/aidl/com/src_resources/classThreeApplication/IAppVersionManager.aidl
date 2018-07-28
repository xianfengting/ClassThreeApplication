// AppVersionResult.aidl
package com.src_resources.classThreeApplication;

// Declare any non-default types here with import statements
import com.src_resources.classThreeApplication.AppVersion;

interface IAppVersionManager {

    /**
     * 获取最新版本的 AppVersion 。
     */
    AppVersion getLatestAppVersion();

    /**
     * 获取当前版本的 AppVersion 。
     */
    AppVersion getCurrentAppVersion();

    /**
     * 判断是否需要更新。
     */
    boolean isUpdateNeeded();

    /**
     * 获取下载更新的 URL 地址。
     */
    String getUpdateDownloadUrl();

}
