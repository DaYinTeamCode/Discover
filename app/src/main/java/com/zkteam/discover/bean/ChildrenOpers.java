package com.zkteam.discover.bean;

import java.io.Serializable;
import java.util.List;

/**
 * ===========================================================
 * 作    者：大印（高印） Github地址：https://github.com/GaoYin2016
 * 邮    箱：18810474975@163.com
 * 版    本：
 * 创建日期：2018/3/1 上午10:00
 * 描    述：
 * 修订历史：
 * ===========================================================
 */
public class ChildrenOpers implements Serializable {

    private List<DiscoverOper> banner;  //Banner

    private List<DiscoverOper> webview; // WebView

    public List<DiscoverOper> getBanner() {

        return banner;
    }

    public void setBanner(List<DiscoverOper> banner) {

        this.banner = banner;
    }

    public List<DiscoverOper> getWebview() {

        return webview;
    }

    public void setWebview(List<DiscoverOper> webview) {

        this.webview = webview;
    }

    @Override
    public String toString() {
        return "ChildrenOpers{" +
                "banner=" + banner +
                ", webview=" + webview +
                '}';
    }
}
