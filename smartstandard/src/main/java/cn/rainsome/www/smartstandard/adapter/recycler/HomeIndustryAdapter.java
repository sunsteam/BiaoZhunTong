package cn.rainsome.www.smartstandard.adapter.recycler;

import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import java.util.Collections;
import java.util.List;

import cn.rainsome.www.smartstandard.App;
import cn.rainsome.www.smartstandard.R;
import cn.rainsome.www.smartstandard.bean.Industry;
import cn.rainsome.www.smartstandard.bean.response.NumberResponse;
import cn.rainsome.www.smartstandard.net.http.Patron;
import cn.rainsome.www.smartstandard.ui.recycler.ItemTouchHelperAdapter;
import cn.yomii.www.frame.adapter.recycler.BaseRecyclerAdapter;
import cn.yomii.www.frame.adapter.recycler.recyclerholder.BaseRecyclerHolder;
import rx.Observable;

public class HomeIndustryAdapter extends BaseRecyclerAdapter<Industry>
        implements ItemTouchHelperAdapter {


    private SparseBooleanArray states = new SparseBooleanArray();
    private String originCopy;

    private OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }


    /**
     * 更新选中行业 行业号>0表示需要选中, <=0表示全部不选中
     * @param industryNo 行业节点号
     */
    public void updateIndustriesSelected(int industryNo){
        int index = states.indexOfValue(true);
        if (index >= 0)
            states.put(states.keyAt(index), false);
        if (industryNo > 0)
            states.put(industryNo, true);
        notifyDataSetChanged();
    }

    @Override
    public void setDataList(List<Industry> dataList) {
        super.setDataList(dataList);
        this.originCopy = serialize();
        for (Industry industry : this.dataList) {
            states.put(industry.no, false);
        }
    }

    private String serialize() {
        StringBuilder sb = new StringBuilder();
        int size = dataList.size();
        for (int i = 0; i < size; i++) {
            if (i != 0)
                sb.append(",");
            sb.append(dataList.get(i).no);
        }
        return sb.toString();
    }

    @Override
    public void onItemDismiss(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(dataList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public String getCopy(){
        String temper = serialize();
        return TextUtils.equals(temper, originCopy) ? "" : temper;
    }

    public void sync(Observable<NumberResponse> observable) {
        final String temper = serialize();
        if (!TextUtils.equals(temper, originCopy)) {
            observable.subscribe(new Patron<NumberResponse>() {
                @Override
                public void onNext(NumberResponse numberResponse) {
                    originCopy = temper;
                    LogUtils.i("位置保存成功, 返回no = " + numberResponse.no);
                }
            });
        }
    }

    @Override
    public BaseRecyclerHolder<Industry> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, R.layout.home_popularize_industry_view_recycler);
    }


    private class ViewHolder extends BaseRecyclerHolder<Industry> {

        private TextView tv;

        public ViewHolder(ViewGroup parent, @LayoutRes int layoutRes) {
            super(parent, layoutRes);
        }

        @Override
        protected void setDataToView(Industry industry, int i) {
            Glide.with(tv.getContext()).load(industry.url).asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            resource.setDensity(320);
                            GlideBitmapDrawable drawable = new GlideBitmapDrawable(App.getContext().getResources(), resource);
                            tv.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
                        }
                    });
            tv.setText(industry.caption);
            tv.setTag(industry.no);
            tv.setSelected(states.get(industry.no));
        }

        @Override
        protected void initView(View view) {
            tv = (TextView) view;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemClickListener != null) {
                        itemClickListener.onItemClick(v, (Integer) v.getTag());
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {

        void onItemClick(View v, int tag);
    }
}