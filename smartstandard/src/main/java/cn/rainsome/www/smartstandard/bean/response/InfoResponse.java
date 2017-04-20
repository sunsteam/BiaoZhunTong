package cn.rainsome.www.smartstandard.bean.response;


import cn.yomii.www.frame.bean.response.ResponseBean;

/**
 * 服务器INFO接口
 * Created by Yomii on 2016/9/1.
 */
public class InfoResponse extends ResponseBean {

    /**
     * app : RSI
     * description : Rainsome weboos interface.
     * host : http://test.rainsome.cn:30303/rsi/
     * update : 0
     * ver : 15
     */

    public String app;
    public String description;
    public String host;
    public int update;
    public int ver;


}