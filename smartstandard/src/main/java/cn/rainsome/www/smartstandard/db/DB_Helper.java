package cn.rainsome.www.smartstandard.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DB_Helper
 * Created by Yomii on 2016/3/19.
 */
public class DB_Helper extends SQLiteOpenHelper {

    private static DB_Helper helper;

    private DB_Helper(Context context) {
        super(context, DBConstants.DBNAME, null, DBConstants.DBVERSION);
    }

    public static DB_Helper getInstance(Context context) {
        if (helper == null) {
            synchronized (DB_Helper.class) {
                if (helper == null)
                    helper = new DB_Helper(context);
            }
        }
        return helper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(create_POSTIL);
        db.execSQL(create_CLAUSE);
        db.execSQL(create_SEARCHRECORD);
        db.execSQL(create_TOPICAL);
        db.execSQL(create_ONYM);
        db.execSQL(create_AUTH);
        db.execSQL(create_SERVER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL(create_SEARCHRECORD);
            db.execSQL(create_TOPICAL);
        }
        if (oldVersion < 3) {
            db.execSQL(create_ONYM);
        }
        if (oldVersion < 4) {
            //题录表添加数据形式字段
            String update_TOPICAL = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_FORMAT + " integer";
            db.execSQL(update_TOPICAL);
        }
        if (oldVersion < 5) {
            //删除旧批注表
            db.execSQL("DROP TABLE comment");
            //新建新批注表
            db.execSQL(create_POSTIL);
            //新建阅读权限表
            db.execSQL(create_AUTH);
            db.execSQL("delete from " + DBConstants.TB_CLAUSE);
            db.execSQL("delete from " + DBConstants.TB_ONYM);
        }
        if (oldVersion < 6) {
            db.execSQL(create_SERVER);
            //删除旧历史记录表
            db.execSQL("DROP TABLE searchrecord");
            //新建历史记录表
            db.execSQL(create_SEARCHRECORD);
        }
        if (oldVersion < 7) {
            //权限表添加编辑时间字段
            String update_Auth = "alter table " + DBConstants.TB_AUTH + " add column "
                    + DBConstants.AUTH_EDITTIME + " varchar(20)";
            db.execSQL(update_Auth);
        }
        if (oldVersion < 8) {
            //删除旧历史记录表
            db.execSQL("DROP TABLE searchrecord");
            //新建历史记录表
            db.execSQL(create_SEARCHRECORD);
            //题录表更新
            String update_TOPICAL_CHIEFDEPT = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_CHIEFDEPT + " varchar(200)";
            String update_TOPICAL_UID = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_UID + " varchar(50)";
            String update_TOPICAL_UNO = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_UNO + " integer";
            String update_TOPICAL_CSMNO = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_CSMNO + " integer";
            String update_TOPICAL_CANREAD = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_CANREAD + " integer";
            String update_TOPICAL_PRICE = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_PRICE + " varchar(10)";
            String update_TOPICAL_EPRICEL = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_EPRICE + " varchar(10)";
            String update_TOPICAL_DRAFTDEPTKEY = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_DRAFTDEPTKEY + " varchar(200)";
            String update_TOPICAL_CHIEFUNITKEY = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_CHIEFUNITKEY + " varchar(200)";
            String update_TOPICAL_DRAFTDEPT = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_DRAFTDEPT + " varchar(200)";
            String update_TOPICAL_CHIEFUNIT = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_CHIEFUNIT + " varchar(200)";
            String update_TOPICAL_BELONG = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_BELONG + " varchar(200)";
            String update_TOPICAL_SUMMARY = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_SUMMARY + " varchar(500)";
            String update_TOPICAL_ISPREFACE = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_ISPREFACE + " integer";
            String update_TOPICAL_RELTYPE = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_RELTYPE + " integer";
            String update_TOPICAL_EDITTIME = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_EDITTIME + " varchar(20)";
            String update_TOPICAL_STATUS = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_STATUS + " integer";
            String update_TOPICAL_LABELS = "alter table " + DBConstants.TB_TOPICAL + " add column "
                    + DBConstants.TOPICAL_LABELS + " varchar(1000)";
            db.execSQL(update_TOPICAL_CHIEFDEPT);
            db.execSQL(update_TOPICAL_UID);
            db.execSQL(update_TOPICAL_UNO);
            db.execSQL(update_TOPICAL_CSMNO);
            db.execSQL(update_TOPICAL_CANREAD);
            db.execSQL(update_TOPICAL_PRICE);
            db.execSQL(update_TOPICAL_EPRICEL);
            db.execSQL(update_TOPICAL_DRAFTDEPTKEY);
            db.execSQL(update_TOPICAL_CHIEFUNITKEY);
            db.execSQL(update_TOPICAL_DRAFTDEPT);
            db.execSQL(update_TOPICAL_CHIEFUNIT);
            db.execSQL(update_TOPICAL_BELONG);
            db.execSQL(update_TOPICAL_SUMMARY);
            db.execSQL(update_TOPICAL_ISPREFACE);
            db.execSQL(update_TOPICAL_RELTYPE);
            db.execSQL(update_TOPICAL_EDITTIME);
            db.execSQL(update_TOPICAL_STATUS);
            db.execSQL(update_TOPICAL_LABELS);

            //阅读权限表更新
            String update_AUTH_CHIEFDEPT = "alter table " + DBConstants.TB_AUTH + " add column "
                    + DBConstants.AUTH_CHIEFDEPT + " varchar(200)";
            String update_AUTH_CANREAD = "alter table " + DBConstants.TB_AUTH + " add column "
                    + DBConstants.AUTH_CANREAD + " integer";
            String update_AUTH_PRICE = "alter table " + DBConstants.TB_AUTH + " add column "
                    + DBConstants.AUTH_PRICE + " varchar(10)";
            String update_AUTH_EPRICEL = "alter table " + DBConstants.TB_AUTH + " add column "
                    + DBConstants.AUTH_EPRICE + " varchar(10)";
            String update_AUTH_DRAFTDEPTKEY = "alter table " + DBConstants.TB_AUTH + " add column "
                    + DBConstants.AUTH_DRAFTDEPTKEY + " varchar(200)";
            String update_AUTH_CHIEFUNITKEY = "alter table " + DBConstants.TB_AUTH + " add column "
                    + DBConstants.AUTH_CHIEFUNITKEY + " varchar(200)";
            String update_AUTH_DRAFTDEPT = "alter table " + DBConstants.TB_AUTH + " add column "
                    + DBConstants.AUTH_DRAFTDEPT + " varchar(200)";
            String update_AUTH_CHIEFUNIT = "alter table " + DBConstants.TB_AUTH + " add column "
                    + DBConstants.AUTH_CHIEFUNIT + " varchar(200)";
            String update_AUTH_BELONG = "alter table " + DBConstants.TB_AUTH + " add column "
                    + DBConstants.AUTH_BELONG + " varchar(200)";
            String update_AUTH_SUMMARY = "alter table " + DBConstants.TB_AUTH + " add column "
                    + DBConstants.AUTH_SUMMARY + " varchar(500)";
            String update_AUTH_ISPREFACE = "alter table " + DBConstants.TB_AUTH + " add column "
                    + DBConstants.AUTH_ISPREFACE + " integer";
            String update_AUTH_STATUS = "alter table " + DBConstants.TB_AUTH + " add column "
                    + DBConstants.AUTH_STATUS + " integer";
            String update_AUTH_LABELS = "alter table " + DBConstants.TB_AUTH + " add column "
                    + DBConstants.AUTH_LABELS + " varchar(1000)";
            db.execSQL(update_AUTH_CHIEFDEPT);
            db.execSQL(update_AUTH_CANREAD);
            db.execSQL(update_AUTH_PRICE);
            db.execSQL(update_AUTH_EPRICEL);
            db.execSQL(update_AUTH_DRAFTDEPTKEY);
            db.execSQL(update_AUTH_CHIEFUNITKEY);
            db.execSQL(update_AUTH_DRAFTDEPT);
            db.execSQL(update_AUTH_CHIEFUNIT);
            db.execSQL(update_AUTH_BELONG);
            db.execSQL(update_AUTH_SUMMARY);
            db.execSQL(update_AUTH_ISPREFACE);
            db.execSQL(update_AUTH_STATUS);
            db.execSQL(update_AUTH_LABELS);
        }

        if (oldVersion < 9){
            //权限表添加完整度字段
            String update_Auth_Complete = "alter table " + DBConstants.TB_AUTH + " add column "
                    + DBConstants.AUTH_UNSAVE + " varchar(2000)";
            db.execSQL(update_Auth_Complete);
        }
    }


