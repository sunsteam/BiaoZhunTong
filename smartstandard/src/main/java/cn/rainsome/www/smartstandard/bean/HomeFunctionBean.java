package cn.rainsome.www.smartstandard.bean;


import cn.yomii.www.frame.bean.ModelEntity;

/**
 * Created by Yomii on 2016/6/14.
 */
public class HomeFunctionBean extends ModelEntity {

    public int titleResId;

    public int descriptionResId;

    public int IconResId;

    public HomeFunctionBean(int titleResId, int descriptionResId, int iconResId) {
        this.titleResId = titleResId;
        this.descriptionResId = descriptionResId;
        IconResId = iconResId;
    }
}
