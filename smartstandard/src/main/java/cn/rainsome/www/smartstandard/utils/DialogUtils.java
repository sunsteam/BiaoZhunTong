package cn.rainsome.www.smartstandard.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog.Builder;
import android.text.TextUtils;

import cn.rainsome.www.smartstandard.R;


public class DialogUtils {

    private static ProgressDialog progressDialog;

    /**
     * 作 者 : YX_Sun 创建日期 ： 2015-12-23 上午11:03:13
     * <p>
     * 描 述 ：按返回键返回关闭页面的确认提示框
     * <p>
     * ============================================================
     **/
    public static void getBackDialog(final Activity activity, String title, String messege) {
        Builder builder = new Builder(activity);

        builder.setTitle(title);
        builder.setMessage(messege);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                activity.finish();
            }
        });

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    /**
     * 作 者 : YX_Sun 创建日期 ： 2015-12-23 上午11:05:19
     *
     * 描 述 ：获取信息提示对话框
     *
     * ============================================================
     **/
    public static void showInfoDialog(Activity activity, String message) {
        showInfoDialog(activity, message, "提示", null, null);
    }

    public static void showInfoDialog(@NonNull Activity activity, @NonNull String message,
                                      @NonNull String titleStr, String positiveStr,
                                      DialogInterface.OnClickListener onPositiveClick) {
        Builder localBuilder = new Builder(activity);
        localBuilder.setTitle(titleStr);
        localBuilder.setMessage(message);
        if (TextUtils.isEmpty(positiveStr))
            positiveStr = activity.getString(R.string.sure);
        if (onPositiveClick == null)
            onPositiveClick = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            };
        localBuilder.setPositiveButton(positiveStr, onPositiveClick);
        localBuilder.show();
    }

    /**
     * 显示确认提示框
     * @param activity activity
     * @param message message
     * @param onPositiveClick 点击确定后的事件
     */
    public static void showConfirmDialog(Activity activity, String message,
                                         @NonNull DialogInterface.OnClickListener onPositiveClick) {
        showConfirmDialog(activity, message, "提示", null, onPositiveClick,null);
    }

    public static void showConfirmDialog(@NonNull Activity activity, @NonNull String message,
                                         @NonNull String titleStr, String positiveStr,
                                         @NonNull DialogInterface.OnClickListener onPositiveClick,
                                         DialogInterface.OnClickListener onNegtiveClick) {
        Builder localBuilder = new Builder(activity);
        localBuilder.setTitle(titleStr);
        localBuilder.setMessage(message);
        if (TextUtils.isEmpty(positiveStr))
            positiveStr = activity.getString(R.string.sure);
        localBuilder.setPositiveButton(positiveStr, onPositiveClick);
        if (onNegtiveClick == null)
            onNegtiveClick = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            };
        localBuilder.setNegativeButton(R.string.cancel,onNegtiveClick);
        localBuilder.show();
    }

    public static void createProgressDialog(@NonNull Activity activity, @NonNull String message,
                                            @NonNull String titleStr){
        createProgressDialog(activity,message,titleStr,true);
    }

    public static void createProgressDialog(@NonNull Activity activity, @NonNull String message,
                                            @NonNull String titleStr, boolean isCancelable){
        progressDialog = new ProgressDialog(activity);
        progressDialog.setTitle(titleStr);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(isCancelable);
        progressDialog.setIndeterminate(true);//设置滚动条的状态为滚动
        progressDialog.show();
    }

    public static void hideProgressDialog(){
        if (progressDialog != null){
            progressDialog.cancel();
            progressDialog = null;
        }
    }

    public static void setProgressMessage(String message){
        if (progressDialog != null){
            progressDialog.setMessage(message);
        }
    }
}