    /**
     * 创建批注表
     */
    String create_POSTIL = "create table " + DBConstants.TB_POSTIL + " ("
            + DBConstants._ID + " integer primary key autoincrement,"
            + DBConstants.POSTIL_CONTENT + " varchar(10000),"
            + DBConstants.POSTIL_QUOTE + " varchar(10000),"
            + DBConstants.POSTIL_EDITMILLIS + " varchar(30),"
            + DBConstants.POSTIL_AUTHORITY + " varchar(4),"
            + DBConstants.POSTIL_CLAUSENO + " integer,"
            + DBConstants.POSTIL_STDNO + " integer,"
            + DBConstants.POSTIL_PSNUID + " varchar(50),"
            + DBConstants.POSTIL_SYNC + " varchar(4),"
            //8/22新增
            + DBConstants.POSTIL_NO + " integer unique,"
            + DBConstants.POSTIL_VERSION + " integer,"
            + DBConstants.POSTIL_TYPE + " integer,"
            + DBConstants.POSTIL_ISREP + " integer,"
            + DBConstants.POSTIL_PSNNO + " integer,"
            + DBConstants.POSTIL_TILNO + " integer)";

    /**
     * 创建结构化数据表
     */
    String create_CLAUSE = "create table " + DBConstants.TB_CLAUSE + " ("
            + DBConstants._ID + " integer primary key autoincrement,"
            + DBConstants.CLAUSE_NO + " integer unique not null,"
            + DBConstants.CLAUSE_PARENTNO + " integer,"
            + DBConstants.CLAUSE_SORTBY + " integer,"
            + DBConstants.CLAUSE_CHAPTER + " varchar(200),"
            + DBConstants.CLAUSE_CAPTION + " varchar(10000),"
            + DBConstants.CLAUSE_GENRE + " integer,"
            + DBConstants.CLAUSE_ISCATALOG + " integer,"
            + DBConstants.CLAUSE_EXPLAIN + " integer,"
            + DBConstants.CLAUSE_MANDATORY + " integer,"
            + DBConstants.CLAUSE_FAILING + " varchar(10000),"
            + DBConstants.CLAUSE_STDID + " varchar(200),"
            + DBConstants.CLAUSE_STDNO + " integer)";


