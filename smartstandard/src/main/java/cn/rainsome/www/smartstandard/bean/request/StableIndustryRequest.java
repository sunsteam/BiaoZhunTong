package cn.rainsome.www.smartstandard.bean.request;


import cn.rainsome.www.smartstandard.net.http.Token;

/**
 * 首页推荐行业列表
 * Created by Yomii on 2017/2/10.
 */
public class StableIndustryRequest extends RequestBean {

    public int parentno;

    public int keep = 1;

    public StableIndustryRequest() {
        super("app_node_pngloadby", Token.getToken());
        parentno = 4508;
    }
}
