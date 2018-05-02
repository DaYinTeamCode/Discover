package com.zkteam.discover.vh;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zkteam.discover.R;
import com.zkteam.discover.base.ExRvItemViewHolderBase;
import com.zkteam.discover.bean.Oper;
import com.zkteam.discover.util.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ===========================================================
 * 作    者：大印（高印） Github地址：https://github.com/GaoYin2016
 * 邮    箱：18810474975@163.com
 * 版    本：
 * 创建日期：2018/2/28 下午2:33
 * 描    述： 一级栏目ViewHolder
 * 修订历史：
 * ===========================================================
 */
public class DiscoverIndexLevel1ViewHolder extends ExRvItemViewHolderBase {

    @BindView(R.id.rlRoot)
    RelativeLayout mRlRoot;

    @BindView(R.id.tvName)
    TextView mTvName;

    @BindView(R.id.ivTip)
    ImageView mIvTip;

    public DiscoverIndexLevel1ViewHolder(ViewGroup viewGroup) {

        super(viewGroup, R.layout.page_discover_index_level1_vh);
        ButterKnife.bind(this, itemView);
    }


    @Override
    protected void initConvertView(View convertView) {

        convertView.setOnClickListener(this);
    }

    public void invalidateView(Oper oper, boolean isSelected) {

        mTvName.setText(oper == null ? TextUtil.TEXT_EMPTY : oper.getTitle());
        if (isSelected)
            setSelectedStyle();
        else
            setNormalStyle();
    }

    public void setSelectedStyle() {

//        mIvTip.setVisibility(View.VISIBLE);
        mTvName.setTextColor(0XFFFF2A24);
        mTvName.setTextSize(13.4f);
        mRlRoot.setBackgroundColor(0XFFFFFF);
    }

    public void setNormalStyle() {

//        mIvTip.setVisibility(View.INVISIBLE);
        mTvName.setTextColor(0XFF444444);
        mTvName.setTextSize(12.5f);
        mRlRoot.setBackgroundColor(0XFFF6F6F6);
    }
}
