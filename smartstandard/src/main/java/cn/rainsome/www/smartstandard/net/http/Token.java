package cn.rainsome.www.smartstandard.net.http;

import com.tencent.bugly.crashreport.CrashReport;

import cn.rainsome.www.smartstandard.App;

/**
 * Created by Yomii on 2017/2/13.
 */

public class Token {

    private static String token;

    public static String getToken() {
        return token;
    }

    public static void setToken(String newToken) {
        token = newToken;
        CrashReport.putUserData(App.getContext(),"token", token);
    }
}
