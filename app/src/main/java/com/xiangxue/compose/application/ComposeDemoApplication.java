package com.xiangxue.compose.application;

import android.app.Application;

import com.kuki.base.preference.PreferencesUtil;
import com.xiangxue.network.base.BaseNetworkApi;

/**
 * Created by Allen on 2017/7/20.
 * 保留所有版权，未经允许请不要分享到互联网和其他人
 */
public class ComposeDemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        BaseNetworkApi.Companion.init(new NetworkRequestInfo(this));
        PreferencesUtil.init(this);
    }
}
