package cn.rainsome.www.smartstandard.ui.SmoothListView;

import android.widget.TextView;

import cn.rainsome.www.smartstandard.R;
import cn.yomii.www.frame.adapter.ListLoader;

/**
 * SmoothListView 底布局内容编辑器
 * Created by Yomii on 2016/6/17.
 */
public class DefaultFootEditor {

    private int dataState;

    public int getDataState() {
        return dataState;
    }

    public void setDataState(int dataState) {
        this.dataState = dataState;
    }


    public void footHintChange(TextView hintView) {
        switch (dataState) {
            case ListLoader.STATE_EMPTY:
                hintView.setText(R.string.load_empty);
                break;
            case ListLoader.STATE_ERROR:
                hintView.setText(R.string.load_error);
                break;
            case ListLoader.STATE_DATASOURCEERROR:
                hintView.setText(R.string.list_server_error);
                break;
            case ListLoader.STATE_NOMORE:
                hintView.setText(R.string.load_nomore);
                break;
        }
    }
}
