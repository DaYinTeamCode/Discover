package com.zkteam.discover.vh;

import android.view.View;
import android.view.ViewGroup;

import com.zkteam.discover.R;
import com.zkteam.discover.base.ExRvItemViewHolderBase;
import com.zkteam.discover.bean.Oper;
import com.zkteam.discover.fresco.FrescoImageView;
import com.zkteam.discover.util.DensityUtil;
import com.zkteam.discover.util.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ===========================================================
 * 作    者：大印（高印） Github地址：https://github.com/GaoYin2016
 * 邮    箱：18810474975@163.com
 * 版    本：
 * 创建日期：2018/3/1 下午3:06
 * 描    述： 第二级banner图片运营位
 * 修订历史：
 * ===========================================================
 */
public class DiscoverIndexLevel2BannerViewHolder extends ExRvItemViewHolderBase  {

    @BindView(R.id.fivCover)
    FrescoImageView mAivCover;

    private int mBannerWidth;

    public DiscoverIndexLevel2BannerViewHolder(ViewGroup viewGroup, int bannerWidth) {

        super(viewGroup, R.layout.page_discover_index_level2_vh_banner);
        ButterKnife.bind(this, itemView);
        mBannerWidth = bannerWidth;
    }

    @Override
    protected void initConvertView(View convertView) {

        convertView.setOnClickListener(this);
    }

    public void invalidateView(Oper oper) {

        if (oper == null) {

            mAivCover.setImageUri((String) null);
        } else {

            ViewUtil.scaleLayoutParams(mAivCover, oper.getPic_width(), oper.getPic_height(), mBannerWidth, DensityUtil.dip2px(80f));
            mAivCover.setImageUriByLp(oper.getPic());
        }

    }
}
