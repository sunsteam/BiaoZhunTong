package cn.rainsome.www.smartstandard.net.http;

import com.apkfuns.logutils.LogUtils;

import cn.rainsome.www.smartstandard.App;
import cn.rainsome.www.smartstandard.utils.ToastUtils;
import io.reactivex.observers.DisposableObserver;


/**
 * 包含默认业务逻辑的订阅者
 * Created by Yomii on 2017/2/14.
 */

public abstract class Watcher<T> extends DisposableObserver<T> {


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