    /**
     * 创建搜索结果表
     */
    String create_SEARCHRECORD = "create table " + DBConstants.TB_SEARCHRECORD + " ("
            + DBConstants.SEARCHRECORD_NO + " integer primary key autoincrement,"
            + DBConstants.SEARCHRECORD_ANDOR + " integer,"
            + DBConstants.SEARCHRECORD_STDID + " varchar(50),"
            + DBConstants.SEARCHRECORD_CAPTION + " varchar(50),"
            + DBConstants.SEARCHRECORD_PZWH + " varchar(50),"
            + DBConstants.SEARCHRECORD_SDCCAPTION + " varchar(50),"
            + DBConstants.SEARCHRECORD_NODNO + " integer,"
            + DBConstants.SEARCHRECORD_STDSTATES + " varchar(50),"
            + DBConstants.SEARCHRECORD_PUBSTART + " varchar(50),"
            + DBConstants.SEARCHRECORD_PUBEND + " varchar(50),"
            + DBConstants.SEARCHRECORD_CARRYSTART + " varchar(50),"
            + DBConstants.SEARCHRECORD_CARRYEND + " varchar(50),"
            + DBConstants.SEARCHRECORD_FZSTART + " varchar(50),"
            + DBConstants.SEARCHRECORD_FZEND + " varchar(50),"
            + DBConstants.SEARCHRECORD_TIME + " varchar(30))";


    /**
     * 创建题录表
     */
    String create_TOPICAL = "create table " + DBConstants.TB_TOPICAL + " ("
            + DBConstants._ID + " integer primary key autoincrement,"
            + DBConstants.TOPICAL_STDNO + " integer unique not null,"
            + DBConstants.TOPICAL_CAPTION + " varchar(300),"
            + DBConstants.TOPICAL_STDID + " varchar(100),"
            + DBConstants.TOPICAL_FOREIGNCAPTION + " varchar(300),"
            + DBConstants.TOPICAL_REVISION + " integer,"
            + DBConstants.TOPICAL_RPLSTDNO + " integer,"
            + DBConstants.TOPICAL_RPLSTDID + " varchar(100),"
            + DBConstants.TOPICAL_WRITEDEPT + " varchar(100),"
            + DBConstants.TOPICAL_PUBLISHER + " varchar(100),"
            + DBConstants.TOPICAL_PUBLISHEDWORD + " varchar(50),"
            + DBConstants.TOPICAL_PUBLISHDATE + " varchar(50),"
            + DBConstants.TOPICAL_PERFORMDATE + " varchar(50),"
            + DBConstants.TOPICAL_EXPIREDDATE + " varchar(50),"
            + DBConstants.TOPICAL_QUERYBY + " integer,"
            + DBConstants.TOPICAL_FORMAT + " integer,"
            //12月12新增
            + DBConstants.TOPICAL_CHIEFDEPT + " varchar(200),"
            + DBConstants.TOPICAL_UID + " varchar(50),"
            + DBConstants.TOPICAL_UNO + " integer,"
            + DBConstants.TOPICAL_CSMNO + " integer,"
            + DBConstants.TOPICAL_CANREAD + " integer,"
            + DBConstants.TOPICAL_PRICE + " varchar(10),"
            + DBConstants.TOPICAL_EPRICE + " varchar(10),"
            + DBConstants.TOPICAL_DRAFTDEPTKEY + " varchar(200),"
            + DBConstants.TOPICAL_CHIEFUNITKEY + " varchar(200),"
            + DBConstants.TOPICAL_DRAFTDEPT + " varchar(200),"
            + DBConstants.TOPICAL_CHIEFUNIT + " varchar(200),"
            + DBConstants.TOPICAL_BELONG + " varchar(200),"
            + DBConstants.TOPICAL_SUMMARY + " varchar(500),"
            + DBConstants.TOPICAL_ISPREFACE + " integer,"
            + DBConstants.TOPICAL_RELTYPE + " integer,"
            + DBConstants.TOPICAL_EDITTIME + " varchar(20),"
            + DBConstants.TOPICAL_STATUS + " integer,"
            + DBConstants.TOPICAL_LABELS + " varchar(1000))";


