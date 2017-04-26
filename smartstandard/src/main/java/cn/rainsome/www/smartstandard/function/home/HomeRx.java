package cn.rainsome.www.smartstandard.function.home;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.rainsome.www.smartstandard.bean.Industry;
import cn.rainsome.www.smartstandard.bean.ViewPagerBean;
import cn.rainsome.www.smartstandard.bean.request.BannerRequest;
import cn.rainsome.www.smartstandard.bean.request.InfoRequest;
import cn.rainsome.www.smartstandard.bean.request.RequestBean;
import cn.rainsome.www.smartstandard.bean.request.StableIndustryRequest;
import cn.rainsome.www.smartstandard.bean.request.SyncIndustryPositionRequest;
import cn.rainsome.www.smartstandard.bean.response.BannerResponse;
import cn.rainsome.www.smartstandard.bean.response.InfoResponse;
import cn.rainsome.www.smartstandard.bean.response.ListBean;
import cn.rainsome.www.smartstandard.bean.response.NoticeCountResponse;
import cn.rainsome.www.smartstandard.bean.response.NumberResponse;
import cn.rainsome.www.smartstandard.net.http.HttpHelper;
import cn.rainsome.www.smartstandard.net.http.Token;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;

/**
 * HomeModel的RX实现
 * Created by Yomii on 2017/2/9.
 */

public class HomeRx {

    /**
     * 获取banner信息
     */
    public Observable<List<ViewPagerBean>> getBanner() {
        return HttpHelper.getApiMain().homeBanner(new BannerRequest()).map(new Function<BannerResponse, List<ViewPagerBean>>() {

            @Override
            public List<ViewPagerBean> apply(BannerResponse bannerResponse) throws Exception {
                List<BannerResponse.BannerInfo> records = bannerResponse.getData();
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
        return HttpHelper.getApiMain().unreadNotice(new RequestBean("app_message_uncount", Token.getToken()))
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 判断服务器接口是否可用（如果不可用，提示离线模式）
     */
    public Observable<InfoResponse> getServerEnable() {
        return HttpHelper.getApiMain().info(new InfoRequest())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取用户选中的行业列表
     */
    public Observable<Industry> getIndustries() {
        return HttpHelper.getApiMain().focusIndustry(new RequestBean("trade_focuslist", Token.getToken()))
                .flatMap(new Function<ListBean<Industry>, Observable<Industry>>() {
                    @Override
                    public Observable<Industry> apply(ListBean<Industry> response) throws Exception {
                        List<Industry> records = response.getData();
                        if (records == null)
                            records = new ArrayList<>();
                        Collections.reverse(records);
                        return Observable.fromIterable(records);
                    }
                }).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取推荐的4个行业
     */
    public Observable<ListBean<Industry>> getHeadIndustries() {
        return HttpHelper.getApi().popularizeIndustry(new StableIndustryRequest())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 用户拖动行业位置时通知服务器记录
     */
    public Observable<NumberResponse> updateHeadIndustriesPosition(String nodeNos) {
        return HttpHelper.getApi().updateIndustriesPosition(new SyncIndustryPositionRequest(nodeNos))
                .observeOn(AndroidSchedulers.mainThread());
    }
}
