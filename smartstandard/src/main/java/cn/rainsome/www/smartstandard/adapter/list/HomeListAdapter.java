package cn.rainsome.www.smartstandard.adapter.list;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

import cn.rainsome.www.smartstandard.adapter.list.viewholder.NormalStandardViewHolder;
import cn.rainsome.www.smartstandard.bean.Standard;
import cn.rainsome.www.smartstandard.bean.request.HomeStdRequest;
import cn.rainsome.www.smartstandard.bean.response.Standards;
import cn.yomii.www.frame.adapter.ListLoader;
import cn.yomii.www.frame.adapter.LoadListAdapter;
import cn.yomii.www.frame.base.BaseViewHolder;

/**
 * 首页标准列表适配器
 * Created by Yomii on 2016/6/15.
 */
public class HomeListAdapter extends LoadListAdapter<Standard, Standards> {


    public HomeListAdapter(@NonNull ListLoader<?, Standards> loaderImp) {
        super(loaderImp);
    }

    @Override
    protected BaseViewHolder<Standard> getViewHolder(int i, ViewGroup viewGroup) {
        return new NormalStandardViewHolder(viewGroup);
    }


    @Override
    public boolean onDataFilter(Standards result) {
        HomeStdRequest request = (HomeStdRequest) getLoader().getRequest();
        return result.sortby == request.sortby
                && result.desc == request.desc
                && result.trdno == request.trdno;
    }
}
