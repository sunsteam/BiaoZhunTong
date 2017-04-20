package cn.rainsome.www.smartstandard.net.http;

import com.apkfuns.logutils.LogUtils;

import cn.rainsome.www.smartstandard.App;
import cn.rainsome.www.smartstandard.utils.ToastUtils;
import rx.Subscriber;



/**
 * 包含默认业务逻辑的订阅者
 * Created by Yomii on 2017/2/14.
 */

public abstract class Patron<T> extends Subscriber<T> {

    @Override
    public void onCompleted() {}

    @Override
    public void onError(Throwable e) {
        if (e instanceof BusinessException) {
            onExceptionResponse((BusinessException) e);
        } else
            LogUtils.e(e);
    }

    protected void onExceptionResponse(final BusinessException e) {
        App.runInMainThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.imitShowToast(e.getError());
            }
        });
    }

}


