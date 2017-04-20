package cn.rainsome.www.smartstandard.bean.response;


import java.util.List;

import cn.rainsome.www.smartstandard.bean.Industry;
import cn.yomii.www.frame.bean.response.ListResponseBean;

/**
 * 用户关注行业
 * Created by Yomii on 2016/6/17.
 */
public class IndustryResponse extends ListResponseBean<Industry> {

    /**
     * no : 4527
     * parentno : 4508
     * caption : DA-档案行标
     * sortby : 0
     * state : 1
     */

    public List<Industry> records;

    @Override
    public List<Industry> getRecords() {
        return records;
    }
}