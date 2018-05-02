package com.zkteam.discover.manager;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.SmoothScroller;
import android.support.v7.widget.RecyclerView.State;
import android.util.AttributeSet;

/**
 * ===========================================================
 * 作    者：大印（高印） Github地址：https://github.com/GaoYin2016
 * 邮    箱：18810474975@163.com
 * 版    本：
 * 创建日期：2018/3/12 下午12:37
 * 描    述： 重写 smoothScrollToPosition 滚动置顶
 * 修订历史：
 * ===========================================================
 */
public class TopSnappedLayoutManager extends GridLayoutManager {

    private class TopSnappedSmoothScroller extends LinearSmoothScroller {
        public TopSnappedSmoothScroller(Context context) {
            super(context);
        }

        public PointF computeScrollVectorForPosition(int targetPosition) {

            return TopSnappedLayoutManager.this.computeScrollVectorForPosition(targetPosition);
        }

        protected int getVerticalSnapPreference() {

            return -1;
        }
    }

    public TopSnappedLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public TopSnappedLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }

    public TopSnappedLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, State state, int position) {
        SmoothScroller smoothScroller = new TopSnappedSmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }
}
