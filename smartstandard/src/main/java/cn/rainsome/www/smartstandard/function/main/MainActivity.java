package cn.rainsome.www.smartstandard.function.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import cn.rainsome.www.smartstandard.App;
import cn.rainsome.www.smartstandard.Info;
import cn.rainsome.www.smartstandard.MainApi;
import cn.rainsome.www.smartstandard.R;
import cn.rainsome.www.smartstandard.bean.response.MsgCountResponse;
import cn.rainsome.www.smartstandard.net.http.JsonCallback;
import cn.rainsome.www.smartstandard.utils.DialogUtils;
import cn.yomii.www.frame.ui.activity.BaseActivity;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 主页面
 * Created by Yomii on 2017/2/10.
 */

public class MainActivity extends BaseActivity {

    private FragmentTabHost tabHost;

    private TextView responseMes;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View
                    .SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initView() {
        setContentView(R.layout.main_activity);
        tabHost = (FragmentTabHost) findViewById(R.id.tabHost);
    }

    @Override
    protected void initData() {
        //初始化底部TAB
        tabHost.setup(this, getSupportFragmentManager(), R.id.content);
        tabHost.getTabWidget().setShowDividers(0);
        //添加Tab内容
        initTab();
    }

    private void initTab() {
        tabHost.clearAllTabs();
        TabEnum[] tabInfos = TabEnum.values();
        for (TabEnum tabInfo : tabInfos) {

            if (Info.isCompanyToken() && tabInfo.getIndex() == 1)
                continue;

            /*
             * A tab has a tab indicator, content, and a tag that is used to keep
             * track of it.  This builder helps choose among these options.
             *
             * For the tab indicator, your choices are:
             * 1) set a label
             * 2) set a label and an icon
             *
             * For the tab content, your choices are:
             * 1) the id of a {@link View}
             * 2) a {@link TabContentFactory} that creates the {@link View} content.
             * 3) an {@link Intent} that launches an {@link Activity}.
             */
            TabHost.TabSpec tab = tabHost.newTabSpec(getString(tabInfo.getTitle()));
            // ------------------------------------------------- 自定义选项卡 ↓
            View indicator = View.inflate(this, R.layout.main_tab_indicator, null);
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);

            if (tabInfo.getIndex() == 2) {
                responseMes = (TextView) indicator.findViewById(R.id.tab_mes);
            }

            Drawable drawable = ResourcesCompat.getDrawable(getResources(), tabInfo.getIcon(), getTheme());
            title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null, null);
            title.setText(getString(tabInfo.getTitle()));
            //2.set custom indicator
            tab.setIndicator(indicator);
            //3.set content
            tab.setContent(new TabHost.TabContentFactory() {

                @Override
                public View createTabContent(String tag) {
                    return new View(MainActivity.this);
                }
            });
            //-----------------------自定义Tab创建完成
            Bundle bundle = new Bundle();
            bundle.putString("key", "content: " + getString(tabInfo.getTitle()));
            // 2. 把新的选项卡添加到TabHost中
            tabHost.addTab(tab, tabInfo.getClazz(), bundle);
        }
    }

    @Override
    public void onBackPressed() {
        DialogUtils.showConfirmDialog(this, "是否退出程序?", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                App.finishActivity();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initTab();
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkResponseMsg();
    }

    private void checkResponseMsg() {
        if (Info.isTemperToken())
            return;

        MainApi.unreadResponse("main").execute(new JsonCallback<MsgCountResponse>() {
            @Override
            public void onSuccess(MsgCountResponse msgCountResponse, Call call, Response response) {
                if (msgCountResponse.count > 0) {
                    responseMes.setBackgroundResource(R.drawable.oval_ff0000);
                    responseMes.setText(String.valueOf(msgCountResponse.count));
                } else {
                    responseMes.setBackgroundResource(0);
                    responseMes.setText("");
                }
            }
        });
    }
}
