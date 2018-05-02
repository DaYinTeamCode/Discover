package com.zkteam.discover.decoration;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zkteam.discover.base.ExRvItemViewHolderBase;
import com.zkteam.discover.util.DensityUtil;
import com.zkteam.discover.vh.DiscoverIndexLevel2BannerViewHolder;
import com.zkteam.discover.vh.DiscoverIndexLevel2MiniViewHolder;
import com.zkteam.discover.vh.DiscoverIndexLevel2TitleViewHolder;

/**
 * ===========================================================
 * 作    者：大印（高印） Github地址：https://github.com/GaoYin2016
 * 邮    箱：18810474975@163.com
 * 版    本：
 * 创建日期：2018/2/28 下午11:32
 * 描    述：
 * 修订历史：
 * ===========================================================
 */
public class DiscoverIndexLevel2Decoration extends ExRvDecoration {

    @Override
    protected void getExRvItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        ExRvItemViewHolderBase viewHolder = (ExRvItemViewHolderBase) parent.getChildViewHolder(view);
        if (viewHolder instanceof DiscoverIndexLevel2TitleViewHolder) {

            outRect.top = DensityUtil.dip2px(20f);
            outRect.left = DensityUtil.dip2px(14f);
            outRect.right = outRect.left;

        } else if (viewHolder instanceof DiscoverIndexLevel2MiniViewHolder) {

            GridLayoutManager.LayoutParams sglm = (GridLayoutManager.LayoutParams) viewHolder.getConvertView().getLayoutParams();
            int spanIndex = sglm.getSpanIndex();

            if (spanIndex == 0) {

                outRect.left = DensityUtil.dip2px(25);
            } else if (spanIndex == 2) {

                outRect.right = DensityUtil.dip2px(25);
            }

            outRect.top = DensityUtil.dip2px(15f);

        } else if (viewHolder instanceof DiscoverIndexLevel2BannerViewHolder) {

            outRect.top = DensityUtil.dip2px(19f);
            outRect.left = DensityUtil.dip2px(10f);
            outRect.right = outRect.left;
        }
    }
}
