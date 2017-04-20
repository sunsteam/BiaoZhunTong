package cn.rainsome.www.smartstandard.function;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.apkfuns.logutils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.rainsome.www.smartstandard.App;
import cn.rainsome.www.smartstandard.bean.event.AppOkEvent;
import cn.rainsome.www.smartstandard.function.main.MainActivity;

/**
 * 欢迎界面
 * Created by Yomii on 2017/2/10.
 */

public class SplashActivity extends AppCompatActivity {

    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        countDownTimer = new CountDownTimer(12000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                LogUtils.i("millisUntilFinished  " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                chooseToActivity();
            }
        }.start();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAppOkEvent(AppOkEvent e) {
        long temperTime = (e != null) ? e.initTime : 2000;
        long showTime = 2000 - temperTime;
        countDownTimer.cancel();
        if (showTime < 0)
            chooseToActivity();
        else {
            App.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    chooseToActivity();
                }
            }, showTime);
        }
    }

    /**
     * 选择进入的界面(假设有选择行业界面的话)
     */
    private void chooseToActivity() {
        goToMainActivity();
    }


    private void goToMainActivity() {
        //        Intent mainIntent = new Intent(SplashActivity.this, NewReadingActivity.class);
        //        mainIntent.putExtra("stdno",621216);//工业建筑供暖
        //        mainIntent.putExtra("stdno",604634);//公路路基规范  322kb
        //        mainIntent.putExtra("stdno",548450);//防洪标准
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
