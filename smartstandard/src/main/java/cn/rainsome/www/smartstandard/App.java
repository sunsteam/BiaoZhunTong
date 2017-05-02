package cn.rainsome.www.smartstandard;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.v4.content.LocalBroadcastManager;
import android.util.DisplayMetrics;

import com.apkfuns.logutils.LogUtils;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import cn.rainsome.www.smartstandard.bean.event.AppOkEvent;
import cn.rainsome.www.smartstandard.bean.request.InfoRequest;
import cn.rainsome.www.smartstandard.bean.request.LoginRequest;
import cn.rainsome.www.smartstandard.bean.request.RequestBean;
import cn.rainsome.www.smartstandard.bean.response.ConfigureResponse;
import cn.rainsome.www.smartstandard.bean.response.InfoResponse;
import cn.rainsome.www.smartstandard.bean.response.LoginResponse;
import cn.rainsome.www.smartstandard.net.http.ApiWatcher;
import cn.rainsome.www.smartstandard.net.http.HttpHelper;
import cn.rainsome.www.smartstandard.net.http.Token;
import cn.rainsome.www.smartstandard.net.http.Urls;
import cn.rainsome.www.smartstandard.service.LocalPostilUploadService;
import cn.rainsome.www.smartstandard.utils.SpfUtils;
import cn.yomii.www.frame.ActivityLifeCallback;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

import static cn.rainsome.www.smartstandard.ApiNeeds.Bugly_ID;

/**
 * Application
 * Created by Yomii on 2017/1/9.
 */
public class App extends Application {

    private static App sContext;
    private static int sMainTid;
    private static Thread sMainThread;
    private static Handler sHandler;
    private static DisplayMetrics sMetrics;

    public static Context getContext() {
        return sContext;
    }

    /**
     * @return 主线程id
     */
    public static int getMainTid() {
        return sMainTid;
    }

    public static Thread getMainThread() {
        return sMainThread;
    }

    public static Handler getHandler() {
        return sHandler;
    }

    public static DisplayMetrics getMetrics() {
        return sMetrics;
    }

    /**
     * 在主线程运行任务
     */
    public static void runInMainThread(Runnable task) {
        if (getMainTid() == Process.myTid()) {
            task.run();
        } else {
            getHandler().post(task);
        }
    }


    private long startTime;


