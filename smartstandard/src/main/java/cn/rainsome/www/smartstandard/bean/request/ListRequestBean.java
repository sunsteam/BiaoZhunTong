package cn.rainsome.www.smartstandard.bean.request;

import cn.yomii.www.frame.bean.ListRequest;

/**
 * 业务需要自定义
 * Created by Yomii on 2017/4/26.
 */

public class ListRequestBean<R> extends ListRequest<R> {


    @Override
    public String toString() {
        return super.toString().replace("pageIndex","pageindex").replace("pageSize","pagesize");
    }
}
