package cn.rainsome.www.smartstandard.bean;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

/**
 * Created by Yomii on 2016/3/9.
 */
public class ViewPagerBean {
    private boolean hasTitle;
    private String picUrl;
    private String pageUrl;
    private Drawable picDrawable;
    private Bitmap picBitmap;
    private int picResID;
    private String title;

    public ViewPagerBean(Drawable picDrawable, String pageUrl,
                         boolean hasTitle, String title) {
        this(pageUrl, hasTitle, title);
        this.picDrawable = picDrawable;
    }

    public ViewPagerBean(Bitmap picBitmap, String pageUrl, boolean hasTitle,
                         String title) {
        this(pageUrl, hasTitle, title);
        this.picBitmap = picBitmap;
    }

    public ViewPagerBean(int picResID, String pageUrl, boolean hasTitle,
                         String title) {
        this(pageUrl, hasTitle, title);
        this.picResID = picResID;
    }

    public ViewPagerBean(String picUrl, String pageUrl, boolean hasTitle,
                         String title) {
        this(pageUrl, hasTitle, title);
        this.picUrl = picUrl;
    }

    private ViewPagerBean(String pageUrl, boolean hasTitle, String title) {
        super();
        this.hasTitle = hasTitle;
        this.pageUrl = pageUrl;
        this.title = title;
    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
    }

    public boolean isHasTitle() {
        return hasTitle;
    }

    public void setHasTitle(boolean hasTitle) {
        this.hasTitle = hasTitle;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Drawable getPicDrawable() {
        return picDrawable;
    }

    public void setPicDrawable(Drawable picDrawable) {
        this.picDrawable = picDrawable;
    }

    public Bitmap getPicBitmap() {
        return picBitmap;
    }

    public void setPicBitmap(Bitmap picBitmap) {
        this.picBitmap = picBitmap;
    }

    public int getPicResID() {
        return picResID;
    }

    public void setPicResID(int picResID) {
        this.picResID = picResID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
