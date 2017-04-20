package cn.rainsome.www.smartstandard.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * 离线批注上传服务
 * Created by Yomii on 2017/2/10.
 */
public class LocalPostilUploadService extends IntentService {

    public LocalPostilUploadService() {
        super("LocalPostilUploadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // TODO: 2017/2/10 离线批注上传服务
    }
}
