package cn.rainsome.www.smartstandard.function.home;


import cn.rainsome.www.smartstandard.bean.response.BannerResponse;
import cn.rainsome.www.smartstandard.bean.response.IndustryResponse;
import cn.rainsome.www.smartstandard.bean.response.NoticeCountResponse;
import cn.yomii.www.frame.bean.response.ResponseBean;

/**
 * 首页Model
 * Created by Yomii on 2017/1/19.
 */
public interface HomeModel {

    /**
     * 获取banner信息
     */
    void getBanner(HomeCallback callback);

    /**
     * 获取是否有新通知
     */
    void getNewNotice(HomeCallback callback);

    /**
     * 调取Info接口判断服务器接口是否可用（如果不可用，提示离线模式）
     */
    void getServerEnable(HomeCallback callback);

    /**
     * 获取用户选中的行业列表
     */
    void getIndustries(HomeCallback callback);

    /**
     * 获取推荐的4个行业
     */
    void getHeadIndustries(HomeCallback callback);

    /**
     * 用户拖动行业位置时通知服务器记录
     */
    void updateHeadIndustriesPosition(String nodeNos);

    /**
     * 首页View回调
     */
    interface HomeCallback {
        void onGetBannerSuccess(BannerResponse response);
        void onGetNewNoticeSuccess(NoticeCountResponse response);
        void onGetServerEnableSuccess(ResponseBean response);
        void onGetServerEnableError(Exception e);
        void onGetIndustriesSuccess(IndustryResponse response);
        void onGetHeadIndustriesSuccess(IndustryResponse response);
        //该接口的成功与否不对View层造成影响，因此不做页面回调，在Model层处理其他操作，比如Log
        //void onUpdateHeadIndustriesPositionSuccess(DefaultResponse response);
    }
}
