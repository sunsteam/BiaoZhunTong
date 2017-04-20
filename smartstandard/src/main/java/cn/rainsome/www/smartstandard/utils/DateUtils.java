package cn.rainsome.www.smartstandard.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yomii on 2016/3/21.
 */
public class DateUtils {

    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat tdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getDateStringByLong(long millis) {
        Date date = new Date(millis);
        return sdf.format(date);
    }

    public static String getTimeStringByLong(long millis) {
        Date date = new Date(millis);
        return tdf.format(date);
    }

    public static Date getDateByString(String dateStr) {
        if (dateStr == null)
            return null;

        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getLongByStrDate(String dateStr) {
        if (dateStr == null)
            return -1;

        try {
            return sdf.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static long getLongByStrTime(String dateStr) {
        if (dateStr == null)
            return -1;

        try {
            return tdf.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
