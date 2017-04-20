package cn.rainsome.www.smartstandard;

import com.lzy.okgo.request.BaseBodyRequest;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okrx.RxAdapter;

import cn.rainsome.www.smartstandard.bean.request.BannerRequest;
import cn.rainsome.www.smartstandard.bean.request.InfoRequest;
import cn.rainsome.www.smartstandard.bean.request.LabelListRequest;
import cn.rainsome.www.smartstandard.bean.request.LoginRequest;
import cn.rainsome.www.smartstandard.bean.request.NumberRequest;
import cn.rainsome.www.smartstandard.bean.request.ResponseMsgCountRequest;
import cn.rainsome.www.smartstandard.bean.response.BannerResponse;
import cn.rainsome.www.smartstandard.bean.response.ConfigureResponse;
import cn.rainsome.www.smartstandard.bean.response.IndustryResponse;
import cn.rainsome.www.smartstandard.bean.response.InfoResponse;
import cn.rainsome.www.smartstandard.bean.response.LoginResponse;
import cn.rainsome.www.smartstandard.bean.response.NoticeCountResponse;
import cn.rainsome.www.smartstandard.net.http.HttpHelper;
import cn.rainsome.www.smartstandard.net.http.JsonConvert;
import cn.rainsome.www.smartstandard.net.http.Token;
import cn.yomii.www.frame.bean.request.RequestBean;
import rx.Observable;


/**
 * 通用API
 * Created by Yomii on 2017/2/13.
 */

public class MainApi {

    /**
     * info
     * @param tag tag 用于取消请求
     */
    public static Observable<InfoResponse> info(Object tag){
        return HttpHelper.post(new InfoRequest(), tag)
                .getCall(new JsonConvert<InfoResponse>() {}, RxAdapter.<InfoResponse>create());
    }


    /**
     * configure 获取图片访问地址
     * @param tag tag 用于取消请求
     */
    public static Observable<ConfigureResponse> configure(Object tag){
        return HttpHelper.post(new RequestBean("configure",null), tag)
                .getCall(new JsonConvert<ConfigureResponse>() {}, RxAdapter.<ConfigureResponse>create());
    }


    /**
     * 自动登录或重试登录
     * @param tag tag 用于取消请求
     */
    public static Observable<LoginResponse> autoLogin(Object tag){
        return HttpHelper.post(LoginRequest.getRetryLoginRequest(), tag)
                .getCall(new JsonConvert<LoginResponse>() {}, RxAdapter.<LoginResponse>create());
    }


    /**
     * 未读反馈数量
     * @param tag tag 用于取消请求
     */
    public static BaseBodyRequest<PostRequest> unreadResponse(Object tag){
        return HttpHelper.post(new ResponseMsgCountRequest(), tag);
    }


    /**
     * 首页Banner信息
     * @param tag tag 用于取消请求
     */
    public static Observable<BannerResponse> homeBanner(Object tag){
        return HttpHelper.post(new BannerRequest(), tag)
                .getCall(new JsonConvert<BannerResponse>() {}, RxAdapter.<BannerResponse>create());
    }


    /**
     * 未读通知数量
     * @param tag tag 用于取消请求
     */
    public static Observable<NoticeCountResponse> unreadNotice(Object tag){
        return HttpHelper.post(new RequestBean("app_message_uncount", Token.getToken()), tag)
                .getCall(new JsonConvert<NoticeCountResponse>() {}, RxAdapter.<NoticeCountResponse>create());
    }


    /**
     * 用户关注的行业
     * @param tag tag 用于取消请求
     */
    public static Observable<IndustryResponse> focusIndustry(Object tag){
        return HttpHelper.post(new RequestBean("trade_focuslist", Token.getToken()), tag)
                .getCall(new JsonConvert<IndustryResponse>() {}, RxAdapter.<IndustryResponse>create());
    }


    /**
     * 标准的标签列表
     * @param tag tag 用于取消请求
     */
    public static BaseBodyRequest<PostRequest> standardLabels(int stdNo, Object tag){
        return HttpHelper.post(new LabelListRequest(stdNo), tag);
    }

    /**
     * 标准详情
     * @param tag tag 用于取消请求
     */
    public static BaseBodyRequest<PostRequest> standardDetail(int stdNo, Object tag){
        return HttpHelper.post(new NumberRequest("app_topical_detail", stdNo), tag);
    }



}
