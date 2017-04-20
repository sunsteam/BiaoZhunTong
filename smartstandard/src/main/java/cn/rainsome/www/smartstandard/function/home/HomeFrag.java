package cn.rainsome.www.smartstandard.function.home;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.apkfuns.logutils.LogUtils;
import com.lzy.okgo.OkGo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.rainsome.www.smartstandard.Info;
import cn.rainsome.www.smartstandard.R;
import cn.rainsome.www.smartstandard.adapter.WebLoader;
import cn.rainsome.www.smartstandard.adapter.list.HomeFunctionAdapter;
import cn.rainsome.www.smartstandard.adapter.list.HomeListAdapter;
import cn.rainsome.www.smartstandard.adapter.recycler.HomeIndustryAdapter;
import cn.rainsome.www.smartstandard.bean.Industry;
import cn.rainsome.www.smartstandard.bean.ViewPagerBean;
import cn.rainsome.www.smartstandard.bean.event.FreshHomeEvent;
import cn.rainsome.www.smartstandard.bean.request.HomeStdRequest;
import cn.rainsome.www.smartstandard.bean.response.IndustryResponse;
import cn.rainsome.www.smartstandard.bean.response.InfoResponse;
import cn.rainsome.www.smartstandard.bean.response.NoticeCountResponse;
import cn.rainsome.www.smartstandard.bean.response.StandardsResponse;
import cn.rainsome.www.smartstandard.function.TemperActivity;
import cn.rainsome.www.smartstandard.net.http.Patron;
import cn.rainsome.www.smartstandard.ui.AnimationListener;
import cn.rainsome.www.smartstandard.ui.AnimatorListener;
import cn.rainsome.www.smartstandard.ui.SmoothListView.SmoothListView;
import cn.rainsome.www.smartstandard.ui.customview.CustomViewPager;
import cn.rainsome.www.smartstandard.ui.customview.HomeIndustryView;
import cn.rainsome.www.smartstandard.ui.recycler.SimpleItemTouchHelperCallback;
import cn.rainsome.www.smartstandard.utils.PageUtils;
import cn.rainsome.www.smartstandard.utils.ResUtils;
import cn.rainsome.www.smartstandard.utils.ToastUtils;
import cn.yomii.www.frame.adapter.Loader;
import cn.yomii.www.frame.bean.request.ListRequestBean;
import cn.yomii.www.frame.ui.fragment.BaseFragment;

/**
 * 首页 Fragment
 * Created by Yomii on 2017/2/10.
 */

public class HomeFrag extends BaseFragment implements SmoothListView.ISmoothListViewListener, View.OnClickListener {


    @Bind(R.id.home_listView)
    SmoothListView listView;
    @Bind(R.id.bar_action_notice)
    ImageView btnNotice;
    @Bind(R.id.bar_action_search)
    ImageView btnSearch;
    @Bind(R.id.home_icon_offline)
    ImageView iconOffline;
    @Bind(R.id.bar_app_name)
    TextView titleName;
    @Bind(R.id.title_root)
    LinearLayout titleRoot;

    //选择行业部分
    @Bind(R.id.btn_close_choose)
    ImageView btnCloseChoose;
    @Bind(R.id.select_industry_container)
    FrameLayout fragContainer;
    @Bind(R.id.choose_industry_page)
    LinearLayout chooseIndustryPage;

    //我来组成头部
    View listHead;
    //广告条
    FrameLayout headAdsFrame;
    private CustomViewPager customViewPager;
    //中部按钮组
    GridView headGrid;
    //推荐行业
    private HomeIndustryAdapter headIndustryAdapter;

    //排序组
    LinearLayout homeLayoutSort;
    TextView homeSortSelect;
    ImageView homeSortDirection;
    private PopupWindow popupWindow;
    //行业筛选
    LinearLayout homeIndustries;
    ImageView homeIconPlus;
    FrameLayout homeAddIndustryFrame;
    HomeChooseIndustryFrag chooseIndustryFrag;


    private HomeListAdapter homeListAdapter;

