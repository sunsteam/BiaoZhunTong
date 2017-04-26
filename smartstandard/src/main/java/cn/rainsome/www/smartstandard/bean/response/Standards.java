package cn.rainsome.www.smartstandard.bean.response;


import cn.rainsome.www.smartstandard.bean.Standard;


/**
 * 标准列表
 * Created by Yomii on 2016/3/13.
 */
public class Standards extends ListBean<Standard> {

    /**
     * 用于数据去重判断
     */
    public int sortby;

    /**
     * 用于数据去重判断
     */
    public int desc;

    /**
     * 用于数据去重判断
     */
    public int trdno;

}