    @Override
    public void onCreate() {
        super.onCreate();

        String currentProcessName = getCurrentProcessName();
        LogUtils.i("onCreate currentProcessName=" + currentProcessName);
        startTime = System.currentTimeMillis();
        LogUtils.i("startTime __ " + startTime);

        if (getPackageName().equals(currentProcessName)) {
            registerActivityLifecycleCallbacks(new ActivityLifeCallback() {
                @Override
                public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                    pageList.add(activity);
                }

                @Override
                public void onActivityDestroyed(Activity activity) {
                    pageList.remove(activity);
                }
            });

            sMainTid = Process.myTid();
            sMainThread = Thread.currentThread();
            sHandler = new Handler();
            sContext = (App) getApplicationContext();
            sMetrics = sContext.getResources().getDisplayMetrics();

            LogUtils.i("逻辑密度: " + sMetrics.density);
            LogUtils.i("屏幕密度: " + sMetrics.densityDpi);
            LogUtils.i("屏幕宽度: " + sMetrics.widthPixels);
            LogUtils.i("屏幕高度: " + sMetrics.heightPixels);
            LogUtils.i("缩放密度: " + sMetrics.scaledDensity);

            LogUtils.getLogConfig().configAllowLog(BuildConfig.LOG_DEBUG);

            //设置统计超时时间为1分钟
            //MobclickAgent.setSessionContinueMillis(60000);
            //MobclickAgent.setDebugMode(BuildConfig.LOG_DEBUG);

            //本地崩溃处理初始化
            CrashManager.getInstance().init();
            //Bugly初始化
            //单独使用CrashReport功能时
            //CrashReport.initCrashReport(this, Bugly_ID, BuildConfig.LOG_DEBUG);
            //CrashReport.setIsDevelopmentDevice(this, BuildConfig.DEBUG);
            Beta.upgradeCheckPeriod = 60 * 1000;
            Bugly.init(this, Bugly_ID, BuildConfig.LOG_DEBUG);
            Bugly.setIsDevelopmentDevice(this, BuildConfig.LOG_DEBUG);


            //            OkGo.init(this);
            //            OkGo.getInstance()
            //                    .setConnectTimeout(HttpHelper.DEFAULT_MILLISECONDS)               //全局的连接超时时间
            //                    .setReadTimeOut(HttpHelper.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
            //                    .setWriteTimeOut(HttpHelper.DEFAULT_MILLISECONDS)               //全局的写入超时时间
            //                    //.debug("OkGo", Level.WARNING, true)                                //是否调试
            //                    .setCertificates(new Buffer().writeUtf8(CRT).inputStream());     //证书
            //                    //.addInterceptor(new RefreshTokenInterceptor());                 //自动刷新Token拦截器

            requestServerInfo();

        }
    }

    /**
     * 获取服务器信息
     */
    private void requestServerInfo() {
        HttpHelper.getApiMain().info(new InfoRequest())
                .flatMap(new Function<InfoResponse, Observable<LoginResponse>>() {
                    @Override
                    public Observable<LoginResponse> apply(InfoResponse infoResponse) throws Exception {
                        //Urls.setMainUrl(infoResponse.host); 与Retrofit不兼容
                        requestImageUrl();
                        return HttpHelper.getApiMain().autoLogin(LoginRequest.getRetryLoginRequest());
                    }
                })
                .doOnNext(new Consumer<LoginResponse>() {

                    @Override
                    public void accept(LoginResponse response) throws Exception {
                        //填充用户信息
                        Info.fillUserInfoAfterLogin(response);
                        //cache到SPF
                        SharedPreferences.Editor editor =
                                SpfUtils.getUser().edit().putInt(SpfUtils.USER_NO, response.no);
                        if (!Info.isTemperToken()) {
                            editor.putString(SpfUtils.USER_COMPANIES, response.companys.get(0).csmcaption)
                                    .putString(SpfUtils.USER, response.fullname);
                        }
                        editor.apply();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiWatcher<LoginResponse>() {

                    @Override
                    public void onNext(LoginResponse response) {
                        //发送广播 用于详情页面收到分享标准回调
                        LocalBroadcastManager.getInstance(getContext())
                                .sendBroadcast(new Intent(Actions.Action_AppInit));
                        //todo 详情页面接收广播

                        //开始离线批注上传服务
                        startService(new Intent(getContext(), LocalPostilUploadService.class));
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        loadCacheUserInfo();
                        EventBus.getDefault().post(new AppOkEvent(System.currentTimeMillis() - startTime));
                    }

                    @Override
                    public void onComplete() {
                        EventBus.getDefault().post(new AppOkEvent(System.currentTimeMillis() - startTime));
                    }
                });


    }

    private void requestImageUrl() {
        HttpHelper.getApiMain().configure(new RequestBean("configure", Token.getToken()))
                .subscribe(new Consumer<ConfigureResponse>() {

                    @Override
                    public void accept(ConfigureResponse configureResponse) throws Exception {
                        Urls.setImageUrl(configureResponse.sdcpic);
                        Urls.setMandatoryUrl(configureResponse.mdypic);
                    }
                });
    }

    private void loadCacheUserInfo() {
        SharedPreferences user = SpfUtils.getUser();
        Info.setPsnNo(user.getInt(SpfUtils.USER_NO, 0));
        Info.setCompany(user.getString(SpfUtils.USER_COMPANIES, "[]"));
    }


    private String getCurrentProcessName() {
        String currentProcessName = "";
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                currentProcessName = processInfo.processName;
                break;
            }
        }
        return currentProcessName;
    }

    /**
     * 打开的Activity引用链
     */
    private static ArrayList<Activity> pageList = new ArrayList<>();

    /**
     * 关闭Activity列表中的所有Activity
     */
    public static void finishActivity() {
        for (Activity activity : pageList) {
            if (null != activity) {
                activity.finish();
            }
        }
        pageList.clear();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
