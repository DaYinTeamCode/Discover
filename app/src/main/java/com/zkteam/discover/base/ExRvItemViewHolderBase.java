package com.zkteam.discover.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class ExRvItemViewHolderBase extends RecyclerView.ViewHolder implements View.OnClickListener {

    private View mConvertView;
    private int mDataPosition = -1, mDataOldPosition = -2;
    private OnExRvItemViewClickListener mClickLisn;


    public ExRvItemViewHolderBase(View itemView) {

        super(itemView);
        mConvertView = itemView;
    }

    protected ExRvItemViewHolderBase(ViewGroup parent, int itemViewResId) {

        this(LayoutInflater.from(parent.getContext()).inflate(itemViewResId, parent, false));
    }


    /**************************** 供适配器回调函数 ******************************/

    public final void onAdapterSetEventListener(OnExRvItemViewClickListener clickLisn) {

        mClickLisn = clickLisn;
    }

    public final void onAdapterInitConvertView() {

        initConvertView(mConvertView);
    }

    public final void onAdapterSetDataPosition(int position) {

        mDataOldPosition = mDataPosition;
        mDataPosition = position;
    }

    public void onAdapterViewRecycled() {

        //nothing
    }

    public void onAdapterViewDetachedFromWindow() {

        //nothing
    }

    public void onAdapterViewAttachedToWindow() {

        //nothing
    }


    /**************************** 供子类覆写函数 ******************************/


    protected abstract void initConvertView(View convertView);


    /**************************** 供子类调用函数 ******************************/


    public View getConvertView() {

        return mConvertView;
    }

    /**
     * 返回校正过的pos值,保证了有header时返回的pos是data的pos
     * 尽量使用该函数, adapterPosition()尽量弃用
     *
     * @return
     */
    public final int getDataPosition() {

        return mDataPosition;
    }

    /**
     * 返回校正过的pos值,保证了有header时返回的pos是data的pos
     * 尽量使用该函数, adapterPosition()尽量弃用
     *
     * @return
     */
    public final int getOldDataPosition() {

        return mDataOldPosition;
    }

    /**
     * 默认onclick实现
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        callbackClickListener(v, getDataPosition());
    }

    protected void callbackClickListener(View view, int dataPos) {

        if (mClickLisn != null)
            mClickLisn.onExRvItemViewClick(view, dataPos);
    }
}
