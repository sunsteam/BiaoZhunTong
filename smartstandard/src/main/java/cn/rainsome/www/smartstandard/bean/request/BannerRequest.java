package cn.rainsome.www.smartstandard.bean.request;


import cn.rainsome.www.smartstandard.net.http.Token;

/**
 *
 * Created by Yomii on 2016/4/6.
 */
public class BannerRequest extends RequestBean {

    public String app;

    public BannerRequest() {
        super("ads", Token.getToken());
        this.app = "aphone";
    }
}