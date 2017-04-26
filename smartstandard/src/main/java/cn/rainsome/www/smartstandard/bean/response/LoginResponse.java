package cn.rainsome.www.smartstandard.bean.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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

    @SerializedName(value = "issignined")
    public int signIn;


    public List<Company> companys;


    public static class Company {

        public int csmno;

        public String csmcaption;

        @Override
        public String toString() {
            return "Company{" +
                    "csmno=" + csmno +
                    ", csmcaption='" + csmcaption + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "token='" + token + '\'' +
                ", fullname='" + fullname + '\'' +
                ", kind=" + kind +
                ", no=" + no +
                ", uid='" + uid + '\'' +
                ", signIn=" + signIn +
                ", companys=" + companys +
                "} " + super.toString();
    }
}
