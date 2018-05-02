package com.zkteam.discover.base;

import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class ExRvAdapterBase<T, K extends ExRvItemViewHolderBase> extends RecyclerView.Adapter<ExRvItemViewHolderBase> {

    private List<T> mData;
    private int mOrientation = OrientationHelper.VERTICAL;

    private OnExRvItemViewClickListener mClickLisn;

    protected ExRvAdapterBase() {
    }

    protected ExRvAdapterBase(List<T> data) {

        setData(data);
    }


    //************************ 覆写的父类函数 **************************


    /**
     * 返回所有item个数
     * 包含 header,footer,data
     * 如果没有 header,footer 就只是data的size
     *
     * @return
     */
    @Override
    public final int getItemCount() {

        return getDataItemCount();
    }

    /**
     * 返回 item 的id
     * 默认实现返回 position
     *
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {

        return position;
    }

    /**
     * 如果是header 返回 ITEM_VIEW_TYPE_HEADER
     * 如果是footer 返回 ITEM_VIEW_TYPE_FOOTER
     * 如果是data item 返回 0
     *
     * @param position
     * @return
     */
    @Override
    public final int getItemViewType(int position) {

        return getDataItemViewType(getDataPosByAdapterPos(position));
    }

    @Override
    public final ExRvItemViewHolderBase onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            default:
                K vh = onCreateDataViewHolder(parent, viewType);
                vh.onAdapterSetEventListener(mClickLisn);
                vh.onAdapterInitConvertView();
                return vh;
        }
    }

    /**
     * 如果有header position 偏移量要减 1, 才是实际data的position
     *
     * @param holder
     * @param position
     */
    @Override
    public final void onBindViewHolder(ExRvItemViewHolderBase holder, int position) {

        switch (holder.getItemViewType()) {
            default:
                int dataPos = getDataPosByAdapterPos(position);
                holder.onAdapterSetDataPosition(dataPos);
                onBindDataViewHolder((K) holder, dataPos);
                break;
        }
    }

    @Override
    public void onBindViewHolder(ExRvItemViewHolderBase holder, int position, List<Object> payloads) {

        super.onBindViewHolder(holder, position, payloads);
        onBindDataViewHolder((K) holder, getDataPosByAdapterPos(position), payloads);
    }

    @Override
    public void onViewAttachedToWindow(ExRvItemViewHolderBase holder) {

        if (holder != null)
            holder.onAdapterViewAttachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(ExRvItemViewHolderBase holder) {

        if (holder != null)
            holder.onAdapterViewDetachedFromWindow();
    }

    @Override
    public void onViewRecycled(ExRvItemViewHolderBase holder) {

        if (holder != null)
            holder.onAdapterViewRecycled();
    }

    //***************************** 供子类覆写的函数 ****************************


    /**
     * 获取data item view type
     *
     * @param dataPos
     * @return
     */
    public int getDataItemViewType(int dataPos) {

        return 0;
    }

    public abstract K onCreateDataViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindDataViewHolder(K holder, int dataPos);

    public void onBindDataViewHolder(K holder, int dataPos, List<Object> payLoads) {
        //nothing
    }

    //*************************** 与data集合操作相关的函数 **************************


    public List<T> getData() {

        return mData;
    }

    /**
     * 返回data的个数
     *
     * @return
     */
    public int getDataItemCount() {

        return mData == null ? 0 : mData.size();
    }

    public T getDataItem(int dataPos) {

        return checkDataPosition(dataPos) ? mData.get(dataPos) : null;
    }

    public void setData(List<T> data) {

        clearData();
        addDataAll(data);
    }

    public void addData(T item) {

        if (item != null) {

            initDataIfNull();
            mData.add(item);
        }
    }

    public void addData(int dataPos, T item) {

        if (item != null) {

            initDataIfNull();
            if (checkDataInsertPosition(dataPos))
                mData.add(dataPos, item);
        }
    }

    public void addDataAll(List<? extends T> data) {

        if (data != null && data.size() > 0) {

            initDataIfNull();
            mData.addAll(data);
        }
    }

    public void addDataAll(int dataPos, List<? extends T> data) {

        if (data != null && data.size() > 0 &&
                checkDataInsertPosition(dataPos)) {

            initDataIfNull();
            mData.addAll(dataPos, data);
        }
    }

    public boolean removeData(T item) {

        if (hasData() && item != null)
            return mData.remove(item);
        else
            return false;
    }

    public T removeData(int dataPos) {

        if (hasData() && checkDataPosition(dataPos))
            return mData.remove(dataPos);
        else
            return null;
    }

    /**
     * 清空数据
     */
    public void clearData() {

        if (mData != null)
            mData.clear();
    }

    /**
     * 获取最后一item的position
     *
     * @return 如果集合为空返回－1
     */
    public int getDataLastItemPosition() {

        return getDataItemCount() - 1;
    }

    /**
     * 获取集合中指定type的第一个position
     *
     * @param type
     * @return
     */
    public int findDataPosByDataItemViewType(int type) {

        if (isDataEmpty())
            return -1;

        for (int i = 0; i < mData.size(); i++) {

            if (getDataItemViewType(i) == type)
                return i;
        }

        return -1;
    }

    /**
     * 获取最后一种itemType的数据对象
     *
     * @param type
     * @return
     */
    public T findDataByLastDataItemViewType(int type) {

        if (isDataEmpty())
            return null;

        int size = getDataItemCount();
        for (int i = size - 1; i >= 0; i--) {

            if (getDataItemViewType(i) == type)
                return getDataItem(i);
        }

        return null;
    }

    /**
     * 获取最后一个元素的类型
     *
     * @return
     */
    public int findDataLastItemViewType() {

        if (isDataEmpty())
            return -1;
        else
            return getDataItemViewType(getDataItemCount() - 1);
    }

    /**
     * 检查pos是否越界
     *
     * @param position
     * @return
     */
    public boolean checkDataPosition(int position) {

        return position >= 0 && position < getDataItemCount();
    }

    /**
     * 检查插入操作pos是否越界
     *
     * @param position
     * @return
     */
    public boolean checkDataInsertPosition(int position) {

        return position >= 0 && position <= getDataItemCount();
    }

    /**
     * 判断是否有数据
     *
     * @return
     */
    public boolean hasData() {

        return getDataItemCount() > 0;
    }

    /**
     * 判断数据是否为空
     *
     * @return
     */
    public boolean isDataEmpty() {

        return getDataItemCount() <= 0;
    }

    /**
     * 将adapterPosition 转换为 data对应的position
     * 因为在有header的情况下,将adapterPosition将不再是data对应的position
     *
     * @param adapterPosition adapter的position
     * @return
     */
    public int getDataPosByAdapterPos(int adapterPosition) {

        return adapterPosition;
    }

    private void initDataIfNull() {

        if (mData == null)
            mData = new ArrayList<T>();
    }

    //***************************** 监听器相关函数 ****************************


    /**
     * 设置点击监听器
     *
     * @param lisn
     */
    public void setOnExRvItemViewClickListener(OnExRvItemViewClickListener lisn) {

        mClickLisn = lisn;
    }

    //**************************   其他   ****************************


    public void setOrientation(int orientation) {

        mOrientation = orientation;
    }

    private String simpleTag() {

        return getClass().getSimpleName();
    }
}
