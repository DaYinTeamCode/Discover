package com.zkteam.discover.vh;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zkteam.discover.R;
import com.zkteam.discover.base.ExRvItemViewHolderBase;
import com.zkteam.discover.bean.Oper;
import com.zkteam.discover.fresco.FrescoImageView;
import com.zkteam.discover.util.TextUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ===========================================================
 * 作    者：大印（高印） Github地址：https://github.com/GaoYin2016
 * 邮    箱：18810474975@163.com
 * 版    本：
 * 创建日期：2018/2/28 下午7:59
 * 描    述：第二级mini小方块运营位
 * 修订历史：
 * ===========================================================
 */
public class DiscoverIndexLevel2MiniViewHolder extends ExRvItemViewHolderBase {

    @BindView(R.id.fivCover)
    FrescoImageView mFivCover;

    @BindView(R.id.tvName)
    TextView mTvName;

    public DiscoverIndexLevel2MiniViewHolder(ViewGroup viewGroup) {

        super(viewGroup, R.layout.page_discover_index_level2_vh_mini);
        ButterKnife.bind(this, itemView);
    }

    @Override
    protected void initConvertView(View convertView) {

        convertView.setOnClickListener(this);
    }

    public void invalidateView(Oper oper) {

        if (oper == null) {

            mTvName.setText(TextUtil.TEXT_EMPTY);
            mFivCover.setImageUriByLp((String) null);
        } else {

            mTvName.setText(oper.getTitle());
            mFivCover.setImageUriByLp(oper.getPic());
        }
    }
}
