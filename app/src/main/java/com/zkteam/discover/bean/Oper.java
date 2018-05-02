package com.zkteam.discover.bean;

import com.zkteam.discover.util.TextUtil;

/**
 * ===========================================================
 * 作    者：大印（高印） Github地址：https://github.com/GaoYin2016
 * 邮    箱：18810474975@163.com
 * 版    本：
 * 创建日期：2018/4/24 上午11:16
 * 描    述：
 * 修订历史：
 * ===========================================================
 */
public class Oper {

    public static final String TYPE_WEBVIEW = "webview";

    public static final String TYPE_COUPON_IDS = "coupon_ids";
    public static final String TYPE_COUPON_SINGLE = "single_coupon";//2.5.6新增

    public static final String TYPE_TOPIC_IDS = "topic_ids";
    public static final String TYPE_TOPIC_SINGLE = "single_topic";//2.5.6新增
    public static final String TYPE_TOPIC_3PIC = "single_topic_3";//2.5.8新增 新用户专场
    public static final String TYPE_BANNER = "banner";              // 2.6.2 新增 Banner类型

    private int element_id;
    private String element_type = TextUtil.TEXT_EMPTY;
    private String title = TextUtil.TEXT_EMPTY;
    private String subtitle = TextUtil.TEXT_EMPTY;
    private String extend = TextUtil.TEXT_EMPTY;
    private String pic = TextUtil.TEXT_EMPTY;
    private int pic_width;
    private int pic_height;
    private ChildrenOpers children;

    public int getElement_id() {
        return element_id;
    }

    public void setElement_id(int element_id) {
        this.element_id = element_id;
    }

    public ChildrenOpers getChildren() {
        return children;
    }

    public void setChildren(ChildrenOpers children) {
        this.children = children;
    }

    public String getElement_type() {
        return element_type;
    }

    public void setElement_type(String element_type) {
        this.element_type = element_type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getPic_width() {
        return pic_width;
    }

    public void setPic_width(int pic_width) {
        this.pic_width = pic_width;
    }

    public int getPic_height() {
        return pic_height;
    }

    public void setPic_height(int pic_height) {
        this.pic_height = pic_height;
    }


    public boolean isTypeWebView() {

        return TYPE_WEBVIEW.equalsIgnoreCase(element_type);
    }

    public boolean isTypeBanner() {

        return TYPE_BANNER.equals(element_type);
    }

    @Override
    public String toString() {

        return "Oper{" +
                "element_type='" + element_type + '\'' +
                ", title='" + title + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", extend='" + extend + '\'' +
                ", pic='" + pic + '\'' +
                ", pic_width=" + pic_width +
                ", pic_height=" + pic_height +
                '}';
    }
}
