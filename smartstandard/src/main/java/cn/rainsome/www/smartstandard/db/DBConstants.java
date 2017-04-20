package cn.rainsome.www.smartstandard.db;

/**
 * 数据库变量储存接口
 * Created by Yomii on 2016/3/19.
 */
public interface DBConstants {


    /**
     * 数据库名称
     */
    String DBNAME = "smartstandard";

    /**
     * 数据库版本
     */
    int DBVERSION = 9;

    /**
     * 主键
     */
    String _ID = "_id";

    /**
     * 批注数据表名
     */
    String TB_POSTIL = "postil";
    /**
     * 批注内容
     */
    String POSTIL_CONTENT = "content";
    /**
     * 批注引用文字
     */
    String POSTIL_QUOTE = "quote";
    /**
     * 批注编辑时间
     */
    String POSTIL_EDITMILLIS = "editmillis";
    /**
     * 批注可见范围(0:本人,1:所有人)
     */
    String POSTIL_AUTHORITY = "kind";
    /**
     * 批注挂载的结构化数据
     */
    String POSTIL_CLAUSENO = "clauseno";
    /**
     * 对应的标准结构化条目序号
     */
    String POSTIL_STDNO = "stdno";
    /**
     * 批注作者
     */
    String POSTIL_PSNUID = "editor";
    /**
     * 同步状态 0未同步,1已同步
     */
    String POSTIL_SYNC = "sync";

//////////////////////////////////////  8月22 db v5 批注库新增  ////////////////////////////////////
    /**
     * 批注序号(服务端)
     */
    String POSTIL_NO = "no";
    /**
     * 批注版本
     */
    String POSTIL_VERSION = "version";
    /**
     * 批注类型   1 批注  2 纠错  3 疑问  4 建议
     */
    String POSTIL_TYPE = "type";
    /**
     * 是否已反馈   1：已反馈    2未反馈  默认2
     */
    String POSTIL_ISREP = "isrep";
    /**
     * 批注者编号
     */
    String POSTIL_PSNNO = "psnno";
    /**
     * 批注对应的会话编号
     */
    String POSTIL_TILNO = "tilno";
    //////////////////////////////////////  8月22 db v5 批注库新增完毕  /////////////////////////////



    /**
     * 结构化数据表名
     */
    String TB_CLAUSE = "clause";

    /**
     * 结构化数据的ID号
     */
    String CLAUSE_NO = "no";
    /**
     * 父节点号,标准标题为0
     */
    String CLAUSE_PARENTNO = "parentno";
    /**
     * 节点排序号,初始序号为1,
     */
    String CLAUSE_SORTBY = "sortby";
    /**
     * 章节
     */
    String CLAUSE_CHAPTER = "chapter";
    /**
     * 内容
     */
    String CLAUSE_CAPTION = "caption";
    /**
     * 0:标题,1:内容,2:图片
     */
    String CLAUSE_GENRE = "genre";
    /**
     * 目录
     */
    String CLAUSE_ISCATALOG = "iscatalog";
    /**
     * 条文说明
     */
    String CLAUSE_EXPLAIN = "explain";
    /**
     * 强制性条文
     */
    String CLAUSE_MANDATORY = "mandatory";
    /**
     * 设计通病
     */
    String CLAUSE_FAILING = "failing";
    /**
     * 标准编号
     */
    String CLAUSE_STDID = "stdid";
    /**
     * 标准序号
     */
    String CLAUSE_STDNO = "stdno";



    /**
     * 搜索记录表名
     */
    String TB_SEARCHRECORD = "searchrecord";

    /**
     * 搜索记录的ID号
     */
    String SEARCHRECORD_NO = "no";

    /**
     * 搜索记录的与或
     */
    String SEARCHRECORD_ANDOR = "andor";

    /**
     * 搜索记录标准编号
     */
    String SEARCHRECORD_STDID = "stdid";
    /**
     * 搜索记录标准名称
     */
    String SEARCHRECORD_CAPTION = "caption";
    /**
     * 批准文号
     */
    String SEARCHRECORD_PZWH = "pzwh";
    /**
     * 全文检索
     */
    String SEARCHRECORD_SDCCAPTION = "sdccaption";
    /**
     * 所属目录
     */
    String SEARCHRECORD_NODNO = "nodno";
    /**
     * 标准状态
     */
    String SEARCHRECORD_STDSTATES = "stdstates";
    /**
     * 发布起始日期
     */
    String SEARCHRECORD_PUBSTART = "pubstart";

    /**
     * 发布结束日期
     */
    String SEARCHRECORD_PUBEND = "pubend";
    /**
     * 实施起始日期
     */
    String SEARCHRECORD_CARRYSTART = "carrystart";

