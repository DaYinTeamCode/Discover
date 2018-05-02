package com.zkteam.discover.adapter;

import android.view.ViewGroup;

import com.zkteam.discover.base.ExRvAdapterBase;
import com.zkteam.discover.base.ExRvItemViewHolderBase;
import com.zkteam.discover.bean.DiscoverOper;
import com.zkteam.discover.bean.Oper;
import com.zkteam.discover.util.CollectionUtil;
import com.zkteam.discover.util.DensityUtil;
import com.zkteam.discover.util.DimenConstant;
import com.zkteam.discover.vh.DiscoverIndexLevel2BannerViewHolder;
import com.zkteam.discover.vh.DiscoverIndexLevel2MiniViewHolder;
import com.zkteam.discover.vh.DiscoverIndexLevel2TitleViewHolder;
import com.zkteam.discover.vh.ExRvItemViewHolderEmpty;

/**
 * ===========================================================
 * 作    者：大印（高印） Github地址：https://github.com/GaoYin2016
 * 邮    箱：18810474975@163.com
 * 版    本：
 * 创建日期：2018/4/24 上午10:58
 * 描    述：
 * 修订历史：
 * ===========================================================
 */
public class DiscoverLevel2Adapter extends ExRvAdapterBase {


    public static final int TYPE_ITEM_TITLE = 0;     // Title 运营位
    public static final int TYPE_ITEM_WEBVIEW = 1;  // webView 运营位
    public static final int TYPE_ITEM_BANNER = 2;   // Banner 运营位
    private static final int TYPE_ITEM_NONE = 3;           // NONE

    /**
     * 查询指定elementId的运营位元素pos
     *
     * @param elementId
     * @return 未找到返回-1
     */
    public int getSelectPosition(int elementId) {

        for (int i = 0; i < CollectionUtil.size(getData()); i++) {

            DiscoverOper discoverOper = (DiscoverOper) CollectionUtil.getItem(getData(), i);
            if (discoverOper.getElement_id() == elementId)
                return i;
        }
        return -1;
    }

    @Override
    public int getDataItemViewType(int dataPos) {

        Object obj = getDataItem(dataPos);
        if (obj instanceof DiscoverOper) {

            DiscoverOper oper = (DiscoverOper) obj;

            if (oper.isTypeWebView())
                return TYPE_ITEM_WEBVIEW;
            else if (oper.isTypeBanner())
                return TYPE_ITEM_BANNER;
            else if (oper.isTypeTitle())
                return TYPE_ITEM_TITLE;
        }

        return TYPE_ITEM_NONE;
    }

    @Override
    public ExRvItemViewHolderBase onCreateDataViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TYPE_ITEM_TITLE:
                return new DiscoverIndexLevel2TitleViewHolder(parent);
            case TYPE_ITEM_WEBVIEW:
                return new DiscoverIndexLevel2MiniViewHolder(parent);
            case TYPE_ITEM_BANNER:
                int bannerWidth = (int) (DimenConstant.SCREEN_WIDTH * (0.75f)) - DensityUtil.dip2px(20f);
                return new DiscoverIndexLevel2BannerViewHolder(parent, bannerWidth);
            default:
            case TYPE_ITEM_NONE:
                return ExRvItemViewHolderEmpty.newVertInstance(parent);
        }
    }

    @Override
    public void onBindDataViewHolder(ExRvItemViewHolderBase holder, int dataPos) {

        if (holder instanceof DiscoverIndexLevel2TitleViewHolder)
            ((DiscoverIndexLevel2TitleViewHolder) holder).invalidateView((DiscoverOper) getDataItem(dataPos));
        else if (holder instanceof DiscoverIndexLevel2MiniViewHolder)
            ((DiscoverIndexLevel2MiniViewHolder) holder).invalidateView((Oper) getDataItem(dataPos));
        else if (holder instanceof DiscoverIndexLevel2BannerViewHolder)
            ((DiscoverIndexLevel2BannerViewHolder) holder).invalidateView((Oper) getDataItem(dataPos));
    }
}
