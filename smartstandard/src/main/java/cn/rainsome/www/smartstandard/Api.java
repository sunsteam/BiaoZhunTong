package cn.rainsome.www.smartstandard;

import cn.rainsome.www.smartstandard.bean.Industry;
import cn.rainsome.www.smartstandard.bean.request.StableIndustryRequest;
import cn.rainsome.www.smartstandard.bean.request.SyncIndustryPositionRequest;
import cn.rainsome.www.smartstandard.bean.response.ListBean;
import cn.rainsome.www.smartstandard.bean.response.NumberResponse;
import cn.rainsome.www.smartstandard.bean.response.Standards;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;


/**
 * 版本独有或可能变化的API
 * Created by Yomii on 2017/2/13.
 */

public interface Api {

    /**
     * 首页推荐的行业
     */
    @POST("rsi/")
    Observable<ListBean<Industry>> popularizeIndustry(@Body StableIndustryRequest request);


    /**
     * 保存推荐行业列表的位置变化
     */
    @POST("rsi/")
    Observable<NumberResponse> updateIndustriesPosition(@Body SyncIndustryPositionRequest request);

    /**
     * 首页标准列表
     */
    @POST("rsi/")
    Observable<Standards> industryStandards(@Body RequestBody request);

}
