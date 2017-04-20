package cn.rainsome.www.smartstandard.adapter.list;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cn.rainsome.www.smartstandard.Info;
import cn.rainsome.www.smartstandard.R;
import cn.rainsome.www.smartstandard.bean.HomeFunctionBean;
import cn.rainsome.www.smartstandard.function.TemperActivity;
import cn.rainsome.www.smartstandard.utils.NetworkUtils;
import cn.rainsome.www.smartstandard.utils.PageUtils;
import cn.rainsome.www.smartstandard.utils.ResUtils;
import cn.rainsome.www.smartstandard.utils.ToastUtils;
import cn.yomii.www.frame.adapter.list.BaseListAdapter;
import cn.yomii.www.frame.adapter.list.viewholder.BaseViewHolder;

/**
 * 首页GridView适配器, 对应各功能模块
 * Created by Yomii on 2016/6/14.
 */
public class HomeFunctionAdapter extends BaseListAdapter<HomeFunctionBean> {

    static ArrayList<HomeFunctionBean> list = new ArrayList<>();

    static {
        list.add(new HomeFunctionBean(R.string.home_catalog_text_btn,
                R.string.home_catalog_text_description, R.drawable.home_btn_catalog));

        list.add(new HomeFunctionBean(R.string.home_library_text_btn,
                R.string.home_library_text_description, R.drawable.home_btn_library));

        list.add(new HomeFunctionBean(R.string.home_industry_text_btn,
                R.string.home_industry_text_description, R.drawable.home_btn_news));

        list.add(new HomeFunctionBean(R.string.home_mandatory_text_btn,
                R.string.home_mandatory_text_description, R.drawable.home_btn_mandatory));
    }

    public HomeFunctionAdapter() {
        super(list);
    }

    @Override
    protected BaseViewHolder<HomeFunctionBean> getViewHolder(int position, ViewGroup parent) {
        return new FunctionHolder(parent);
    }

    class FunctionHolder extends BaseViewHolder<HomeFunctionBean> implements View.OnClickListener {

        TextView caption;
        TextView description;
        ImageView icon;

        public FunctionHolder(ViewGroup parent) {
            super(parent);
        }

        @Override
        protected void setDataToView(HomeFunctionBean data, int position) {
            caption.setText(data.titleResId);
            description.setText(data.descriptionResId);
            icon.setImageResource(data.IconResId);

            switch (data.descriptionResId) {
                case R.string.home_catalog_text_description:
                    description.setBackgroundResource(R.drawable.home_head_stroke_catalog);
                    description.setTextColor(ResUtils.getColor(mContext, R.color.stroke_catalog));
                    break;
                case R.string.home_library_text_description:
                    description.setBackgroundResource(R.drawable.home_head_stroke_library);
                    description.setTextColor(ResUtils.getColor(mContext, R.color.stroke_library));
                    break;
                case R.string.home_industry_text_description:
                    description.setBackgroundResource(R.drawable.home_head_stroke_industry);
                    description.setTextColor(ResUtils.getColor(mContext, R.color.stroke_industry));
                    break;
                case R.string.home_mandatory_text_description:
                    description.setBackgroundResource(R.drawable.home_head_stroke_mandatory);
                    description.setTextColor(ResUtils.getColor(mContext, R.color.stroke_mandatory));
                    break;
            }
        }

        @Override
        protected View initView(Context context, ViewGroup parent) {

            View inflate = mInflater.inflate(R.layout.home_view_grid, parent, false);
            caption = (TextView) inflate.findViewById(R.id.home_middle_caption);
            description = (TextView) inflate.findViewById(R.id.home_middle_description);
            icon = (ImageView) inflate.findViewById(R.id.home_middle_icon);

            inflate.setOnClickListener(this);

            return inflate;
        }

        @Override
        public void onClick(View v) {
            //// TODO: 2017/2/13 优化
            Intent intent = new Intent();
            int kind;
            switch (position) {
                case 0:
                    intent.setClass(mContext, TemperActivity.class/*New2CatalogActivity.class*/);
                    break;
                case 1:
                    if (Info.isTemperToken() && NetworkUtils.isConnected()) {
                        ToastUtils.imitShowToast(R.string.error_9988);
                        PageUtils.goToLoginActivity(mContext);
                        return;
                    }
                    intent.setClass(mContext, TemperActivity.class/*NewFavoriteListActivity.class*/);
                    break;
                case 2:
                    intent.setClass(mContext, TemperActivity.class/*IndustryInfoActivity.class*/);
                    break;
                case 3:
                    if (Info.isTemperToken()) {
                        ToastUtils.imitShowToast(R.string.error_9988);
                        PageUtils.goToLoginActivity(mContext);
                        return;
                    } else {
                        intent.setClass(mContext, TemperActivity.class/*MandatoryTreeActivity.class*/);
                    }
                    break;
            }
            mContext.startActivity(intent);
        }
    }
}
