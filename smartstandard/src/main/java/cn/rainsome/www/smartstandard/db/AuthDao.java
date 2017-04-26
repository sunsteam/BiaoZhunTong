package cn.rainsome.www.smartstandard.db;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;

import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.rainsome.www.smartstandard.App;
import cn.rainsome.www.smartstandard.Info;
import cn.rainsome.www.smartstandard.bean.Auth;
import cn.rainsome.www.smartstandard.bean.Standard;
import cn.rainsome.www.smartstandard.bean.request.NumberRequest;
import cn.rainsome.www.smartstandard.net.http.ApiWatcher;
import cn.rainsome.www.smartstandard.net.http.HttpHelper;

/**
 * 权限Dao
 * Created by Yomii on 2016/8/23.
 */
public class AuthDao {
    private DB_Helper helper;

    public AuthDao() {
        helper = DB_Helper.getInstance(App.getContext());
    }

    private static String[] queryComments = new String[]{DBConstants.AUTH_STDNO,
            DBConstants.AUTH_STDTYPE, DBConstants.AUTH_STDSIZE, DBConstants.AUTH_EDITTIME,
            DBConstants.AUTH_UNSAVE};


    public void insert(final Auth outAuth) {
        HttpHelper.getApiMain().standardAuth(new NumberRequest("app_topical_detail", outAuth.stdNo))
                .subscribe(new ApiWatcher<Auth>() {
                    @Override
                    public void onNext(Auth auth) {
                        SQLiteDatabase db = helper.getWritableDatabase();
                        ContentValues values = new ContentValues();
                        values.put(DBConstants.AUTH_STDNO, outAuth.stdNo);
                        values.put(DBConstants.AUTH_UID, outAuth.uerId);
                        values.put(DBConstants.AUTH_UNO, outAuth.uerNo);
                        values.put(DBConstants.AUTH_STDTYPE, outAuth.stdType);
                        values.put(DBConstants.AUTH_STDSIZE, outAuth.stdSize);
                        values.put(DBConstants.AUTH_EDITTIME, outAuth.editTime);
                        values.put(DBConstants.AUTH_UNSAVE, outAuth.unSave);
                        //题录信息
                        values.put(DBConstants.AUTH_CAPTION, auth.caption);
                        values.put(DBConstants.AUTH_STDID, auth.stdid);
                        values.put(DBConstants.AUTH_FOREIGNCAPTION, auth.foreigncaption);
                        values.put(DBConstants.AUTH_REVISION, auth.revision);
                        values.put(DBConstants.AUTH_WRITEDEPT, auth.writedept);
                        values.put(DBConstants.AUTH_PUBLISHER, auth.publisher);
                        values.put(DBConstants.AUTH_PUBLISHEDWORD, auth.publishedword);
                        values.put(DBConstants.AUTH_PUBLISHDATE, auth.publishdate);
                        values.put(DBConstants.AUTH_PERFORMDATE, auth.performdate);
                        values.put(DBConstants.AUTH_EXPIREDDATE, auth.expireddate);
                        values.put(DBConstants.AUTH_FORMAT, auth.format);

                        if (auth.rpls != null && auth.rpls.size() > 0) {
                            Auth.RplsEntity rplsEntity = auth.rpls.get(0);
                            values.put(DBConstants.AUTH_RPLSTDNO, rplsEntity.no);
                            values.put(DBConstants.AUTH_RPLSTDID, rplsEntity.stdid);
                            values.put(DBConstants.AUTH_RELTYPE, rplsEntity.reltype);
                        }

                        //12.12更新
                        values.put(DBConstants.AUTH_CHIEFDEPT, auth.chiefdept);
                        values.put(DBConstants.AUTH_CANREAD, auth.canread);
                        values.put(DBConstants.AUTH_PRICE, auth.price);
                        values.put(DBConstants.AUTH_EPRICE, auth.eprice);
                        values.put(DBConstants.AUTH_DRAFTDEPTKEY, auth.draftdeptkey);
                        values.put(DBConstants.AUTH_CHIEFUNITKEY, auth.chiefunitkey);
                        values.put(DBConstants.AUTH_DRAFTDEPT, auth.draftdept);
                        values.put(DBConstants.AUTH_CHIEFUNIT, auth.chiefunit);
                        values.put(DBConstants.AUTH_BELONG, auth.belong);
                        values.put(DBConstants.AUTH_SUMMARY, auth.summary);
                        values.put(DBConstants.AUTH_ISPREFACE, auth.ispreface);
                        values.put(DBConstants.AUTH_STATUS, auth.status);
                        values.put(DBConstants.AUTH_LABELS, "");

                        long insert = db.insert(DBConstants.TB_AUTH, null, values);

                        LogUtils.i("auth插入成功: " + (insert != -1));
                        LocalBroadcastManager.getInstance(App.getContext())
                                .sendBroadcast(new Intent("action.update_cached"));
                    }
                });
    }

