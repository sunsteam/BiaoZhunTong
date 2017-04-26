package cn.rainsome.www.smartstandard.net.http;

import android.support.annotation.NonNull;

import cn.rainsome.www.smartstandard.adapter.CustomAspectListener;
import cn.rainsome.www.smartstandard.bean.request.RequestBean;
import cn.rainsome.www.smartstandard.bean.response.ListBean;
import cn.yomii.www.frame.adapter.ListLoader;
import cn.yomii.www.frame.bean.ListRequest;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;


/**
 * 从网络获取列表数据的获取器实现
 * Created by Yomii on 2017/1/12.
 */
public class WebLoader<R extends RequestBean, Z extends ListBean> extends ListLoader<R, Z> {

    private CustomAspectListener listener;

    public WebLoader(@NonNull ListRequest<R> request) {
        super(request);
    }

    @Override
    public OnLoadAspectListener getLoadListener() {
        if (listener != null)
            return listener;
        else
            return super.getLoadListener();
    }

    @Override
    public void setLoadListener(OnLoadAspectListener l) {
        if (l instanceof CustomAspectListener)
            listener = (CustomAspectListener) l;
        else
            super.setLoadListener(l);
    }

    @Override
    public void load() {
        setState(STATE_LOADING);

        Observable<Z> observable = ServiceAdapter.adapt(request);
        if (observable == null)
            throw new RuntimeException("the cmd in request is illegal");

        observable.doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                if (getLoadListener() != null)
                    getLoadListener().onLoadBefore(getState(), request);
            }
        })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiWatcher<Z>() {
                    @Override
                    public void onNext(Z value) {

                        if (adapter != null && adapter.onDataFilter(value)) {
                            //更新页数
                            request.setPageIndex(value.getPageIndex());

                            if (getLoadListener() != null && getLoadListener() instanceof CustomAspectListener)
                                ((CustomAspectListener)getLoadListener()).onDataFiltered(value.getPageIndex());
                            adapter.onFilterSuccess(value);
                        }
                    }

                    @Override
                    public void onComplete() {
                        super.onComplete();
                        if (getLoadListener() != null)
                            getLoadListener().onLoadAfter(getState());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if (getLoadListener() != null)
                            getLoadListener().onLoadAfter(getState());
                    }
                });


    }


}