    /**
     * 实施结束日期
     */
    String SEARCHRECORD_CARRYEND = "carryend";
    /**
     * 废止起始日期
     */
    String SEARCHRECORD_FZSTART = "fzstart";

    /**
     * 废止结束日期
     */
    String SEARCHRECORD_FZEND = "fzend";

    /**
     * 搜索日期
     */
    String SEARCHRECORD_TIME = "time";





    /**
     * 阅读搜索记录表名
     */
    String TB_SEARCH_READING = "search_reading";

    /**
     * 搜索记录的内容
     */
    String SEARCH_READING_CONTENT = "content";




    /**
     * 题录表名
     */
    String TB_TOPICAL = "topical";

    /**
     * 标准序号
     */
    String TOPICAL_STDNO = "no";
    /**
     * 标题
     */
    String TOPICAL_CAPTION = "caption";
    /**
     * 标准编号
     */
    String TOPICAL_STDID = "stdid";
    /**
     * 国外标准标题
     */
    String TOPICAL_FOREIGNCAPTION = "foreigncaption";
    /**
     * 修订版本
     */
    String TOPICAL_REVISION = "revision";
    /**
     * 替代标准序号
     */
    String TOPICAL_RPLSTDNO = "rplstdno";
    /**
     * 替代标准编号
     */
    String TOPICAL_RPLSTDID = "rplstdid";
    /**
     * 主编部门 （废弃）
     */
    String TOPICAL_WRITEDEPT = "writedept";
    /**
     * 出版社
     */
    String TOPICAL_PUBLISHER = "publisher";
    /**
     * 批准文号
     */
    String TOPICAL_PUBLISHEDWORD = "publishedword";
    /**
     * 出版日期
     */
    String TOPICAL_PUBLISHDATE = "publishdate";
    /**
     * 实施日期
     */
    String TOPICAL_PERFORMDATE = "performdate";
    /**
     * 作废日期
     */
    String TOPICAL_EXPIREDDATE = "expireddate";
    /**
     * 数据形式  1 结构化数据  2 图片  3 结构化数据+图片  9 都不存在
     */
    String TOPICAL_FORMAT = "format";

    //2016_12_12更新字段

    /**
     * 主编部门
     */
    String TOPICAL_CHIEFDEPT = "chiefdept";

    /**
     * 用户登录名
     */
    String TOPICAL_UID = "user_id";

    /**
     * 用户编号
     */
    String TOPICAL_UNO = "user_no";

    /**
     * 用户对应企业编号
     */
    String TOPICAL_CSMNO = "csm_no";

    /**
     * 是否能阅读  0 不能  1 能
     */
    String TOPICAL_CANREAD = "canread";

    /**
     * 纸质版价格
     */
    String TOPICAL_PRICE = "price";

    /**
     * 电子版价格
     */
    String TOPICAL_EPRICE = "eprice";

    /**
     * 参编者名称 参编单位/参与起草单位
     */
    String TOPICAL_DRAFTDEPTKEY = "draftdeptkey";

    /**
     * 主编者名称 主编单位/主要起草单位
     */
    String TOPICAL_CHIEFUNITKEY = "chiefunitkey";

    /**
     * 参编者内容
     */
    String TOPICAL_DRAFTDEPT = "draftdept";

    /**
     * 主编者内容
     */
    String TOPICAL_CHIEFUNIT = "chiefunit";

    /**
     * 归口单位
     */
    String TOPICAL_BELONG = "belong";

    /**
     * 简介
     */
    String TOPICAL_SUMMARY = "summary";

    /**
     * 是否有前言  1 有   2 没有
     */
    String TOPICAL_ISPREFACE = "ispreface";


    /**
     * 替换信息  1. 本标准旧, (显示替换stdid)   2. 本标准新, (显示被stdid替换)
     */
    String TOPICAL_RELTYPE = "reltype";

    /**
     * 编辑时间(毫秒值)
     */
    String TOPICAL_EDITTIME = "edittime";
    /**
     * 标签
     */
    String TOPICAL_LABELS = "labels";

    /**
     *标准收录状态: 1 未收录 2信息录入 3制作中 4草稿 5发布
     *
     *  >= 5 表示已审 else 未审
     */
    String TOPICAL_STATUS = "status";





    /**
     * 根据个人账号或是企业账号查询  (1: 个人  2: 企业)
     */
    String TOPICAL_QUERYBY = "queryby";



    /**
     * 术语表名
     */
    String TB_ONYM = "onym";

    /**
     * 标准序号
     */
    String ONYM_NO = "no";

    /**
     * 关键词
     */
    String ONYM_ONYM = "onym";

    /**
     * 词条内容(中文)
     */
    String ONYM_LEMENTRY = "lementry";

    /**
     * 词条内容(英文)
     */
    String ONYM_LEMENTRYEN = "lementryen";

