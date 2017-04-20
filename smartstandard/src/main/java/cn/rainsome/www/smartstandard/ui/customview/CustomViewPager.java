package cn.rainsome.www.smartstandard.ui.customview;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import cn.rainsome.www.smartstandard.App;
import cn.rainsome.www.smartstandard.bean.ViewPagerBean;
import cn.rainsome.www.smartstandard.utils.ConvertUtils;

/**
 * 自滚动ViewPager
 * Created by Yomii on 2016/3/9.
 */
public class CustomViewPager extends RelativeLayout implements ViewPager.OnPageChangeListener,
        View.OnTouchListener {

    public static int DOTS_ID = 0x01;

    private List<ViewPagerBean> data;
    private RelativeLayout.LayoutParams viewPagerLp;
    private RelativeLayout.LayoutParams textLp;

    private ViewPager viewPager;
    private LinearLayout dotsLayout;
    private TextView title;

    private boolean isEmpty;
    private boolean pagerIdle;
    private Timer timer;

    public boolean isEmpty() {
        return isEmpty;
    }


    public CustomViewPager(Context context, int dipHeight) {
        this(context, null, dipHeight);
    }

    public CustomViewPager(Context context, List<ViewPagerBean> data, int dipHeight) {
        super(context);
        LogUtils.i("CustomViewPager初始化");

        //--------------------初始化布局参数----------------
        //this.setPadding(ConvertUtils.dp2px(5), ConvertUtils.dp2px(5), ConvertUtils.dp2px(5), ConvertUtils.dp2px(5));
        viewPagerLp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        if (dipHeight > 0) {
            viewPagerLp.height = ConvertUtils.dp2px(dipHeight);
        }

        LayoutParams dotsLp = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        dotsLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        dotsLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //dotsLp.setMargins(0, 0, 0, ConvertUtils.dp2px(10));

        textLp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        textLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        textLp.addRule(RelativeLayout.LEFT_OF, DOTS_ID);
        textLp.height = ConvertUtils.dp2px(30);
        textLp.setMargins(ConvertUtils.dp2px(5), 0, ConvertUtils.dp2px(5), ConvertUtils.dp2px(5));
        //--------------------初始化DotsLayout----------------
        dotsLayout = new LinearLayout(context);
        dotsLayout.setPadding(0, 0, 0, ConvertUtils.dp2px(4));
        //noinspection ResourceType
        dotsLayout.setId(DOTS_ID);
        this.addView(dotsLayout, dotsLp);
        setData(data);
        //--------------------初始化TextTitle----------------
        title = new TextView(context);
        title.setSingleLine();
        title.setBackgroundColor(Color.argb(88, 0, 0, 0));
        title.setTextColor(Color.WHITE);
        title.setText("测试效果");
        title.setGravity(Gravity.CENTER_VERTICAL);
        //隐藏title
        title.setVisibility(View.GONE);
        this.addView(title, textLp);
        //--------------------初始化ViewPager----------------
        viewPager = new ViewPager(context);
        viewPager.setOnPageChangeListener(this);
        ViewPagerAdapter adapter = new ViewPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2);
        viewPager.setOnTouchListener(this);
        this.addView(viewPager, viewPagerLp);
        //--------------------准备阶段----------------
        dotsLayout.bringToFront();
        title.bringToFront();
    }

    /**
     * 设置数据列表,并根据数据动态加点
     *
     * @param data data
     */
    public void setData(List<ViewPagerBean> data) {
        if (data == null || data.size() == 0) {
            data = new ArrayList<>();
            data.add(new ViewPagerBean("", "", false, null));
            isEmpty = true;
        }
        this.data = data;

        //根据List的数据数量动态加点
        dotsLayout.removeAllViews();
        int size = this.data.size();
        for (int i = 0; i < size; i++) {
            LinearLayout.LayoutParams dotLp = new LinearLayout.LayoutParams(ConvertUtils.dp2px(8),
                    ConvertUtils.dp2px(8));
            View dot = new View(getContext());
            //状态选择器
            StateListDrawable seletor = new StateListDrawable();
            GradientDrawable selectedDrawable = new GradientDrawable();
            selectedDrawable.setShape(GradientDrawable.OVAL);
            selectedDrawable.setCornerRadius(16f);
            selectedDrawable.setColor(Color.BLUE);
            seletor.addState(SELECTED_STATE_SET, selectedDrawable);
            GradientDrawable normalDrawable = new GradientDrawable();
            normalDrawable.setShape(GradientDrawable.OVAL);
            normalDrawable.setCornerRadius(16f);
            normalDrawable.setColor(0xAAAAAAAA);
            seletor.addState(EMPTY_STATE_SET, normalDrawable);
            dot.setBackgroundDrawable(seletor);
            if (i == 0) {
                dot.setSelected(true);
            } else {
                dotLp.setMargins(ConvertUtils.dp2px(6), 0, 0, 0);
            }
            dotsLayout.addView(dot, dotLp);
        }
        if (viewPager != null) {
            viewPager.getAdapter().notifyDataSetChanged();
            viewPager.setCurrentItem(Integer.MAX_VALUE / 2);
            startPageScroll(5000);
        }

    }

    public int getViewPagerHeight() {
        return viewPagerLp.height;
    }

    public void setViewPagerHeight(int viewPagerHeight) {
        this.viewPagerLp.height = viewPagerHeight;
        viewPager.setLayoutParams(viewPagerLp);
    }

    public int getTitleViewHeight() {
        return textLp.height;
    }

    public void setTitleViewHeight(int viewPagerHeight) {
        this.textLp.height = viewPagerHeight;
        title.setLayoutParams(textLp);
    }

    public View getRootView() {
        return this;
    }

    public ViewPager getViewPager() {
        return viewPager;
    }

    /**
     * @return 当前页面的数据
     */
    public ViewPagerBean getCurrentPageBean() {
        return data.get(getRealPostion(viewPager.getCurrentItem()));
    }

    /**
     * 设置当前页面的点击事件
     *
     * @param listener View.OnClickListener
     */
    public void setOnPagerClickListener(View.OnClickListener listener) {
        viewPager.setOnClickListener(listener);
    }


    private class ViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            int size = data.size();
            return size == 1 ? 1 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(getContext());
            if (data != null) {
                int realPosition = getRealPostion(position);
                container.addView(imageView, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
                if (data.get(realPosition).getPicUrl() != null) {
                    Glide.with(getContext()).load(data.get(realPosition).getPicUrl()).centerCrop()
                            .into(imageView);
                } else if (data.get(realPosition).getPicDrawable() != null) {
                    imageView.setImageDrawable(data.get(realPosition).getPicDrawable());
                } else if (data.get(realPosition).getPicBitmap() != null) {
                    imageView.setImageBitmap(data.get(realPosition).getPicBitmap());
                } else if (data.get(realPosition).getPicResID() > 0) {
                    imageView.setImageResource(data.get(realPosition).getPicResID());
                }
            }
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;

        }
    }

    /**
     * 根据当前position计算实际数据position
     */
    private int getRealPostion(int position) {
        return position % data.size();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        int realPosition = getRealPostion(position);
        int childCount = dotsLayout.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (i == realPosition) {
                dotsLayout.getChildAt(i).setSelected(true);
            } else {
                dotsLayout.getChildAt(i).setSelected(false);
            }
        }
        if (data.get(realPosition).isHasTitle()) {
            String title = data.get(realPosition).getTitle();
            if (title == null) {
                title = "";
            }
            this.title.setText(title);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        pagerIdle = state == ViewPager.SCROLL_STATE_IDLE;
    }

    //----------------实现自动轮播--------------------------


    private int delayTime = 5000;

    /**
     * @return 获取轮播间隔时间
     */
    public int getDelayTime() {
        return delayTime;
    }

    /**
     * @param delayTime 设置轮播间隔时间
     */
    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public void stopPageScroll() {
        LogUtils.i("CustomViewPager stop");
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
    }

    public void startPageScroll(int delayTime) {
        LogUtils.i("CustomViewPager move");
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                App.runInMainThread(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                });
            }
        }, delayTime, 5000);
    }

    //----------------自动轮播实现完毕--------------------------


    private long lastClickTime;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //长按停止轮播,短按点击
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastClickTime = System.currentTimeMillis();
                stopPageScroll();
                break;
            case MotionEvent.ACTION_CANCEL:
                startPageScroll(3000);
                break;
            case MotionEvent.ACTION_UP:
                startPageScroll(3000);
                if (pagerIdle && System.currentTimeMillis() - lastClickTime < 300L) {
                    v.performClick();
                }
                break;
        }
        return false;
    }

}
