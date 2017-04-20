package cn.rainsome.www.smartstandard;

import com.lzy.okrx.RxAdapter;

import cn.rainsome.www.smartstandard.bean.request.StableIndustryRequest;
import cn.rainsome.www.smartstandard.bean.request.SyncIndustryPositionRequest;
import cn.rainsome.www.smartstandard.bean.response.IndustryResponse;
import cn.rainsome.www.smartstandard.bean.response.NumberResponse;
import cn.rainsome.www.smartstandard.net.http.HttpHelper;
import cn.rainsome.www.smartstandard.net.http.JsonConvert;
import rx.Observable;

/**
 * 版本独有或可能变化的API
 * Created by Yomii on 2017/2/13.
 */

public class Api {

    /**
     * 首页推荐的行业
     *
     * @param tag tag 用于取消请求
     */
    public static Observable<IndustryResponse> popularizeIndustry(Object tag) {
        return HttpHelper.post(new StableIndustryRequest(), tag)
                .getCall(new JsonConvert<IndustryResponse>() {}, RxAdapter.<IndustryResponse>create());
    }

    /**
     * 保存推荐行业列表的位置变化
     *
     * @param tag tag 用于取消请求
     */
    public static Observable<NumberResponse> updateIndustriesPosition(String nodeNos, Object tag) {
        return HttpHelper.post(new SyncIndustryPositionRequest(nodeNos), tag)
                .getCall(new JsonConvert<NumberResponse>() {}, RxAdapter.<NumberResponse>create());
    }

}
