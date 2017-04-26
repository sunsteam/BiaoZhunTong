package cn.rainsome.www.smartstandard.bean.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.UUID;

import cn.rainsome.www.smartstandard.App;
import cn.rainsome.www.smartstandard.BuildConfig;
import cn.rainsome.www.smartstandard.net.http.Token;
import cn.rainsome.www.smartstandard.utils.SpfUtils;

/**
 * Created by Yomii on 2016/6/30.
 */
public class LoginRequest extends RequestBean {

    public String app = "aphone";

    public int ver = 1;

    public int os = 1;

    public String ip;

    public String mac;

    public String imei;

    public String csmid;

    public String uid;

    public String pwd;

    public String productid = BuildConfig.APPLICATION_ID;


    /**
     * 用于正常登陆, imei会从spf获取, 没有则新建
     *
     * @param csmid
     * @param uid
     * @param pwd
     */
    public LoginRequest(String csmid, String uid, String pwd) {
        super("app_login", Token.getToken());
        this.csmid = csmid;
        this.uid = uid;
        this.pwd = pwd;
        this.imei = getImei();
    }

    private String getImei() {
        SharedPreferences main = App.getContext().getSharedPreferences("main", Context.MODE_PRIVATE);
        imei = main.getString("uuid", "");
        if (!TextUtils.isEmpty(imei))
            return imei;
        return generateImei(main);
    }

    private String generateImei(SharedPreferences main) {
        String imei = UUID.randomUUID().toString();
        main.edit().putString("uuid", imei).apply();
        return imei;
    }


    /**
     * 只用于自动重试登录
     */
    private LoginRequest() {
        super("app_login",Token.getToken());
        this.imei = getImei();
        SharedPreferences spf = SpfUtils.getSpf("longinvalue");
        this.pwd = spf.getString("txt2", "");
        if (TextUtils.isEmpty(pwd)) {
            this.csmid = "";
            this.uid = "";
        } else {
            this.csmid = spf.getString("txt", "");
            this.uid = spf.getString("txt1", "");
        }
    }

    public static LoginRequest getRetryLoginRequest() {
        return new LoginRequest();
    }
}
