package cn.rainsome.www.smartstandard.bean.response;


import java.util.List;

import cn.rainsome.www.smartstandard.bean.Label;
import cn.yomii.www.frame.bean.response.ListResponseBean;

/**
 * 标准标签列表
 * Created by Yomii on 2017/2/14.
 */

public class LabelResponse extends ListResponseBean<Label> {

    public List<Label> records;

    @Override
    public List<Label> getRecords() {
        return records;
    }
}
