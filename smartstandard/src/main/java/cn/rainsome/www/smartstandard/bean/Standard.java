package cn.rainsome.www.smartstandard.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.rainsome.www.smartstandard.bean.response.ResponseBean;


/**
 * 标准的封装
 * Created by Yomii on 2016/4/12.
 */
public class Standard extends ResponseBean {


    @SerializedName("no")
    public int no;
    @SerializedName("stdid")
    public String stdid;
    @SerializedName("state")
    public int state;
    @SerializedName("caption")
    public String caption;
    @SerializedName("status")
    public int status;
    @SerializedName("foreigncaption")
    public String foreigncaption;
    @SerializedName("revision")
    public String revision;
    @SerializedName("rplstdno")
    public int rplstdno;
    @SerializedName("rplstdid")
    public String rplstdid;
    @SerializedName("writedept")
    public String writedept;
    @SerializedName("publisher")
    public String publisher;
    @SerializedName("publishdate")
    public String publishdate;
    @SerializedName("performdate")
    public String performdate;
    @SerializedName("expireddate")
    public String expireddate;
    @SerializedName("publishedword")
    public String publishedword;
    @SerializedName("price")
    public float price;
    @SerializedName("eprice")
    public float eprice;
    @SerializedName("hascontent")
    public int hascontent;
    @SerializedName("format")
    public int format;
    @SerializedName("bokstate")
    public int bokstate;
    @SerializedName("pdfcopyright")
    public int pdfcopyright;
    @SerializedName("stdkind")
    public int stdkind;
    @SerializedName("canread")
    public int canread;
    @SerializedName("canbuy")
    public int canbuy;
    @SerializedName("rpls")
    public List<ReplaceStandard> rpls;
    @SerializedName("labels")
    public List<Label> labels;

    //    /**
//     * 用户名
//     */
//    public String uid;
//    /**
//     * 用户no
//     */
//    public int uno;
//    /**
//     * 企业no
//     */
//    public int csmno;
//    /**
//     * 标准序号
//     */
//    public int no;
//    /**
//     * 标题
//     */
//    public String caption;
//    /**
//     * 标准编号
//     */
//    public String stdid;
//    /**
//     * 国外标准标题
//     */
//    public String foreigncaption;
//    /**
//     * 修订版本
//     */
//    public int revision;
//    /**
//     * 替代标准序号
//     */
//    public int rplstdno;
//    /**
//     * 替代标准编号
//     */
//    public String rplstdid;
//
//    /**
//     * 主编部门
//     */
//    public String chiefdept;
//    /**
//     * 出版社
//     */
//    public String publisher;
//    /**
//     * 出版日期
//     */
//    public String publishdate;
//    /**
//     * 批准文号
//     */
//    public String publishedword;
//    /**
//     * 作废日期
//     */
//    public String expireddate;
//    /**
//     * 实施日期
//     */
//    public String performdate;
//
//    /**
//     * 是否有内容  1: 结构化数据 2 图片 3 有结构化数据和图片 9 都没有
//     */
//    public int format;
//
//    /**
//     * 是否能阅读  0 不能  1 能
//     */
//    public int canread;
//
//    /**
//     * 纸质版价格
//     */
//    public float price;
//
//    /**
//     * 电子版价格
//     */
//    public float eprice;
//
//    /**
//     * 积分价格
//     */
//    public int integral;
//
//    /**
//     * 参编者名称 参编单位/参与起草单位
//     */
//    public String draftdeptkey;
//
//    /**
//     * 主编者名称 主编单位/主要起草单位
//     */
//    public String chiefunitkey;
//
//    /**
//     * 参编者内容
//     */
//    public String draftdept;
//
//    /**
//     * 主编者内容
//     */
//    public String chiefunit;
//
//    /**
//     * 归口单位
//     */
//    public String belong;
//
//    /**
//     * 简介
//     */
//    public String summary;
//
//    /**
//     * 是否有前言  1 有   2 没有
//     */
//    public int ispreface;
//
//    /**
//     * 标准收录状态: 1 未收录 2信息录入 3制作中 4草稿 5发布
//     * <p>
//     * >= 5 表示已审 else 未审
//     */
//    public int status;
//
//
//    public List<ReplaceStandard> rpls;
//
//    /**
//     * no : 15
//     * label : 12
//     */
//    public List<Label> labels;
//
//    /**
//     * 编辑时间(毫秒值)
//     */
//    public String editTime;
//
//
//    public Standard(int no, String stdid, String caption) {
//        this.no = no;
//        this.stdid = stdid;
//        this.caption = caption;
//    }
//
//    public Standard() {
//    }
//
//    /*--------------------------------元属性-----------------------------------*/
//
//    /**
//     * 替换类型
//     */
//    public int reltype;
//
//
//    /*--------------------------------废弃属性-----------------------------------*/
//
//    /**
//     * 主编部门(废弃)
//     */
//    public String writedept;
//    /**
//     * 是否有内容 (废弃)
//     */
//    public int hascontent;
//    /**
//     * 是否能买  (废弃)
//     */
//    public int canbuy;
//    /**
//     * 状态 (废弃)
//     */
//    public int state;




}
