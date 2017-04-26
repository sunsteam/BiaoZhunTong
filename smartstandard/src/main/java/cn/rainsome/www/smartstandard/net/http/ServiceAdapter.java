package cn.rainsome.www.smartstandard.net.http;

import android.text.TextUtils;

import com.apkfuns.logutils.LogUtils;

import cn.yomii.www.frame.bean.ListRequest;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 将Request转化为对应的ServiceApi
 * Created by Yomii on 2017/4/25.
 */

public class ServiceAdapter {

    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");

    public static <R, Z> Observable<Z> adapt(ListRequest<R> request) {
        String requestClassName = request.getRequest().getClass().getSimpleName();
        LogUtils.i("name: " + requestClassName);
        String s = HttpHelper.getGson().toJson(request.getRequest());
        String v;
        if (TextUtils.isEmpty(s))
            v = s;
        else
            v = s.substring(0,1).concat(request.toString()).concat(s.substring(1));
        RequestBody requestBody = RequestBody.create(MEDIA_TYPE, v);
        switch (requestClassName) {
            case "HomeStdRequest":
                return (Observable<Z>) HttpHelper.getApi().industryStandards(requestBody);
        }

        return null;
    }


}
