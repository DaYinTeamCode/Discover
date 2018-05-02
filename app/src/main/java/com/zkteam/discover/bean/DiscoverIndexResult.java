package com.zkteam.discover.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * ===========================================================
 * 作    者：大印（高印） Github地址：https://github.com/GaoYin2016
 * 邮    箱：18810474975@163.com
 * 版    本：
 * 创建日期：2018/2/28 下午5:40
 * 描    述：找券 实体类
 * 修订历史：
 * ===========================================================
 */
public class DiscoverIndexResult {

    private List<DiscoverOper> discoverList;
    private List<DiscoverOper> localLevel2List;

    public List<DiscoverOper> getLocalLevel2List() {

        return localLevel2List;
    }

    public void setLocalLevel2List(List<DiscoverOper> localLevel2List) {

        this.localLevel2List = localLevel2List;
    }

    public List<DiscoverOper> getDiscoverList() {

        return discoverList;
    }

    @JSONField(name = "zhekou_discover_left_nav")
    public void setDiscoverList(List<DiscoverOper> discoverList) {

        this.discoverList = discoverList;
    }

    @Override
    public String toString() {
        return "DiscoverIndexResult{" +
                "discoverList=" + discoverList +
                '}';
    }
}
