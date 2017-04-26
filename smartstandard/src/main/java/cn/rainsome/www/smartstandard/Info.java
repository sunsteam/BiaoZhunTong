package cn.rainsome.www.smartstandard;

import com.tencent.bugly.crashreport.CrashReport;

import cn.rainsome.www.smartstandard.bean.response.LoginResponse;
import cn.rainsome.www.smartstandard.net.http.Token;

/**
 * Created by Yomii on 2017/1/10.
 */

public class Info {

    /**
     * 用户类型
     */
    private static int kind;
    /**
     * 用户唯一编号
     */
    private static int psnNo;
    /**
     * 用户ID
     */
    private static String psnUid = "";

    /**
     * 显示用户名
     */
    private static String userName = "";

    /**
     * 企业名(可能多个)
     */
    private static String companys = "";

    /**
     * 是否签到 0 : 未签到  1 : 已签到  -1 : 默认值,没有获取到服务器信息
     */
    private static int signIn = -1;


    public static void setKind(int kind) {
        Info.kind = kind;
    }

    public static boolean isTemperToken() {
        return kind == 0;
    }

    public static boolean isPersonalToken() {
        return kind == 1;
    }

    public static boolean isCompanyToken() {
        return kind > 1;
    }

    public static int getPsnNo() {
        return psnNo;
    }

    public static void setPsnNo(int userNo) {
        psnNo = userNo;
    }

    public static String getPsnUid() {
        return psnUid;
    }

    public static void setPsnUid(String userId) {
        psnUid = userId;
        CrashReport.setUserId(psnUid);
    }


    public static String getUser() {
        return userName;
    }
    public static void setUser(String user) {
        userName = user;}

    public static String getCompany() {
        return companys;
    }

    public static void setCompany(String sCompany) {
        companys = sCompany;
    }

    public static int getSignIn() {
        return signIn;
    }

    public static void setSignIn(int signInFromServer) {
        signIn = signInFromServer;
    }

    public static void fillUserInfoAfterLogin(LoginResponse response){
        Token.setToken(response.token);
        setPsnNo(response.no);
        setPsnUid(response.uid);
        setKind(response.kind);
        setUser(response.fullname);
        setCompany(response.companys.get(0).csmcaption);
        setSignIn(response.signIn);
    }


    public static void fillFromCache() {
        // TODO: 2017/1/10
    }
}