    /**
     * 创建术语表
     */
    String create_ONYM = "create table " + DBConstants.TB_ONYM + " ("
            + DBConstants._ID + " integer primary key autoincrement,"
            + DBConstants.ONYM_NO + " integer unique not null,"
            + DBConstants.ONYM_ONYM + " varchar(100) not null,"
            + DBConstants.ONYM_LEMENTRY + " varchar(200),"
            + DBConstants.ONYM_LEMENTRYEN + " varchar(200),"
            + DBConstants.ONYM_DESCRIPTION + " varchar(20000),"
            + DBConstants.ONYM_KIND + " integer,"
            + DBConstants.ONYM_STDNO + " integer not null,"
            + DBConstants.ONYM_STDID + " varchar(100),"
            + DBConstants.ONYM_STDCAPTION + " varchar(300))";


    /**
     * 创建阅读权限表
     */
    String create_AUTH = "create table " + DBConstants.TB_AUTH + " ("
            + DBConstants._ID + " integer primary key autoincrement,"
            + DBConstants.AUTH_UID + " varchar(200),"
            + DBConstants.AUTH_UNO + " integer,"
            + DBConstants.AUTH_STDNO + " integer,"
            + DBConstants.AUTH_STDTYPE + " integer,"
            + DBConstants.AUTH_STDSIZE + " integer,"
            + DBConstants.AUTH_EXPIRE + " integer,"

            + DBConstants.AUTH_CAPTION + " varchar(300),"
            + DBConstants.AUTH_STDID + " varchar(100),"
            + DBConstants.AUTH_FOREIGNCAPTION + " varchar(300),"
            + DBConstants.AUTH_REVISION + " integer,"
            + DBConstants.AUTH_RPLSTDNO + " integer,"
            + DBConstants.AUTH_RPLSTDID + " varchar(100),"
            + DBConstants.AUTH_WRITEDEPT + " varchar(100),"
            + DBConstants.AUTH_PUBLISHER + " varchar(100),"
            + DBConstants.AUTH_PUBLISHEDWORD + " varchar(50),"
            + DBConstants.AUTH_PUBLISHDATE + " varchar(50),"
            + DBConstants.AUTH_PERFORMDATE + " varchar(50),"
            + DBConstants.AUTH_EXPIREDDATE + " varchar(50),"
            + DBConstants.AUTH_FORMAT + " integer,"
            + DBConstants.AUTH_RELTYPE + " integer,"
            //数据库版本7新增
            + DBConstants.AUTH_EDITTIME + " varchar(20),"
            //12月12 数据库版本8新增
            + DBConstants.AUTH_CHIEFDEPT + " varchar(200),"
            + DBConstants.AUTH_CANREAD + " integer,"
            + DBConstants.AUTH_PRICE + " varchar(10),"
            + DBConstants.AUTH_EPRICE + " varchar(10),"
            + DBConstants.AUTH_DRAFTDEPTKEY + " varchar(200),"
            + DBConstants.AUTH_CHIEFUNITKEY + " varchar(200),"
            + DBConstants.AUTH_DRAFTDEPT + " varchar(200),"
            + DBConstants.AUTH_CHIEFUNIT + " varchar(200),"
            + DBConstants.AUTH_BELONG + " varchar(200),"
            + DBConstants.AUTH_SUMMARY + " varchar(500),"
            + DBConstants.AUTH_ISPREFACE + " integer,"
            + DBConstants.AUTH_STATUS + " integer,"
            + DBConstants.AUTH_LABELS + " varchar(1000),"
            //12月20 数据库版本9新增
            + DBConstants.AUTH_UNSAVE + " varchar(2000))";

    /**
     * 创建服务器
     */
    String create_SERVER = "create table " + DBConstants.TB_SERVER + " ("
            + DBConstants._ID + " integer primary key autoincrement,"
            + DBConstants.SERVER_NAME + " varchar(200) unique not null,"
            + DBConstants.SERVER_STATE + " integer,"
            + DBConstants.SERVER_URL + " varchar(200)  not null)";

}
