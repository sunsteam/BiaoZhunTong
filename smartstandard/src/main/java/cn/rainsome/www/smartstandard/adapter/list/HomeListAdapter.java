package cn.rainsome.www.smartstandard.adapter.list;

import android.view.ViewGroup;

import cn.rainsome.www.smartstandard.net.http.Token;
import cn.yomii.www.frame.adapter.Loader;
import cn.yomii.www.frame.adapter.list.LoadListAdapter;
import cn.yomii.www.frame.adapter.list.viewholder.BaseViewHolder;

import cn.rainsome.www.smartstandard.adapter.list.viewholder.NormalStandardViewHolder;
import cn.rainsome.www.smartstandard.bean.Standard;
import cn.rainsome.www.smartstandard.bean.request.HomeStdRequest;
import cn.rainsome.www.smartstandard.bean.response.StandardsResponse;

/**
 * 首页标准列表适配器
 * Created by Yomii on 2016/6/15.
 */
public class HomeListAdapter extends LoadListAdapter<Standard, HomeStdRequest,StandardsResponse> {


    public HomeListAdapter(Loader<HomeStdRequest, StandardsResponse> loader) {
        super(loader);
    }

    public void setIndustryNO(int industryNO) {
        getLoader().getRequest().trdno = industryNO;
        getLoader().refreshData(Token.getToken());
        clearDataList();
    }

    public int getIndustryNo() {
        return getLoader().getRequest().trdno;
    }

    public int getSortBy() {
        return getLoader().getRequest().sortby;
    }

    public void setSortBy(int sortBy) {
        getLoader().getRequest().sortby = sortBy;
        getLoader().refreshData(Token.getToken());
        clearDataList();
    }

    public boolean isSortAsAsc() {
        return getLoader().getRequest().desc == 0;
    }

    public void setSortAsAsc(boolean sortAsAsc) {
        getLoader().getRequest().desc = sortAsAsc ? 0 : 1;
        getLoader().refreshData(Token.getToken());
        clearDataList();
    }


    @Override
    public boolean dataFilter(StandardsResponse result) {
        HomeStdRequest request = getLoader().getRequest();
        return result.sortby == request.sortby
                && result.desc == request.desc
                && result.trdno == request.trdno;
    }

    @Override
    protected BaseViewHolder<Standard> getViewHolder(int i, ViewGroup viewGroup) {
        return new NormalStandardViewHolder(viewGroup);
    }




}
