package cn.rainsome.www.smartstandard.net.http;

import com.android.databinding.library.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import cn.rainsome.www.smartstandard.Api;
import cn.rainsome.www.smartstandard.ApiMain;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Http请求二次封装
 * Created by Yomii on 2017/1/9.
 */
public class HttpHelper {

    private static Gson gson;
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit = generateRetrofit();

    private static Api api = getRetrofit().create(Api.class);
    private static ApiMain apiMain = getRetrofit().create(ApiMain.class);

    private static Retrofit generateRetrofit() {
        Retrofit.Builder retrofitBuilder = generateCommonRetrofitBuilder();
        retrofitBuilder.baseUrl(Urls.getMainUrl());
        return retrofitBuilder.build();
    }

    private static Retrofit.Builder generateCommonRetrofitBuilder() {
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").create();
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        okHttpBuilder.connectTimeout(20, TimeUnit.SECONDS);
//        okHttpBuilder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request().newBuilder()
//                        .header("Content-Type", "application/json; charset=utf-8")
//                        .build();
//                return chain.proceed(request);
//            }
//        });
        if (BuildConfig.DEBUG)
            okHttpBuilder.addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BASIC));

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        okHttpClient = okHttpBuilder.build();
        retrofitBuilder.client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(GsonConverterFactory.create(gson));
        return retrofitBuilder;
    }

    static Retrofit getRetrofit() {
        return retrofit;
    }

    static OkHttpClient getClient() {
        return okHttpClient;
    }

    public static Gson getGson() {
        return gson;
    }

    public static Api getApi() {
        return api;
    }

    public static ApiMain getApiMain() {
        return apiMain;
    }


}
