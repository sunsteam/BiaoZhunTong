package cn.rainsome.www.smartstandard.bean.request;

import cn.rainsome.www.smartstandard.net.http.Token;
import cn.yomii.www.frame.bean.request.ListRequestBean;

/**
 * 用于标准列表的请求
 * Created by Yomii on 2016/3/13.
 */
public class StdListRequest extends ListRequestBean {



    /**
     * 1发布日期 2实施日期 3废止日期 5题录编号 6题录名称 8关注度
     */
    public int sortby;

    /**
     * 数据筛选 (有数据的在前, 无数据的在后, 然后再按照sortby排序)
     *
     * 0  关闭
     * 1  开启
     *
     */
    public int formatsort = 0;

    public StdListRequest(String cmd, int sortby, int desc){
        super(cmd, Token.getToken());
        this.sortby = sortby;
        this.desc = desc;
    }

}
