package cn.rainsome.www.smartstandard.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 首页 用户关注行业列表中子控件
 * Created by Yomii on 2016/8/10.
 */
public class HomeIndustryView extends LinearLayout {

    private TextView textView;

    private ImageView imageView;

    public HomeIndustryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HomeIndustryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        textView = (TextView) getChildAt(0);
        imageView = (ImageView) getChildAt(1);
    }

    public void setText(String s){
        textView.setText(s);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        imageView.setVisibility(selected ? View.VISIBLE : View.INVISIBLE);
    }
}
