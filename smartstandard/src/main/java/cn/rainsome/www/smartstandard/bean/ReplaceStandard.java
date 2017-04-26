package cn.rainsome.www.smartstandard.bean;


/**
 * 标准替换信息
 * Created by Yomii on 2016/12/6.
 */

public class ReplaceStandard {
    public int no;
    public String stdid;
    public String caption;
    /**
     * 1. 本标准旧, (显示替换stdid)
     * 2. 本标准新, (显示被stdid替换)
     */
    public int reltype;
}
