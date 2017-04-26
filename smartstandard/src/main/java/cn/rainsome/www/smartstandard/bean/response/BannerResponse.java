package cn.rainsome.www.smartstandard.bean.response;


/**
 * 轮播图接收
 * Created by Yomii on 2017/2/9.
 */
public class BannerResponse extends ListBean<BannerResponse.BannerInfo> {


    /**
     * src : http://192.168.0.10:9999/ads/1.jpg
     * url :
     */

    public static class BannerInfo {
        public String src;
        public String url;
    }
}
