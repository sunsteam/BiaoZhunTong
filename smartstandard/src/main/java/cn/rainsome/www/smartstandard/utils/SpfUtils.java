package cn.rainsome.www.smartstandard.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.Set;

import cn.rainsome.www.smartstandard.App;

/**
 *
 * Created by Yomii on 2016/4/12.
 */
public class SpfUtils {

    /**
     * SPF文件名
     */
    public static final String SPF = "bst";

    /**
     * 阅读界面引导完成
     */
    public static final String SPF_GUIDE_READING = "guide_reading";

    /**
     * 批注详情引导完成
     */
    public static final String SPF_GUIDE_POSTIL = "guide_postil";




    /**
     * 1. 用于储存用户设置信息的文件名
     * 2. 储存用户名水印的字段
     */
    public static final String USER = "user";

    /**
     * 用户唯一识别号
     */
    public static final String USER_NO = "user_person_no";

    /**
     * 用户唯一识别号
     */
    public static final String USER_COMPANIES = "user_companies";


    /**
     * 权限提示 储存空间
     */
    public static final String AUTHORITY_FILE = "au_file_saving";

    /**
     * 权限提示 地理位置
     */
    public static final String AUTHORITY_LOCATION = "au_location";




    public static SharedPreferences getSpf(String name){
        return App.getContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSpf() {
        return App.getContext().getSharedPreferences(SPF, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getUser() {
        return App.getContext().getSharedPreferences(USER, Context.MODE_PRIVATE);
    }

    public static void saveString(String key, String value){
        getSpf().edit().putString(key, value).apply();
    }

    public static void saveBoolean(String key, Boolean value){
        getSpf().edit().putBoolean(key, value).apply();
    }

    @SuppressLint("CommitPrefEdits")
    public static void saveStringSet(String key, Set<String> stdNoSet){
        getSpf().edit().putStringSet(key,null).commit();
        getSpf().edit().putStringSet(key,stdNoSet).apply();
    }

    public static void userSaveString(String key, String value){
        getUser().edit().putString(key, value).apply();
    }

    public static void userSaveBoolean(String key, Boolean value){
        getUser().edit().putBoolean(key, value).apply();
    }

    public static void userSaveStringSet(String key, Set<String> stdNoSet){
        getUser().edit().putStringSet(key,stdNoSet).apply();
    }


}
