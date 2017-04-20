package cn.rainsome.www.smartstandard.adapter.list.viewholder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.rainsome.www.smartstandard.MainApi;
import cn.rainsome.www.smartstandard.R;
import cn.rainsome.www.smartstandard.bean.Label;
import cn.rainsome.www.smartstandard.bean.ReplaceStandard;
import cn.rainsome.www.smartstandard.bean.Standard;
import cn.rainsome.www.smartstandard.bean.response.LabelResponse;
import cn.rainsome.www.smartstandard.db.AuthDao;
import cn.rainsome.www.smartstandard.net.http.JsonCallback;
import cn.rainsome.www.smartstandard.ui.customview.SwipeLayout;
import cn.rainsome.www.smartstandard.utils.DateUtils;
import cn.rainsome.www.smartstandard.utils.PageUtils;
import cn.rainsome.www.smartstandard.utils.ResUtils;
import cn.yomii.www.frame.adapter.list.viewholder.BaseViewHolder;
import okhttp3.Call;
import okhttp3.Response;

public class NormalStandardViewHolder extends BaseViewHolder<Standard> {

    @Bind(R.id.std_id)
    TextView stdId;
    @Bind(R.id.std_caption)
    TextView stdCaption;
    @Bind(R.id.home_list_tag_tv)
    TextView homeListTagTv;
    @Bind(R.id.home_list_add_tag_iv)
    ImageView homeListAddTagIv;
    @Bind(R.id.home_list_time)
    TextView homeListTime;
    @Bind(R.id.std_replace_info)
    TextView stdReplaceInfo;
    @Bind(R.id.swipeLayout)
    SwipeLayout swipeLayout;

    @Bind(R.id.iv_products_clause)
    ImageView ivProductsClause;
    @Bind(R.id.iv_products_image)
    ImageView ivProductsImage;
    @Bind(R.id.iv_products_paid)
    ImageView ivProductsPaid;
    @Bind(R.id.iv_products_downloaded)
    ImageView ivProductsDownload;
    @Bind(R.id.iv_products_verify_state)
    ImageView ivProductsVerify;


    /**
     * 是否需要刷新ui
     */
    protected boolean invalidate;

    private static UpdateLabelReceiver receiver;
    private AuthDao authDao;

    public NormalStandardViewHolder(ViewGroup parent) {
        super(parent);
        authDao = new AuthDao();
    }

    @Override
    protected void setDataToView(Standard data, int position) {

        invalidate = false;

        stdCaption.setText(data.caption);
        stdId.setText(data.stdid);

        //格式图标
        int authType = authDao.queryTypeByNo(data.no);
        ivProductsDownload.setVisibility(authType > 0 ? View.VISIBLE : View.GONE);
        ivProductsPaid.setVisibility(data.canread == 1 ? View.VISIBLE : View.GONE);
        ivProductsVerify.setImageResource(data.status >= 5 ?
                R.drawable.standard_state_verified : R.drawable.standard_state_unverified);
        switch (data.format) {
            case 1://结构化数据
                ivProductsClause.setVisibility(View.VISIBLE);
                ivProductsImage.setVisibility(View.GONE);
                break;
            case 2://图片
                ivProductsClause.setVisibility(View.GONE);
                ivProductsImage.setVisibility(View.VISIBLE);
                break;
            case 3://结构化数据/图片都有
                ivProductsClause.setVisibility(View.VISIBLE);
                ivProductsImage.setVisibility(View.GONE);
                break;
            case 9://数据制作中
                ivProductsClause.setVisibility(View.GONE);
                ivProductsImage.setVisibility(View.GONE);
                break;
        }


        //是否有替换标准
        if (data.rpls != null && data.rpls.size() > 0) {
            stdReplaceInfo.setVisibility(View.VISIBLE);
            ReplaceStandard replaceStandard = data.rpls.get(0);
            int res = replaceStandard.reltype == 2 ? R.string.replace_new_std : R.string.replace_old_std;
            if (replaceStandard.stdid != null && !("").equals(replaceStandard.stdid)) {
                stdReplaceInfo.setText(mContext.getString(res, replaceStandard.stdid));
            } else {
                stdReplaceInfo.setText(mContext.getString(res, replaceStandard.caption));
            }
        } else {
            stdReplaceInfo.setVisibility(View.INVISIBLE);
        }

        //设置标签
        setLabel(data.labels);

        //日期
        if (!TextUtils.isEmpty(data.expireddate)) {
            long expiredTime = DateUtils.getLongByStrDate(data.expireddate);
            if (expiredTime != -1 && expiredTime <= System.currentTimeMillis()) {
                //已废止
                stdCaption.setCompoundDrawablesWithIntrinsicBounds(R.drawable.standard_icon_abolish, 0,
                        0, 0);
                homeListTime.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.standard_icon_time_abolish, 0, 0, 0);
                homeListTime.setTextColor(ResUtils.getColor(mContext, R.color.std_time_abolish));
                homeListTime.setText(data.expireddate);
                return;
            }
        }

