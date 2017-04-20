package cn.rainsome.www.smartstandard.bean.request;

/**
 * 首页标准列表
 * Created by Yomii on 2017/2/14.
 */

public class HomeStdRequest extends StdListRequest {

    /**
     * 行业节点号
     */
    public int trdno;

    public HomeStdRequest(int trdno, int sortby, int desc) {
        super("app_topical_bytrade", sortby, desc);
        this.trdno = trdno;
    }
}
