package cn.rainsome.www.smartstandard.function.main;


import cn.rainsome.www.smartstandard.R;
import cn.rainsome.www.smartstandard.function.cart.CartFrag;
import cn.rainsome.www.smartstandard.function.home.HomeFrag;
import cn.rainsome.www.smartstandard.function.response.ResponseFrag;
import cn.rainsome.www.smartstandard.function.user.UserFrag;

public enum TabEnum {


    home(0, R.string.main_tab_name_home, R.drawable.tab_icon_home, HomeFrag.class),
    cart(1, R.string.main_tab_name_cart, R.drawable.tab_icon_cart, CartFrag.class),
    response(2, R.string.main_tab_name_response, R.drawable.tab_icon_search, ResponseFrag.class),
    me(3, R.string.main_tab_name_me, R.drawable.tab_icon_me, UserFrag.class);


    private int index;
    private int title;
    private int icon;
    private Class<?> clazz;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    TabEnum(int index, int title, int icon, Class<?> clazz) {
        this.index = index;
        this.title = title;
        this.icon = icon;
        this.clazz = clazz;
    }


}
