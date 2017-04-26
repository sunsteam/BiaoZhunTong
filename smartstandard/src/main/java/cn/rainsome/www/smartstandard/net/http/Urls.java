package cn.rainsome.www.smartstandard.net.http;


import cn.rainsome.www.smartstandard.BuildConfig;
import cn.rainsome.www.smartstandard.bean.response.ResponseBean;

/**
 * Created by Yomii on 2017/2/9.
 */

public class Urls {

    /**
     * 【正式地址】：正式服务器地址。
     */
    private static final String URL_FORMAL = "http://rsi.weboos.com:10101/";
    //public static final String URL_FORMAL = "http://192.168.0.231:10101/rsi_dev/";
    /**
     * 开发、调试接口地址（内网、公网同时有效）
     */
    private static final String URL_DEBUG = "http://rsi.weboos.com:10101/";
    //public static final String URL_DEBUG = "http://192.168.0.231:10101/rsi_dev/";
    /**
     * 模拟接口地址（内网、公网同时有效）
     * 模拟真实服务器环境的地址
     */
    //public static final String URL_DEBUG = "http://test.rainsome.cn:20202/rsi/";
    //    public static String URL_DEBUG;
    /**
     * 【应急地址】：未来作为故障应急使用。
     */
    //public static final String URL_DEBUG = "http://test.rainsome.cn:10101/rsi/";


    /**
     * API地址
     */
    private static String mainUrl = BuildConfig.LOG_DEBUG ? URL_DEBUG : URL_FORMAL;

    public static String getMainUrl() {
        return mainUrl;
    }

    public static void setMainUrl(String newUrl) {
        mainUrl = newUrl;
    }

    /**
     * 图片服务器地址
     */
    private static String imageUrl = "http://www.bzton.com/sdcpic/";
    /**
     * ???
     */
    private static String mandatoryUrl = "http://www.bzton.com/mdypic/";

    public static String getImageUrl() {
        return imageUrl;
    }

    public static void setImageUrl(String imageUrl) {
        Urls.imageUrl = imageUrl;
    }

    public static String getMandatoryUrl() {
        return mandatoryUrl;
    }

    public static void setMandatoryUrl(String mandatoryUrl) {
        Urls.mandatoryUrl = mandatoryUrl;
    }


    public static void fillUrlInfoAfterQuery(ResponseBean response) {
        // TODO: 2017/1/14
    }

    public static void fillFromCache() {
        // TODO: 2017/1/14
    }
}
