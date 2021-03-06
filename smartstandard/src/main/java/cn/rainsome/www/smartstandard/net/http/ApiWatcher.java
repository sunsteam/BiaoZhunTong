package cn.rainsome.www.smartstandard.net.http;

import com.apkfuns.logutils.LogUtils;

import cn.rainsome.www.smartstandard.App;
import cn.rainsome.www.smartstandard.utils.ToastUtils;


/**
 * 事件执行完毕自动取消订阅的订阅者
 * Created by Yomii on 2017/2/14.
 */

public abstract class ApiWatcher<T> extends Watcher<T> {


    @Override
    public void onError(Throwable e) {
        dispose();
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

    @Override
    public void onComplete() {
        dispose();
    }
}


