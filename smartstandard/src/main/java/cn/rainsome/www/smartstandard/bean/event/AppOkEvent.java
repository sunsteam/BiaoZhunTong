package cn.rainsome.www.smartstandard.bean.event;

/**
 * APP初始化完成广播
 * Created by Yomii on 2016/9/1.
 */
public class AppOkEvent {

    public long initTime;

    public AppOkEvent(long initTime) {
        this.initTime = initTime;
    }
}