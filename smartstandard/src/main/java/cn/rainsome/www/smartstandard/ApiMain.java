package cn.rainsome.www.smartstandard;


import cn.rainsome.www.smartstandard.bean.Auth;
import cn.rainsome.www.smartstandard.bean.Industry;
import cn.rainsome.www.smartstandard.bean.Label;
import cn.rainsome.www.smartstandard.bean.Standard;
import cn.rainsome.www.smartstandard.bean.request.BannerRequest;
import cn.rainsome.www.smartstandard.bean.request.InfoRequest;
import cn.rainsome.www.smartstandard.bean.request.LabelListRequest;
import cn.rainsome.www.smartstandard.bean.request.LoginRequest;
import cn.rainsome.www.smartstandard.bean.request.NumberRequest;
import cn.rainsome.www.smartstandard.bean.request.RequestBean;
import cn.rainsome.www.smartstandard.bean.request.ResponseMsgCountRequest;
import cn.rainsome.www.smartstandard.bean.response.BannerResponse;
import cn.rainsome.www.smartstandard.bean.response.ConfigureResponse;
import cn.rainsome.www.smartstandard.bean.response.InfoResponse;
import cn.rainsome.www.smartstandard.bean.response.ListBean;
import cn.rainsome.www.smartstandard.bean.response.LoginResponse;
import cn.rainsome.www.smartstandard.bean.response.MsgCountResponse;
import cn.rainsome.www.smartstandard.bean.response.NoticeCountResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * 通用API
 * Created by Yomii on 2017/2/13.
 */

public interface ApiMain {

    /**
     * info
     *
     */
    @POST("rsi/")
    Observable<InfoResponse> info(@Body InfoRequest request);


    /**
     * 获取图片访问地址 configure
     */
    @POST("rsi/")
    Observable<ConfigureResponse> configure(@Body RequestBean request);


    /**
     * 自动登录或重试登录
     */
    @POST("rsi/")
    Observable<LoginResponse> autoLogin(@Body LoginRequest request);

    /**
     * 未读反馈数量
     */
    @POST("rsi/")
    Observable<MsgCountResponse> unreadResponse(@Body ResponseMsgCountRequest request);


    /**
     * 首页Banner信息
     */
    @POST("rsi/")
    Observable<BannerResponse> homeBanner(@Body BannerRequest request);


    /**
     * 未读通知数量 app_message_uncount
     */
    @POST("rsi/")
    Observable<NoticeCountResponse> unreadNotice(@Body RequestBean request);


    /**
     * 用户关注的行业 trade_focuslist
     */
    @POST("rsi/")
    Observable<ListBean<Industry>> focusIndustry(@Body RequestBean request);


    /**
     * 标准的标签列表
     */
    @POST("rsi/")
    Observable<ListBean<Label>> standardLabels(@Body LabelListRequest request);


    /**
     * 标准详情 app_topical_detail
     */
    @POST("rsi/")
    Observable<Standard> standardDetail(@Body NumberRequest request);


    /**
     * 用于Dao的标准详情 app_topical_detail
     */
    @POST("rsi/")
    Observable<Auth> standardAuth(@Body NumberRequest request);


}
