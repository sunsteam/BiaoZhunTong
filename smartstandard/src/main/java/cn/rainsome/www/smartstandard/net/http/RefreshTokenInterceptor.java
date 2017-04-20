package cn.rainsome.www.smartstandard.net.http;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.apkfuns.logutils.LogUtils;
import com.lzy.okgo.model.HttpParams;

import java.io.IOException;

import cn.rainsome.www.smartstandard.bean.request.LoginRequest;
import cn.rainsome.www.smartstandard.bean.response.LoginResponse;
import cn.yomii.www.frame.bean.response.ResponseBean;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

/**
 * 自动刷新Token拦截器
 * Created by Yomii on 2017/2/14.
 */
public class RefreshTokenInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        LogUtils.i("--进入自动刷新token拦截器--");
        Request request = chain.request();
        Response response = chain.proceed(request);
        return handleRefreshToken(chain, response);
    }

    /**
     * 处理刷新token逻辑
     */
    private Response handleRefreshToken(Chain chain, Response originalResponse) throws IOException {
        // 判断是否过期
        if (isTokenExpired(originalResponse)) {
            LogUtils.i("-- 处理自动刷新token --");
            // 通过同步方法获取新的token
            String tokenInfo = getTokenInfo();
            if (TextUtils.isEmpty(tokenInfo)) {
                LogUtils.i("--刷新token请求失败了--");
                return originalResponse;
            }
            LogUtils.i("--刷新后的token-->" + tokenInfo);
            // 组成新的请求
            Buffer buffer = new Buffer();
            chain.request().body().writeTo(buffer);
            String oldStr = buffer.readUtf8();
            LogUtils.i("原始body--" + oldStr);

            String wrappedToken = "token\":\"" + tokenInfo + "\"";
            String newStr = oldStr.replaceAll("token\":\"\\w+\"", wrappedToken);
            LogUtils.i("新body--" + newStr);

            Request newRequest = chain.request().newBuilder()
                    .post(RequestBody.create(HttpParams.MEDIA_TYPE_JSON, newStr))
                    .build();
            // 将原来的响应关闭
            originalResponse.body().close();
            return chain.proceed(newRequest);
        }
        return originalResponse;
    }

    /**
     * 判断应答码是否是token过期
     */
    private boolean isTokenExpired(Response response) throws IOException {
        ResponseBean bean = JSON.parseObject(response.body().string(), ResponseBean.class);
        return bean.err == 9984;
    }

    /**
     * 使用同步方式获取TokenInfo
     *
     * @return TokenInfo
     *
     * @throws IOException
     */
    private String getTokenInfo() throws IOException {

        Response r = HttpHelper.post(LoginRequest.getRetryLoginRequest(), "Interceptor").execute();
        LoginResponse loginResponse = JSON.parseObject(r.body().string(), LoginResponse.class);
        LogUtils.i("自动重新请求token结果", loginResponse);
        return loginResponse.token;
    }


}