    private int headHeight = 1;
    private int panelHeight = 1;
    private int actionBarHeight;
    private boolean isSortAsc = true;
    private HomeRx homeRx;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.home_frag, container, false);
        ButterKnife.bind(this, inflate);
        EventBus.getDefault().register(this);

        addListHead();

        addFilter();

        //配置和监听
        setStandardsList();
        setTitleScrollAlpha();
        setCompanyNotice();
        setChooseIndustry();
        setChooseSort();


        return inflate;
    }

    private void addListHead() {
        listHead = View.inflate(getContext(), R.layout.home_head_layout, null);
        //添加自定义Banner
        headAdsFrame = (FrameLayout) listHead.findViewById(R.id.home_advertisement_frame);
        customViewPager = new CustomViewPager(getContext(), 150);
        customViewPager.setOnPagerClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.imitShowToast(customViewPager.getCurrentPageBean().getPageUrl());
            }
        });
        headAdsFrame.addView(customViewPager);
        //头部功能模块Grid
        headGrid = (GridView) listHead.findViewById(R.id.home_middle_grid);
        headGrid.setAdapter(new HomeFunctionAdapter());
        //头部推荐行业Recycler
        RecyclerView headRecyclerView = (RecyclerView) listHead.findViewById(R.id.home_industry_recycler);
        headRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        headIndustryAdapter = new HomeIndustryAdapter();
        headRecyclerView.setAdapter(headIndustryAdapter);
        //滑动辅助
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SimpleItemTouchHelperCallback(headIndustryAdapter));
        itemTouchHelper.attachToRecyclerView(headRecyclerView);
        //推荐行业点击监听
        headIndustryAdapter.setOnItemClickListener(new HomeIndustryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int industryNo) {
                clickOneIndustry(v, industryNo, true);
            }
        });

        headRecyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (headIndustryAdapter != null) {
                        String copy = headIndustryAdapter.getCopy();
                        if (!TextUtils.isEmpty(copy))
                            headIndustryAdapter.sync(homeRx.updateHeadIndustriesPosition(copy));
                    }
                }
                return false;
            }
        });

        listHead.measure(0, 0);
        actionBarHeight = (int) ResUtils.getDimensPixel(getContext(), R.dimen.action_bar_height) + 4;
        headHeight = listHead.getMeasuredHeight() - actionBarHeight;
        listView.addHeaderView(listHead);
    }


    private void addFilter() {
        View listFilter = View.inflate(getContext(), R.layout.home_filter_layout, null);
        homeLayoutSort = (LinearLayout) listFilter.findViewById(R.id.home_ll_sort);
        homeSortSelect = (TextView) listFilter.findViewById(R.id.home_sort_select);
        homeSortDirection = (ImageView) listFilter.findViewById(R.id.home_sort_direction);

        homeIndustries = (LinearLayout) listFilter.findViewById(R.id.home_industries);
        homeIconPlus = (ImageView) listFilter.findViewById(R.id.home_icon_plus);
        homeAddIndustryFrame = (FrameLayout) listFilter.findViewById(R.id.home_frame_industry_add);
        listView.addHeaderView(listFilter);
        //设置排序Popup
        SortClickListener sortClickListener = new SortClickListener();
        View sortList = View.inflate(HomeFrag.this.getContext(), R.layout.home_sort_popup, null);
        sortList.findViewById(R.id.sort_concern).setOnClickListener(sortClickListener);
        sortList.findViewById(R.id.sort_publish).setOnClickListener(sortClickListener);
        sortList.findViewById(R.id.sort_apply).setOnClickListener(sortClickListener);
        sortList.findViewById(R.id.sort_abolish).setOnClickListener(sortClickListener);
        sortList.findViewById(R.id.sort_std_id).setOnClickListener(sortClickListener);
        sortList.findViewById(R.id.sort_std_caption).setOnClickListener(sortClickListener);
        popupWindow = new PopupWindow(sortList, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(R.style.anim_alpha_popuwindow);
    }

    private void setStandardsList() {
        listView.setRefreshEnable(false);
        listView.setLoadMoreEnable(true);
        listView.setSmoothListViewListener(this);
        Loader<HomeStdRequest, StandardsResponse> loader = new WebLoader<>();
        loader.setRequestAndResponseType(new HomeStdRequest(0, 5, 0),StandardsResponse.class);
        homeListAdapter = new HomeListAdapter(loader);
        listView.setAdapter(homeListAdapter);
        loader.setLoadListener(new Loader.OnLoadAspectListener() {
            @Override
            public void onLoadBefore(int i, ListRequestBean listRequestBean) {

            }

            @Override
            public void onLoadAfter(int i) {
                if (listView != null) {
                    listView.getFootEditor().setDataState(i);
                    listView.stopLoadMore();
                }
            }
        });
    }


    private void setTitleScrollAlpha() {
        listView.setOnScrollListener(new SmoothListView.OnSmoothScrollListener() {

            private boolean setAlpha;

            @Override
            public void onSmoothScrolling(View view) {
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                //0 下拉刷新
                //1 广告和功能
                //2 筛选组

                if (firstVisibleItem <= 1) {
                    int scrollY = listHead.getTop();
                    if (scrollY < 0) {
                        setAlpha = true;
                        float s = -scrollY;
                        float v = s / headHeight;
                        v = Math.min(1f, v);
                        titleRoot.setAlpha(v);
                        titleName.setAlpha(v);
                        titleRoot.setClickable(true);
                    } else {
                        titleRoot.setAlpha(0);
                        titleName.setAlpha(0);
                        titleRoot.setClickable(false);
                    }
                } else if (setAlpha) {
                    titleRoot.setAlpha(1f);
                    titleName.setAlpha(1f);
                    setAlpha = false;
                }
            }
        });
    }


    private void setCompanyNotice() {
        btnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Info.isTemperToken()) {
                    ToastUtils.imitShowToast(R.string.error_9988);
                    PageUtils.goToLoginActivity(getContext());
                } else {
                    Intent intent = new Intent(getContext(), TemperActivity
                            .class/*NoticeListActivity.class*/);
                    startActivity(intent);
                }
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PageUtils.goToSearchActivity(getContext());
            }
        });
    }


    private void setChooseIndustry() {

        homeAddIndustryFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chooseIndustryFrag == null)
                    chooseIndustryFrag = new HomeChooseIndustryFrag();
                else if (!chooseIndustryFrag.isAdded())
                    getChildFragmentManager().beginTransaction()
                            .replace(R.id.select_industry_container, chooseIndustryFrag, "chooseIndustry")
                            .commit();

                listView.smoothScrollToPositionFromTop(2, actionBarHeight - 6);

                RotateAnimation rotateAnimation = new RotateAnimation(0, 45,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                        RotateAnimation.RELATIVE_TO_SELF, 0.5f);
                rotateAnimation.setDuration(300);
                rotateAnimation.setStartOffset(100);

                homeIconPlus.startAnimation(rotateAnimation);

                rotateAnimation.setAnimationListener(new AnimationListener() {
                    @Override
                    public void onAnimationEnd(Animation animation) {
                        super.onAnimationEnd(animation);
                        chooseIndustryPage.setVisibility(View.VISIBLE);

                        fragContainer.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver
                                .OnGlobalLayoutListener() {
                            @SuppressWarnings("deprecation")
                            @Override
                            public void onGlobalLayout() {
                                fragContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                                panelHeight = fragContainer.getHeight();

                                LogUtils.i("panelHeight--" + panelHeight);
                                ObjectAnimator.ofFloat(fragContainer, "translationY",
                                        -panelHeight, 0).setDuration(300).start();
                            }
                        });
                    }
                });
            }
        });


        btnCloseChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService
                        (Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                homeListAdapter.clearDataList();
                getFilterList();

                ObjectAnimator translationY = ObjectAnimator.ofFloat(fragContainer, "translationY",
                        0, -panelHeight).setDuration(300);
                translationY.addListener(new AnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        chooseIndustryPage.setVisibility(View.GONE);
                    }
                });
                translationY.start();
            }
        });
    }

    private void getFilterList() {
        homeListAdapter.setIndustryNO(0);
        homeRx.getIndustries().subscribe(new Patron<Industry>() {
            @Override
            public void onNext(Industry industry) {

                HomeIndustryView view = (HomeIndustryView) View.inflate(getContext(), R
                        .layout.home_filter_view, null);
                view.setOnClickListener(HomeFrag.this);
                view.setText(industry.caption);
                view.setTag(industry.no);
                if (homeIndustries.getChildCount() == 0) {
                    LogUtils.i("选中行业--" + industry.no);
                    headIndustryAdapter.updateIndustriesSelected(0);
                    view.setSelected(true);
                }
                homeIndustries.addView(view);
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                listView.startLoadMore();
            }
        });
    }


    private void setChooseSort() {
        homeSortDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSortAsc = !isSortAsc;
                homeSortDirection.setImageResource(isSortAsc ? R.drawable.home_icon_sort_up : R.drawable.home_icon_sort_down);
                if (homeListAdapter != null) {
                    homeListAdapter.setSortAsAsc(isSortAsc);
                    listView.startLoadMore();
                }
            }
        });

        homeSortSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                    return;
                }

                int position = 2 - listView.getFirstVisiblePosition();

                int top = listView.getChildAt(position).getTop();

                LogUtils.i("top--" + top);
                top += (actionBarHeight);

                int offsetX = (int) ResUtils.getDimensPixel(getContext(), R.dimen.dp_8);
                popupWindow.showAtLocation(listHead, Gravity.START | Gravity.TOP, offsetX, top);

            }
        });
    }


    @Override
    protected void initData(View view, Bundle savedInstanceState) {
        homeRx = new HomeRx();
        homeRx.getBanner().subscribe(new Patron<List<ViewPagerBean>>() {
            @Override
            public void onNext(List<ViewPagerBean> viewPagerBeen) {
                customViewPager.setData(viewPagerBeen);
            }
        });

        if (!Info.isTemperToken())
            homeRx.getNewNotice().subscribe(new Patron<NoticeCountResponse>() {
                @Override
                public void onNext(NoticeCountResponse noticeCountResponse) {
                    if (btnNotice != null)
                        btnNotice.setSelected(noticeCountResponse.count > 0);
                }
            });

        homeRx.getServerEnable().subscribe(new Patron<InfoResponse>() {
            @Override
            public void onNext(InfoResponse infoResponse) {
                hideReminder();
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                showReminder();
            }
        });

        homeRx.getHeadIndustries().subscribe(new Patron<IndustryResponse>() {
            @Override
            public void onNext(IndustryResponse industryResponse) {
                headIndustryAdapter.setDataList(industryResponse.records);
            }
        });

        getFilterList();

    }

    private void hideReminder() {
        if (iconOffline != null) {
            iconOffline.clearAnimation();
            iconOffline.setVisibility(View.INVISIBLE);
        }
    }

    private void showReminder() {
        if (iconOffline != null) {
            iconOffline.setVisibility(View.VISIBLE);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            alphaAnimation.setDuration(1000);
            alphaAnimation.setRepeatMode(Animation.REVERSE);
            alphaAnimation.setRepeatCount(Animation.INFINITE);
            alphaAnimation.start();
            iconOffline.setAnimation(alphaAnimation);
        }
    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        if (homeListAdapter != null)
            homeListAdapter.getLoader().loadPage();
    }


    @Subscribe
    public void onEventFresh(FreshHomeEvent e) {
        if (isDetached())
            return;

        homeListAdapter.clearDataList();
        getFilterList();
    }

    @Override
    public void onClick(View v) {
        Object tag = v.getTag();
        Integer industryNo;

        if (tag instanceof Integer) {
            industryNo = (Integer) tag;
        } else {
            industryNo = Integer.parseInt(tag.toString());
        }

        clickOneIndustry(v, industryNo, false);
    }

    private void clickOneIndustry(View v, Integer industryNo, boolean fromRecycler) {
        headIndustryAdapter.updateIndustriesSelected(fromRecycler ? industryNo : 0);
        updateFocusIndustriesSelected(v);

        if (industryNo == 4513) {
            startActivity(new Intent(getContext(), TemperActivity.class/*IndustryStandardsActivity
                .class*/));
        } else {
            homeListAdapter.setIndustryNO(industryNo);
            listView.startLoadMore();
        }
    }

    private void updateFocusIndustriesSelected(View v) {
        int smallChildCount = homeIndustries.getChildCount();
        for (int i = 0; i < smallChildCount; i++) {
            HomeIndustryView view = (HomeIndustryView) homeIndustries.getChildAt(i);
            if (view == v) {
                view.setSelected(true);
            } else if (view.isSelected()) {
                view.setSelected(false);
            }
        }
    }


    class SortClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            TextView t = (TextView) v;
            int sortType = 0;
            switch (t.getId()) {
                case R.id.sort_concern:
                    sortType = 8;
                    break;
                case R.id.sort_publish:
                    sortType = 1;
                    break;
                case R.id.sort_apply:
                    sortType = 2;
                    break;
                case R.id.sort_abolish:
                    sortType = 3;
                    break;
                case R.id.sort_std_id:
                    sortType = 5;
                    break;
                case R.id.sort_std_caption:
                    sortType = 6;
                    break;
            }
            if (homeListAdapter != null) {
                homeListAdapter.setSortBy(sortType);
                listView.startLoadMore();
            }
            homeSortSelect.setText(t.getText().toString());
            popupWindow.dismiss();
        }
    }


    @Override
    public void onDestroyView() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
        super.onDestroyView();
        OkGo.getInstance().cancelTag("home");
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
    }

}