        if (!TextUtils.isEmpty(data.performdate)) {
            long performTime = DateUtils.getLongByStrDate(data.performdate);
            if (performTime != -1 && performTime <= System.currentTimeMillis()) {
                //已实施
                stdCaption.setCompoundDrawablesWithIntrinsicBounds(R.drawable.standard_icon_apply, 0, 0, 0);
                homeListTime.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.standard_icon_time_apply, 0, 0, 0);
                homeListTime.setTextColor(ResUtils.getColor(mContext, R.color.std_time_apply));
                homeListTime.setText(data.performdate);
                return;
            }
        }

        if (!TextUtils.isEmpty(data.publishdate)) {
            stdCaption.setCompoundDrawablesWithIntrinsicBounds(R.drawable.standard_icon_publish, 0, 0, 0);
            homeListTime.setCompoundDrawablesWithIntrinsicBounds(
                    R.drawable.standard_icon_time_publish, 0, 0, 0);
            homeListTime.setTextColor(ResUtils.getColor(mContext, R.color.std_time_publish));
            homeListTime.setText(data.publishdate);
        }
    }

    @Override
    protected View initView(Context context, ViewGroup parent) {

        View view = mInflater.inflate(R.layout.standard_view_list, parent, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.home_list_root)
    public void seeStandardDetail(View v) {
        invalidate = true;
        registerLabelReceiver();
        PageUtils.goToBookDetailActivity(mContext, data.no);
    }

    @OnClick(R.id.std_replace_info)
    public void seeReplaceDetail() {
        if (stdReplaceInfo.getText().length() > 0 && data.rpls != null) {
            if (data.rpls.size() > 1) {
                PageUtils.goToReplaceStandardsActivity(mContext, data.no);
            } else {
                PageUtils.goToBookDetailActivity(mContext, data.rpls.get(0).no);
            }
        }
    }

    @OnClick(R.id.home_list_tag_tv)
    public void seeStandardTag() {
        invalidate = true;
        registerLabelReceiver();
        PageUtils.goToTagListActivity(mContext, data.labels.get(0).no);

    }

    @OnClick({R.id.home_list_button_tag, R.id.home_list_add_tag_iv})
    public void addStandardToTagList() {
        invalidate = true;
        registerLabelReceiver();
        PageUtils.goToAddLabelActivity(mContext, data.no);
        swipeLayout.close();
    }

    protected void registerLabelReceiver() {
        if (receiver == null) {
            receiver = new UpdateLabelReceiver();
            LocalBroadcastManager instance = LocalBroadcastManager.getInstance(mContext);
            IntentFilter singleUpdateFilter = new IntentFilter();
            singleUpdateFilter.addAction("action.update_label");
            singleUpdateFilter.addAction("action.update_paid");
            singleUpdateFilter.addAction("action.update_cached");
            singleUpdateFilter.addAction("action.close_receiver");
            instance.registerReceiver(receiver, singleUpdateFilter);
        }
    }


    private void setLabel(List<Label> list) {
        if (list != null && list.size() > 0) {

            Label labelsEntity = list.get(0);

            switch (labelsEntity.breed) {
                case 1: //yellow
                    homeListTagTv.setBackgroundResource(R.drawable.standard_label_yellow);
                    break;
                case 2: //green
                    homeListTagTv.setBackgroundResource(R.drawable.standard_label_green);
                    break;
                case 3: //red
                    homeListTagTv.setBackgroundResource(R.drawable.standard_label_red);
                    break;
                case 4: //blue
                    homeListTagTv.setBackgroundResource(R.drawable.standard_label_blue);
                    break;
                case 5: //pink
                    homeListTagTv.setBackgroundResource(R.drawable.standard_label_pink);
                    break;
                case 6: //purple
                    homeListTagTv.setBackgroundResource(R.drawable.standard_label_purple);
                    break;
            }

            homeListTagTv.setVisibility(View.VISIBLE);
            homeListAddTagIv.setVisibility(View.GONE);
            homeListTagTv.setText(labelsEntity.label);
        } else {
            homeListTagTv.setVisibility(View.GONE);
            homeListAddTagIv.setVisibility(View.VISIBLE);
        }
    }

    private class UpdateLabelReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            LogUtils.i("Holder" + position + "--收到广播");
            String action = intent.getAction();
            if (invalidate && action.equals("action.update_label"))
                updateLabel();
            else if (invalidate && action.equals("action.update_paid"))
                updateRead();
            else if (invalidate && action.equals("action.update_cached"))
                updateCached();
            if (receiver != null) {
                LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);
                receiver = null;
            }
        }
    }

    private void updateLabel() {
        LogUtils.i("Holder" + position + "--请求labels");
        MainApi.standardLabels(data.no, this).execute(new JsonCallback<LabelResponse>() {
            @Override
            public void onSuccess(LabelResponse labelResponse, Call call, Response response) {
                data.labels = labelResponse.records;
                setLabel(labelResponse.records);
                getRootView().postInvalidate();
            }
        });
    }

    private void updateRead() {
        LogUtils.i("Holder" + position + "--请求标准详情");
        MainApi.standardDetail(data.no, this).execute(new JsonCallback<Standard>() {

            @Override
            public void onSuccess(Standard standard, Call call, Response response) {
                data.canread = standard.canread;
                if (standard.canread == 1) {
                    ivProductsPaid.setVisibility(View.VISIBLE);
                } else {
                    ivProductsPaid.setVisibility(View.GONE);
                }
            }
        });

    }

    private void updateCached() {
        int authType = authDao.queryTypeByNo(data.no);
        if (ivProductsDownload != null)
            ivProductsDownload.setVisibility(authType > 0 ? View.VISIBLE : View.GONE);
    }

}