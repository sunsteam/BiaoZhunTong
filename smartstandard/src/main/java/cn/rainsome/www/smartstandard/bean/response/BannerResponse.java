package cn.rainsome.www.smartstandard.bean.response;


import java.util.List;

import cn.yomii.www.frame.bean.ModelEntity;
import cn.yomii.www.frame.bean.response.ListResponseBean;

/**
 * 轮播图接收
 * Created by Yomii on 2017/2/9.
 */
public class BannerResponse extends ListResponseBean<BannerResponse.BannerInfo> {

    public List<BannerInfo> records;

    @Override
    public List<BannerInfo> getRecords() {
        return records;
    }

    /**
     * src : http://192.168.0.10:9999/ads/1.jpg
     * url :
     */

    public static class BannerInfo extends ModelEntity {
        public String src;
        public String url;
    }
}
