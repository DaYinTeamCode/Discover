package com.zkteam.discover.adapter;

import android.view.ViewGroup;

import com.zkteam.discover.base.ExRvAdapterBase;
import com.zkteam.discover.bean.DiscoverOper;
import com.zkteam.discover.util.CollectionUtil;
import com.zkteam.discover.vh.DiscoverIndexLevel1ViewHolder;

import java.util.List;

/**
 * ===========================================================
 * 作    者：大印（高印） Github地址：https://github.com/GaoYin2016
 * 邮    箱：18810474975@163.com
 * 版    本：
 * 创建日期：2018/4/24 上午10:57
 * 描    述：
 * 修订历史：
 * ===========================================================
 */
public class DiscoverLeve1Adapter extends ExRvAdapterBase<DiscoverOper, DiscoverIndexLevel1ViewHolder> {

    // 选中
    public static final String STATUS_SELECT = "SELECT";
    // 默认
    public static final String STATUS_NOMAL = "NOMAL";
    // 当前索引
    private int mSelectPosition;

    public void setSelectPos(int position) {

        if (position >= 0 && position < getDataItemCount() && mSelectPosition != position) {

            notifyItemRangeChanged(mSelectPosition, 1, STATUS_NOMAL);
            notifyItemRangeChanged(position, 1, STATUS_SELECT);
            mSelectPosition = position;
        }
    }

    @Override
    public DiscoverIndexLevel1ViewHolder onCreateDataViewHolder(ViewGroup parent, int viewType) {

        return new DiscoverIndexLevel1ViewHolder(parent);
    }

    @Override
    public void onBindDataViewHolder(DiscoverIndexLevel1ViewHolder holder, int dataPos) {

        holder.invalidateView(getDataItem(dataPos), dataPos == mSelectPosition);
    }

    @Override
    public void onBindDataViewHolder(DiscoverIndexLevel1ViewHolder holder, int dataPos, List<Object> payLoads) {

        if (CollectionUtil.isEmpty(payLoads))
            return;

        String payload = (String) payLoads.get(0);
        if (STATUS_SELECT.equalsIgnoreCase(payload))
            holder.setSelectedStyle();  //选中状态
        else if (STATUS_NOMAL.equalsIgnoreCase(payload))
            holder.setNormalStyle();   // 默认状态
    }
}
