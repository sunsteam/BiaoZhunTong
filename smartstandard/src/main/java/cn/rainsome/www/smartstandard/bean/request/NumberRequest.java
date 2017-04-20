package cn.rainsome.www.smartstandard.bean.request;


import cn.rainsome.www.smartstandard.net.http.Token;
import cn.yomii.www.frame.bean.request.RequestBean;

/**
 * 通用int值请求
 * Created by Yomii on 2016/4/20.
 */
public class NumberRequest extends RequestBean {

    public int no;

    public NumberRequest(String cmd, int no) {
        super(cmd, Token.getToken());
        this.no = no;
    }


}
