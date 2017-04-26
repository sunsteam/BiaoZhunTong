package cn.rainsome.www.smartstandard.bean.request;


import cn.rainsome.www.smartstandard.BuildConfig;

/**
 * 服务器INFO接口
 * Created by Yomii on 2017/2/9.
 */
public class InfoRequest extends RequestBean {

    public String app = "aphone";
    public int ver;

    public InfoRequest() {
        cmd = "info";
        ver = BuildConfig.VERSION_CODE;
    }
}
