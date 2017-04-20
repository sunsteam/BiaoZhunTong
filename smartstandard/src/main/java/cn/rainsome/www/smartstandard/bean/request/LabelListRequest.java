package cn.rainsome.www.smartstandard.bean.request;


import cn.rainsome.www.smartstandard.net.http.Token;
import cn.yomii.www.frame.bean.request.RequestBean;

/**
 * 标准的标签列表
 * Created by Yomii on 2016/8/9.
 */
public class LabelListRequest extends RequestBean {

    public int stdno;

    public LabelListRequest(int stdno) {
        super("label_loadby", Token.getToken());
        this.stdno = stdno;
    }
}
