package cn.rainsome.www.smartstandard.bean.request;


import cn.rainsome.www.smartstandard.net.http.Token;

/**
 * 反馈条数
 * Created by Yomii on 2016/8/22.
 */
public class ResponseMsgCountRequest extends RequestBean {

    public int tilno;

    public ResponseMsgCountRequest() {
        super("title_unreadcount", Token.getToken());
        tilno = 0;
    }
}
