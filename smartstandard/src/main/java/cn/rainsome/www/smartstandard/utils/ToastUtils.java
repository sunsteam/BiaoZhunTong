package cn.rainsome.www.smartstandard.utils;

import android.widget.Toast;

import cn.rainsome.www.smartstandard.App;

public class ToastUtils {

    private static Toast toast,timetoast;
    private static String oldMsg;
    private static long oneTime = 0;
    private static long twoTime = 0;

    public static void imitShowToast(String string) {
        if (toast == null) {
            toast = Toast.makeText(App.getContext(), string, Toast.LENGTH_SHORT);
        } else {
            toast.setText(string);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        toast.show();
    }

    public static void imitShowToast(int StringRes) {
        imitShowToast(App.getContext().getString(StringRes));
    }



    public static void showToast(String s) {
        if (timetoast == null) {
            timetoast = Toast.makeText(App.getContext(), s, Toast.LENGTH_SHORT);
            timetoast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    timetoast.show();
                }
            } else {
                oldMsg = s;
                timetoast.setText(s);
                timetoast.show();
            }
        }
        oneTime = twoTime;
    }

    /**
     * 取消吐司显示
     */
    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }

}