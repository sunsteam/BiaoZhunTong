package cn.rainsome.www.smartstandard.bean.request;


import cn.rainsome.www.smartstandard.net.http.Token;

/**
 * 首页移动行业列表顺序后向服务器同步状态
 * Created by Yomii on 2017/1/4.
 */
public class SyncIndustryPositionRequest extends RequestBean {

    public String nodnos;

    public SyncIndustryPositionRequest(String nos) {
        super("app_node_click", Token.getToken());
        this.nodnos = nos;
    }
}