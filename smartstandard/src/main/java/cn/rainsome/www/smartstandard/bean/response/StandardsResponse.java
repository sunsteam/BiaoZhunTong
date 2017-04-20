package cn.rainsome.www.smartstandard.bean.response;


import java.util.List;

import cn.rainsome.www.smartstandard.bean.Standard;
import cn.yomii.www.frame.bean.response.ListResponseBean;


/**
 * 标准列表
 * Created by Yomii on 2016/3/13.
 */
public class StandardsResponse extends ListResponseBean<Standard> {

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

    public List<Standard> records;

    @Override
    public List<Standard> getRecords() {
        return records;
    }
}
