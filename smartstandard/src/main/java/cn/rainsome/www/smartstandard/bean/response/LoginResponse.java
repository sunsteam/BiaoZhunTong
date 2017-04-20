package cn.rainsome.www.smartstandard.bean.response;

import com.alibaba.fastjson.annotation.JSONField;

import cn.yomii.www.frame.bean.response.ResponseBean;

/**
 * 登录
 * Created by Yomii on 2016/5/23.
 */
public class LoginResponse extends ResponseBean {


    public String token;

    public String fullname;

    public int kind;

    public int no;

    public String uid;

    @JSONField (name = "issignined")
    public int signIn;


    public String companys;


    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", fullname='" + fullname + '\'' +
                ", kind=" + kind +
                ", no=" + no +
                ", uid='" + uid + '\'' +
                ", signIn=" + signIn +
                ", companys='" + companys + '\'' +
                "} " + super.toString();
    }
}