    /**
     * 根据stdno更新权限的类型和容量和修改时间
     */
    public int update(Auth auth) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBConstants.AUTH_STDTYPE, auth.stdType);
        values.put(DBConstants.AUTH_STDSIZE, auth.stdSize);
        values.put(DBConstants.AUTH_EDITTIME, auth.editTime);
        values.put(DBConstants.AUTH_UNSAVE, auth.unSave);
        String selection = DBConstants.AUTH_STDNO + " = ?";
        int update = db.update(DBConstants.TB_AUTH, values, selection, new String[]{String.valueOf(auth.stdNo)});
        LogUtils.i("auth更新条目: " + update);
        return update;
    }

    /**
     * 更新状态为等待更新
     */
    public void update(String whereArgs) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update " + DBConstants.TB_AUTH
                + " set " + DBConstants.AUTH_STDTYPE + " = -2, "
                + DBConstants.AUTH_STDSIZE + " = 0 where "
                + DBConstants.AUTH_STDNO + " in (" + whereArgs + ");";
        LogUtils.i("Auth更新sql: " + sql);
        db.execSQL(sql);
    }


    /**
     * 获取通用数据信息
     */
    public Auth query(int stdNo) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = DBConstants.AUTH_STDNO + " = ?";
        Cursor cursor = db.query(DBConstants.TB_AUTH, queryComments, selection, new String[]{
                String.valueOf(stdNo)}, null, null, null);
        Auth auth = null;
        if (cursor != null) {
            if (cursor.moveToNext()) {
                int authNo = cursor.getInt(0);
                int type = cursor.getInt(1);
                long size = cursor.getLong(2);
                String editTime = cursor.getString(3);
                String unsave = cursor.getString(4);

                auth = new Auth(authNo, type, size, editTime, unsave);
            }
            cursor.close();
        }
        return auth;
    }


    /**
     * 获取当前账号下是否有阅读权限
     */
    public int queryTypeByNo(int stdNo) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = DBConstants.AUTH_UNO + " = ? and " + DBConstants.AUTH_STDNO + " = ?";
        Cursor cursor = db.query(DBConstants.TB_AUTH, queryComments, selection, new String[]{
                String.valueOf(Info.getPsnNo()), String.valueOf(stdNo)
        }, null, null, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                int type = cursor.getInt(1);
                cursor.close();
                return type;
            }
        }
        return 0;
    }

    /**
     * 查询数据是否存在
     */
    public int exist(int stdNo) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = DBConstants.AUTH_STDNO + " = ?";
        Cursor cursor = db.query(DBConstants.TB_AUTH, queryComments, selection,
                new String[]{String.valueOf(stdNo)}, null, null, null);

        if (cursor != null) {
            if (cursor.moveToNext()) {
                int type = cursor.getInt(1);
                cursor.close();
                return type;
            }
        }
        return 0;
    }


    /**
     * 查询数据占用容量(byte)
     */
    public long getLength(int stdNo) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = DBConstants.AUTH_STDNO + " = ?";
        Cursor cursor = db.query(DBConstants.TB_AUTH, queryComments, selection,
                new String[]{String.valueOf(stdNo)}, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                long size = cursor.getLong(2);
                if (size > 0)
                    return size;
            }
            cursor.close();
        }
        return 0;
    }

    /**
     * 获取当前账号下的所有阅读权限
     */
    public List<Auth> getCachesUnderId() {
        ArrayList<Auth> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = DBConstants.AUTH_UNO + " = ?";
        Cursor cursor = db.query(DBConstants.TB_AUTH, queryComments, selection,
                new String[]{String.valueOf(Info.getPsnNo())}, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int stdNo = cursor.getInt(0);
                int type = cursor.getInt(1);
                long size = cursor.getLong(2);
                String editTime = cursor.getString(3);
                String unSave = cursor.getString(4);

                list.add(new Auth(stdNo, type, size, editTime, unSave));
            }
            cursor.close();
        }
        return list;
    }


    /**
     * 获取当前账号下有阅读权限的数据修订时间
     */
    public HashMap<Integer, String> getRevisionsUnderId() {
        HashMap<Integer, String> revisionMap = new HashMap<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = DBConstants.AUTH_UNO + " = ?";
        Cursor cursor = db.query(DBConstants.TB_AUTH, queryComments, selection,
                new String[]{String.valueOf(Info.getPsnNo())}, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int stdNo = cursor.getInt(0);

                String editTime = cursor.getString(3);
                if (editTime == null)
                    editTime = "";

                revisionMap.put(stdNo, editTime);
            }
            cursor.close();
        }
        return revisionMap;
    }

    public List<Auth> queryAll() {
        List<Auth> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select distinct * from reading_authority where user_no=?",
                new String[]{String.valueOf(Info.getPsnNo())});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Auth auth = new Auth();
                auth.stdNo = cursor.getInt(3);
                auth.stdType = cursor.getInt(4);
                auth.stdSize = cursor.getLong(5);
                auth.caption = cursor.getString(7);
                auth.stdid = cursor.getString(8);
                auth.foreigncaption = cursor.getString(9);
                auth.revision = cursor.getString(10);
                auth.rplstdno = cursor.getInt(11);
                auth.rplstdid = cursor.getString(12);
                auth.writedept = cursor.getString(13);
                auth.publisher = cursor.getString(14);
                auth.publishedword = cursor.getString(15);
                auth.publishdate = cursor.getString(16);
                auth.performdate = cursor.getString(17);
                auth.expireddate = cursor.getString(18);
                auth.format = cursor.getInt(19);
                auth.reltype = cursor.getInt(20);
                auth.editTime = cursor.getString(21);

                //12.12更新
                auth.chiefdept = cursor.getString(22);
                auth.canread = cursor.getInt(23);
                auth.price = cursor.getFloat(24);
                auth.eprice = cursor.getFloat(25);
                auth.draftdeptkey = cursor.getString(26);
                auth.chiefunitkey = cursor.getString(27);
                auth.draftdept = cursor.getString(28);
                auth.chiefunit = cursor.getString(29);
                auth.belong = cursor.getString(30);
                auth.summary = cursor.getString(31);
                auth.ispreface = cursor.getInt(32);
                auth.status = cursor.getInt(33);
                //12.20更新
                auth.unSave = cursor.getString(34);
                list.add(auth);
            }
            cursor.close();
        }
        return list;
    }

    /**
     * 根据关键字查找阅读权限
     */
    public List<Auth> queryByKey(String key) {
        List<Auth> list = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select distinct * from reading_authority where user_no=? and (caption like ? or stdid like ?)",
                new String[]{String.valueOf(Info.getPsnNo()), "%" + key + "%", "%" + key + "%"});
        if (cursor != null) {
            while (cursor.moveToNext()) {
                Auth auth = new Auth();
                auth.stdNo = cursor.getInt(3);
                auth.stdType = cursor.getInt(4);
                auth.stdSize = cursor.getLong(5);
                auth.caption = cursor.getString(7);
                auth.stdid = cursor.getString(8);
                auth.foreigncaption = cursor.getString(9);
                auth.revision = cursor.getString(10);
                auth.rplstdno = cursor.getInt(11);
                auth.rplstdid = cursor.getString(12);
                auth.writedept = cursor.getString(13);
                auth.publisher = cursor.getString(14);
                auth.publishedword = cursor.getString(15);
                auth.publishdate = cursor.getString(16);
                auth.performdate = cursor.getString(17);
                auth.expireddate = cursor.getString(18);
                auth.format = cursor.getInt(19);
                auth.reltype = cursor.getInt(20);
                auth.editTime = cursor.getString(21);
                //12.12更新
                auth.chiefdept = cursor.getString(22);
                auth.canread = cursor.getInt(23);
                auth.price = cursor.getFloat(24);
                auth.eprice = cursor.getFloat(25);
                auth.draftdeptkey = cursor.getString(26);
                auth.chiefunitkey = cursor.getString(27);
                auth.draftdept = cursor.getString(28);
                auth.chiefunit = cursor.getString(29);
                auth.belong = cursor.getString(30);
                auth.summary = cursor.getString(31);
                auth.ispreface = cursor.getInt(32);
                auth.status = cursor.getInt(33);
                //12.20更新
                auth.unSave = cursor.getString(34);
                list.add(auth);
            }
            cursor.close();
        }
        return list;
    }

    /**
     * 按Stdno查找,作为标准信息输出
     */
    public Standard queryByNo(int no) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from reading_authority where std_no=?",
                new String[]{String.valueOf(no)});
        Standard standard = new Standard();
        if (cursor != null && cursor.moveToNext()) {
            standard.no = cursor.getInt(3);
            standard.caption = cursor.getString(7);
            standard.stdid = cursor.getString(8);
            standard.foreigncaption = cursor.getString(9);
            //                standard.revision = cursor.getInt(5);
            standard.rplstdno = cursor.getInt(11);
            standard.rplstdid = cursor.getString(12);
            //standard.chiefdept = cursor.getString(13);
            standard.publisher = cursor.getString(14);
            standard.publishedword = cursor.getString(15);
            standard.publishdate = cursor.getString(16);
            standard.performdate = cursor.getString(17);
            standard.expireddate = cursor.getString(18);
            standard.format = cursor.getInt(19);
            //standard.reltype = cursor.getInt(20);
            //12.12更新
            //standard.chiefdept = cursor.getString(22);
            standard.canread = cursor.getInt(23);
            standard.price = cursor.getFloat(24);
            standard.eprice = cursor.getFloat(25);
//            standard.draftdeptkey = cursor.getString(26);
//            standard.chiefunitkey = cursor.getString(27);
//            standard.draftdept = cursor.getString(28);
//            standard.chiefunit = cursor.getString(29);
//            standard.belong = cursor.getString(30);
//            standard.summary = cursor.getString(31);
//            standard.ispreface = cursor.getInt(32);
            standard.status = cursor.getInt(33);
            cursor.close();
        }
        return standard;
    }


    public boolean deleteAuth(List<Integer> list) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int deleteAuthResult = 0;
        for (int i = 0; i < list.size(); i++) {
            deleteAuthResult = db.delete(DBConstants.TB_AUTH, DBConstants.AUTH_STDNO + " = ?",
                    new String[]{String.valueOf(list.get(i))});
        }
        return deleteAuthResult != 0;
    }
}
