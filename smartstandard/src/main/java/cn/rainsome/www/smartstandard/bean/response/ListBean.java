package cn.rainsome.www.smartstandard.bean.response;



import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.yomii.www.frame.bean.ListResponse;

/**
 * 请求结果封装的基类
 * Created by Yomii on 2016/3/10.
 */
public class ListBean<T> extends ListResponse<T> {

    /**
     * 状态码
     */
    protected int err;

    /**
     * 错误信息
     */
    protected String error;

    public int getErr() {
        return err;
    }

    public void setErr(int err) {
        this.err = err;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    @SerializedName("pageindex")
    protected int pageIndex;

    @SerializedName("records")
    protected List<T> data;


    @Override
    public int getPageIndex() {
        return pageIndex;
    }

    @Override
    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    @Override
    public List<T> getData() {
        return data;
    }

    @Override
    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ListBean{" +
                "err=" + err +
                ", error='" + error + '\'' +
                ", pageIndex=" + pageIndex +
                ", data=" + data +
                '}';
    }
}
