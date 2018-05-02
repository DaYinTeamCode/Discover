package com.zkteam.discover.util;


import com.zkteam.discover.bean.ChildrenOpers;
import com.zkteam.discover.bean.DiscoverOper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ===========================================================
 * 作    者：大印（高印） Github地址：https://github.com/GaoYin2016
 * 邮    箱：18810474975@163.com
 * 版    本：
 * 创建日期：2018/3/1 下午2:53
 * 描    述：
 * 修订历史：
 * ===========================================================
 */
public class DiscoverIndexUtil {

    /**
     * 过滤 WebView列表和Banner列表为null 情况
     *
     * @param opers
     */
    public static void filterNullOperList(List<DiscoverOper> opers) {

        if (CollectionUtil.isEmpty(opers))
            return;

        Iterator<DiscoverOper> iter = opers.iterator();
        while (iter.hasNext()) {

            if (isFilterNullOper(iter.next()))
                iter.remove();
        }
    }

    /**
     * 过滤为NULL 运营位
     *
     * @param discoverOper
     * @return
     */
    private static boolean isFilterNullOper(DiscoverOper discoverOper) {

        if (discoverOper == null)
            return true;

        ChildrenOpers childrenOpers = discoverOper.getChildren();
        if (childrenOpers == null)
            return true;

        return CollectionUtil.isEmpty(childrenOpers.getWebview()) && (CollectionUtil.isEmpty(childrenOpers.getBanner()));
    }

    /**
     * 处理 右侧运营位 合并数据
     *
     * @param opers
     */
    public static List<DiscoverOper> merageOperLevel2List(List<DiscoverOper> opers) {

        if (opers == null)
            return null;

        List<DiscoverOper> merageOpers = new ArrayList<DiscoverOper>();
        for (int i = 0; i < CollectionUtil.size(opers); i++) {

            DiscoverOper oper = CollectionUtil.getItem(opers, i);
            if (oper == null)
                continue;

            ChildrenOpers childrenOpers = oper.getChildren();
            if (childrenOpers == null)
                continue;

            oper.setElement_type(DiscoverOper.TYPE_TITLE);
            oper.setParentTitle(oper.getTitle());
            oper.setParentPosition(i);
            oper.setChildPosition(i);
            oper.setParentId(oper.getElement_id());
            merageOpers.add(oper);

            List<DiscoverOper> operWebViews = childrenOpers.getWebview();     // WebView
            // 模拟数据
            operWebViews.addAll(childrenOpers.getWebview());
            operWebViews.addAll(childrenOpers.getWebview());
            for (int j = 0; j < CollectionUtil.size(operWebViews); j++) {

                DiscoverOper webViewOper = operWebViews.get(j);
                webViewOper.setParentTitle(oper.getTitle());
                webViewOper.setParentPosition(i);
                webViewOper.setChildPosition(j);
                webViewOper.setParentId(oper.getElement_id());
                merageOpers.add(webViewOper);
            }

            List<DiscoverOper> operBanners = childrenOpers.getBanner();      // Banner
            // 模拟数据
            operBanners.addAll(childrenOpers.getBanner());
            operBanners.addAll(childrenOpers.getBanner());
            for (int k = 0; k < CollectionUtil.size(operBanners); k++) {

                DiscoverOper bannerOper = operBanners.get(k);
                bannerOper.setParentTitle(oper.getTitle());
                bannerOper.setParentPosition(i);
                bannerOper.setChildPosition(k);
                bannerOper.setParentId(oper.getElement_id());
                merageOpers.add(bannerOper);
            }
        }
        return merageOpers;
    }
}
