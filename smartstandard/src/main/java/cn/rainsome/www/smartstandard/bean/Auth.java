package cn.rainsome.www.smartstandard.bean;


import java.util.List;

import cn.rainsome.www.smartstandard.Info;
import cn.rainsome.www.smartstandard.bean.response.ResponseBean;


/**
 * 带权限的标准信息
 * Created by Yomii on 2016/8/24.
 */
public class Auth extends ResponseBean {


    /**
     * 所属账户ID
     */
    public String uerId;

    /**
     * 所属账户编号
     */
    public int uerNo;

    public int stdNo;

    /**
     * 0: 不存在  1: 结构化数据  2: 图片   -2 : 等待更新状态
     */
    public int stdType;

    /**
     * 数据占用容量(byte)
     */
    public long stdSize;

    /**
     * 缓存有效期(未启用)
     */
    public long expire;

    /**
     * 标题
     */
    public String caption;
    /**
     * 标准编号
     */
    public String stdid;
    /**
     * 国外标准标题
     */
    public String foreigncaption;
    /**
     * 修订版本
     */
    public String revision;
    /**
     * 替代标准序号
     */
    public int rplstdno;
    /**
     * 替代标准编号
     */
    public String rplstdid;
    /**
     * 主编部门(废弃)
     */
    public String writedept;

    /**
     * 主编部门
     */
    public String chiefdept;
    /**
     * 出版社
     */
    public String publisher;
    /**
     * 出版日期
     */
    public String publishdate;
    /**
     * 出版编号
     */
    public String publishedword;
    /**
     * 作废日期
     */
    public String expireddate;
    /**
     * 实施日期
     */
    public String performdate;

    public int format;

    /**
     * 是否能阅读  0 不能  1 能
     */
    public int canread;

    public List<RplsEntity> rpls;

    /**
     * 替换类型: 取标准信息的替换列表第一项, 参数信息见下方子类
     */
    public int reltype;

    /**
     * 编辑时间(毫秒值)
     */
    public String editTime;

    /**
     * 纸质版价格
     */
    public float price;

    /**
     * 电子版价格
     */
    public float eprice;

    /**
     * 参编者名称 参编单位/参与起草单位
     */
    public String draftdeptkey;

    /**
     * 主编者名称 主编单位/主要起草单位
     */
    public String chiefunitkey;

    /**
     * 参编者内容
     */
    public String draftdept;

    /**
     * 主编者内容
     */
    public String chiefunit;

    /**
     * 归口单位
     */
    public String belong;

    /**
     * 简介
     */
    public String summary;

    /**
     * 是否有前言  1 有   2 没有
     */
    public int ispreface;

    /**
     * 标准收录状态: 1 未收录 2信息录入 3制作中 4草稿 5发布
     * <p>
     * >= 5 表示已审 else 未审
     */
    public int status;


    public static class RplsEntity {
        public int no;
        public String stdid;
        public String caption;
        /**
         * 1. 本标准旧, (显示替换stdid)
         * 2. 本标准新, (显示被stdid替换)
         */
        public int reltype;
    }

    public Auth(int stdNo, int stdType, long stdSize, String editTime) {
        this.uerId = Info.getPsnUid();
        this.uerNo = Info.getPsnNo();
        this.stdNo = stdNo;
        this.stdType = stdType;
        this.stdSize = stdSize;
        this.editTime = editTime;
        this.unSave = "";
    }

    public Auth(int stdNo, int stdType, long stdSize, String editTime, String unSave) {
        this.uerId = Info.getPsnUid();
        this.uerNo = Info.getPsnNo();
        this.stdNo = stdNo;
        this.stdType = stdType;
        this.stdSize = stdSize;
        this.editTime = editTime;
        this.unSave = unSave;
    }

    public Auth() {
    }


    //----------------------------------元数据---------------------------------------//

    /**
     * 未缓存的图片链接集合, 用;分割 (结构化数据中的所有图片缓存到本地) 12/20 新增
     * <p>
     * "" 表示完整, 包含内容表示不完整
     */
    public String unSave;
}
