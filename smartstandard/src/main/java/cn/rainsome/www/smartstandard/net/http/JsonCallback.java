//package cn.rainsome.www.smartstandard.net.http;
//
//import android.support.annotation.NonNull;
//
//import com.apkfuns.logutils.LogUtils;
//
//import java.io.IOException;
//import java.lang.reflect.ParameterizedType;
//import java.lang.reflect.Type;
//
//import cn.rainsome.www.smartstandard.bean.response.ResponseBean;
//import cn.rainsome.www.smartstandard.utils.ToastUtils;
//import okhttp3.Call;
//import okhttp3.Response;
//
///**
// * Json格式数据回调基类
// * Created by Yomii on 2017/1/9.
// */
//public abstract class JsonCallback<T extends ResponseBean> extends AbsCallback<T> {
//
//    //    private BaseRequest baseRequest;
//    //    private int tryCount = 3;
//    private Class<?> clazz;
//
//    public JsonCallback(Class<?> clazz) {
//        this.clazz = clazz;
//    }
//
//    public JsonCallback() {
//    }
//
//
//    @Override
//    public void onBefore(BaseRequest request) {
//        //        baseRequest = request;
//        super.onBefore(request);
//    }
//
//    @Override
//    public T convertSuccess(Response response) throws Exception {
//
//        Type type;
//        if (clazz != null) {
//            type = clazz;
//        } else {
//            ParameterizedType genType = (ParameterizedType) getClass().getGenericSuperclass();
//            type = genType.getActualTypeArguments()[0];
//        }
//
//        T o = parseJson(getBodyString(response), type);
//
//        if (o.err == 0)
//            return o;
//
//        throw new BusinessException(o.err, o.error);
//    }
//
//    @NonNull
//    private String getBodyString(Response response) throws IOException {
//        try {
//            return response.body().string();
//        } finally {
//            response.close();
//        }
//    }
//
//    private T parseJson(String json, Type t) {
//        try {
//            return JSON.parseObject(json, t);
//        } catch (RuntimeException e) {
//            LogUtils.e("reason" + e.toString());
//            LogUtils.e("ErrorJsonString" + json);
//            LogUtils.e("ErrorClass" + t.toString());
//            throw e;
//        }
//    }
//
//    @Override
//    public void onError(Call call, Response response, Exception e) {
//        super.onError(call, response, e);
//        if (e instanceof BusinessException) {
//            BusinessException re = (BusinessException) e;
//            onExceptionResponse(re, call, response);
//            //token过期现在在拦截器中处理
//            //            if (re.getErr() == 9984) {
//            //                onTokenExpired(call, response);
//            //            } else {
//            //                onExceptionResponse(re, call, response);
//            //            }
//        } else
//            LogUtils.e(e);
//
//    }
//
//    protected void onExceptionResponse(BusinessException e, Call call, Response response) {
//        ToastUtils.imitShowToast(e.getError());
//    }
//
//    /*
//
//        原来的处理方式保留
//
//    private void onTokenExpired(Call call, Response response) {
//        if (tryCount >= 1) {
//            LogUtils.i("重试登陆--" + tryCount);
//            tryCount--;
//            HttpHelper.post(LoginRequest.getRetryLoginRequest(), "login")
//                    .execute(new RetryLoginCallBack());
//        } else {
//            ToastUtils.imitShowToast(R.string.error_9984_token_expired);
//        }
//    }
//
//    private class RetryLoginCallBack extends JsonCallback<LoginResponse> {
//
//        @Override
//        public void onSuccess(LoginResponse loginResponse, Call call, Response response) {
//
//            Info.fillUserInfoAfterLogin(loginResponse);
//
//            if (baseRequest instanceof PostRequest) {
//                try {
//                    Field json = baseRequest.getClass().getDeclaredField("content");
//                    json.setAccessible(true);
//                    String jsonStr = (String) json.get(baseRequest);
//                    LogUtils.i("原始body--" + jsonStr);
//
//                    String tokenTag = "token\":\"";
//                    if (jsonStr.contains(tokenTag)) {
//                        int tokenHead = jsonStr.indexOf(tokenTag);
//                        int tokenStart = tokenHead + tokenTag.length();
//                        String left = jsonStr.substring(0, tokenStart);
//                        int tokenEnd = jsonStr.indexOf("\"", tokenStart);
//                        String right = jsonStr.substring(tokenEnd);
//                        jsonStr = left + Info.getToken() + right;
//                    }
//                    LogUtils.i("新body--" + jsonStr);
//
//                    HttpHelper.post(jsonStr, call.request().tag()).execute(JsonCallback.this);
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                throw new RuntimeException("not post request");
//            }
//        }
//    }*/
//}