    /**
     * 词条描述(正文内容)
     */
    String ONYM_DESCRIPTION = "lemdescription";

    /**
     * 文档种类
     */
    String ONYM_KIND = "kind";

    /**
     * 所属标准序号
     */
    String ONYM_STDNO = "stdno";

    /**
     * 标准编号
     */
    String ONYM_STDID = "stdid";

    /**
     * 标准标题
     */
    String ONYM_STDCAPTION = "stdcaption";


    /**
     * 术语表名
     */
    String TB_AUTH = "reading_authority";

    /**
     * 用户登录名
     */
    String AUTH_UID = "user_id";

    /**
     * 用户编号
     */
    String AUTH_UNO = "user_no";

    /**
     * 标准编号
     */
    String AUTH_STDNO = "std_no";

    /**
     * 标准储存类型  1. 结构化数据   2. 图片
     */
    String AUTH_STDTYPE = "std_type";

    /**
     * 数据占用容量(字节)
     */
    String AUTH_STDSIZE = "std_size";

    /**
     * 有效期(本地缓存的阅读权限有效期 目前未启用)
     */
    String AUTH_EXPIRE = "expire";



    /**
     * 标题
     */
    String AUTH_CAPTION = "caption";
    /**
     * 标准编号
     */
    String AUTH_STDID = "stdid";
    /**
     * 国外标准标题
     */
    String AUTH_FOREIGNCAPTION = "foreigncaption";
    /**
     * 修订版本
     */
    String AUTH_REVISION = "revision";
    /**
     * 替代标准序号
     */
    String AUTH_RPLSTDNO = "rplstdno";
    /**
     * 替代标准编号
     */
    String AUTH_RPLSTDID = "rplstdid";
    /**
     * 主编部门（废弃字段）
     */
    String AUTH_WRITEDEPT = "writedept";
    /**
     * 出版社
     */
    String AUTH_PUBLISHER = "publisher";
    /**
     * 出版编号
     */
    String AUTH_PUBLISHEDWORD = "publishedword";
    /**
     * 出版日期
     */
    String AUTH_PUBLISHDATE = "publishdate";
    /**
     * 实施日期
     */
    String AUTH_PERFORMDATE = "performdate";
    /**
     * 作废日期
     */
    String AUTH_EXPIREDDATE = "expireddate";
    /**
     * 数据形式  1 结构化数据  2 图片  3 结构化数据+图片  9 都不存在
     */
    String AUTH_FORMAT = "format";

    /**
     * 替换信息  1. 本标准旧, (显示替换stdid)   2. 本标准新, (显示被stdid替换)
     */
    String AUTH_RELTYPE = "reltype";

    /**
     * 编辑时间(毫秒值)
     */
    String AUTH_EDITTIME = "edittime";

    /**
     * 未缓存的图片链接集合, 用;分割 (结构化数据中的所有图片缓存到本地) 12/20 新增
     *
     * "" 表示完整, 包含内容表示不完整
     */
    String AUTH_UNSAVE = "unsave";


    //2016_12_12更新字段

    /**
     * 主编部门
     */
    String AUTH_CHIEFDEPT = "chiefdept";
    /**
     * 是否能阅读  0 不能  1 能
     */
    String AUTH_CANREAD = "canread";

    /**
     * 纸质版价格
     */
    String AUTH_PRICE = "price";

    /**
     * 电子版价格
     */
    String AUTH_EPRICE = "eprice";

    /**
     * 参编者名称 参编单位/参与起草单位
     */
    String AUTH_DRAFTDEPTKEY = "draftdeptkey";

    /**
     * 主编者名称 主编单位/主要起草单位
     */
    String AUTH_CHIEFUNITKEY = "chiefunitkey";

    /**
     * 参编者内容
     */
    String AUTH_DRAFTDEPT = "draftdept";

    /**
     * 主编者内容
     */
    String AUTH_CHIEFUNIT = "chiefunit";

    /**
     * 归口单位
     */
    String AUTH_BELONG = "belong";

    /**
     * 简介
     */
    String AUTH_SUMMARY = "summary";

    /**
     * 是否有前言  1 有   2 没有
     */
    String AUTH_ISPREFACE = "ispreface";
    /**
     * 标签
     */
    String AUTH_LABELS = "labels";

    /**
     *标准收录状态: 1 未收录 2信息录入 3制作中 4草稿 5发布
     *
     *  >= 5 表示已审 else 未审
     */
    String AUTH_STATUS = "status";


    /**
     * 题录表名
     */
    String TB_SERVER = "server";
    /**
     * 标准序号
     */
    String SERVER_NAME = "name";
    /**
     * 标题
     */
    String SERVER_URL = "url";
    /**
     * 状态 1是选中，2未选中
     */
    String SERVER_STATE = "state";

}
