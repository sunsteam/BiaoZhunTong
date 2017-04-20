package cn.rainsome.www.smartstandard.function.home;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.rainsome.www.smartstandard.Api;
import cn.rainsome.www.smartstandard.MainApi;
import cn.rainsome.www.smartstandard.bean.Industry;
import cn.rainsome.www.smartstandard.bean.ViewPagerBean;
import cn.rainsome.www.smartstandard.bean.response.BannerResponse;
import cn.rainsome.www.smartstandard.bean.response.IndustryResponse;
import cn.rainsome.www.smartstandard.bean.response.InfoResponse;
import cn.rainsome.www.smartstandard.bean.response.NoticeCountResponse;
import cn.rainsome.www.smartstandard.bean.response.NumberResponse;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * HomeModel的RX实现
 * Created by Yomii on 2017/2/9.
 */

public class HomeRx {

    /**
     * 获取banner信息
     */
    public Observable<List<ViewPagerBean>> getBanner() {
        return MainApi.homeBanner("home").map(new Func1<BannerResponse, List<ViewPagerBean>>() {
            @Override
            public List<ViewPagerBean> call(BannerResponse bannerResponse) {
                List<BannerResponse.BannerInfo> records = bannerResponse.records;
                if (records == null)
                    records = new ArrayList<>();
                ArrayList<ViewPagerBean> beanList = new ArrayList<>();
                for (BannerResponse.BannerInfo record : records) {
                    beanList.add(new ViewPagerBean(record.src, record.url, false, null));
                }
                return beanList;
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取是否有新通知
     */
    public Observable<NoticeCountResponse> getNewNotice() {
        return MainApi.unreadNotice("home").observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 判断服务器接口是否可用（如果不可用，提示离线模式）
     */
    public Observable<InfoResponse> getServerEnable() {
        return MainApi.info("home").observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取用户选中的行业列表
     */
    public Observable<Industry> getIndustries() {
        return MainApi.focusIndustry("home").flatMap(new Func1<IndustryResponse, Observable<Industry>>() {
            @Override
            public Observable<Industry> call(IndustryResponse industryResponse) {
                List<Industry> records = industryResponse.records;
                Collections.reverse(records);
                return Observable.from(records);
            }
        }).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取推荐的4个行业
     */
    public Observable<IndustryResponse> getHeadIndustries() {
        return Api.popularizeIndustry("home").observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 用户拖动行业位置时通知服务器记录
     */
    public Observable<NumberResponse> updateHeadIndustriesPosition(String nodeNos) {
        return Api.updateIndustriesPosition(nodeNos, "home");
    }
}
