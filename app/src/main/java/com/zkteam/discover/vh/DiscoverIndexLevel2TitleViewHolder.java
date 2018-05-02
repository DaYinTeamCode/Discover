package com.zkteam.discover.vh;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zkteam.discover.R;
import com.zkteam.discover.base.ExRvItemViewHolderBase;
import com.zkteam.discover.bean.DiscoverOper;
import com.zkteam.discover.util.TextUtil;
import com.zkteam.discover.util.ViewUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ===========================================================
 * 作    者：大印（高印） Github地址：https://github.com/GaoYin2016
 * 邮    箱：18810474975@163.com
 * 版    本：
 * 创建日期：2018/3/6 下午6:34
 * 描    述： 第二级标题栏运营位
 * 修订历史：
 * ===========================================================
 */
public class DiscoverIndexLevel2TitleViewHolder extends ExRvItemViewHolderBase {

    @BindView(R.id.tvTitle)
    TextView mTvTitle;

    @BindView(R.id.tvMore)
    TextView mTvMore;

    public DiscoverIndexLevel2TitleViewHolder(ViewGroup viewGroup) {

        super(viewGroup, R.layout.page_discover_index_level2_vh_title);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void initConvertView(View convertView) {

        convertView.setOnClickListener(this);
    }

    public void invalidateView(DiscoverOper oper) {

        if (oper == null) {

            mTvTitle.setText(TextUtil.TEXT_EMPTY);
            ViewUtil.hideView(mTvMore);
        } else {

            mTvTitle.setText(oper.getTitle());
        }

    }
}
