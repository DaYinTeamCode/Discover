package com.zkteam.discover;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.zkteam.discover.adapter.DiscoverLeve1Adapter;
import com.zkteam.discover.adapter.DiscoverLevel2Adapter;
import com.zkteam.discover.base.OnExRvItemViewClickListener;
import com.zkteam.discover.bean.DiscoverIndexResult;
import com.zkteam.discover.bean.DiscoverOper;
import com.zkteam.discover.decoration.DiscoverIndexLevel2Decoration;
import com.zkteam.discover.manager.TopSnappedLayoutManager;
import com.zkteam.discover.util.CollectionUtil;
import com.zkteam.discover.util.DimenConstant;
import com.zkteam.discover.util.DiscoverIndexUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.ervLevel1)
    RecyclerView mErvLevel1;
    public DiscoverLeve1Adapter mLevel1Adapter;
    public LinearLayoutManager mLevel1LayoutMgr;

    @BindView(R.id.ervLevel2)
    RecyclerView mErvLevel2;
    public DiscoverLevel2Adapter mLevel2Adapter;
    public TopSnappedLayoutManager mLevel2LayoutMgr;
    private Unbinder unbinder;

    private float halfHeight = -1.0f;                        // 屏幕一半高度
    private final int DEFAULT_INT = -1024;                   // 默认位置
    private int targetPosition = -1024;                      // 右侧默认位置
    private int mCurrentLeftPos;                             // 当前左侧位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        initData();
        initContentView();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (unbinder != null)
            unbinder.unbind();
    }

    private void initData() {

    }

    private void initContentView() {

        initLevel1Views();
        initLevel2Views();
        initlidateContent();
    }

    /**
     * 一级类目
     */
    private void initLevel1Views() {

        mLevel1Adapter = new DiscoverLeve1Adapter();
        mLevel1Adapter.setOnExRvItemViewClickListener(new OnExRvItemViewClickListener() {
            @Override
            public void onExRvItemViewClick(View view, int dataPos) {

                onListItemLeve1ViewClick(dataPos);
            }
        });

        mLevel1LayoutMgr = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mErvLevel1.setLayoutManager(mLevel1LayoutMgr);
        mErvLevel1.setAdapter(mLevel1Adapter);
        ViewGroup.LayoutParams vglp = mErvLevel1.getLayoutParams();
        vglp.width = (int) (DimenConstant.SCREEN_WIDTH * (0.25));
    }

    /**
     * 二级类目
     */
    private void initLevel2Views() {

        mLevel2Adapter = new DiscoverLevel2Adapter();
        mLevel2Adapter.setOnExRvItemViewClickListener(new OnExRvItemViewClickListener() {
            @Override
            public void onExRvItemViewClick(View view, int dataPos) {

                onRvItemLevel2ViewClick(dataPos);
            }
        });

        mLevel2LayoutMgr = new TopSnappedLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);

        mLevel2LayoutMgr.setRecycleChildrenOnDetach(true);
        mLevel2LayoutMgr.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int dataPos) {

                int type = mLevel2Adapter.getDataItemViewType(dataPos);
                switch (type) {
                    case DiscoverLevel2Adapter.TYPE_ITEM_BANNER:
                    case DiscoverLevel2Adapter.TYPE_ITEM_TITLE:
                        return 3;
                    default:
                    case DiscoverLevel2Adapter.TYPE_ITEM_WEBVIEW:
                        return 1;
                }
            }
        });
        mErvLevel2.setLayoutManager(mLevel2LayoutMgr);
        mErvLevel2.setItemViewCacheSize(10);
        mErvLevel2.addItemDecoration(new DiscoverIndexLevel2Decoration());
        mErvLevel2.setAdapter(mLevel2Adapter);
        mErvLevel2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE || newState == RecyclerView.SCROLL_STATE_DRAGGING)
                    targetPosition = DEFAULT_INT;
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (targetPosition == DEFAULT_INT)
                    setLeftSelection();
            }
        });

        RecyclerView.RecycledViewPool recycledViewPool = mErvLevel2.getRecycledViewPool();
        recycledViewPool.setMaxRecycledViews(mLevel2Adapter.TYPE_ITEM_WEBVIEW, 20);
        ViewGroup.LayoutParams vglp = mErvLevel2.getLayoutParams();
        vglp.width = (int) (DimenConstant.SCREEN_WIDTH * (0.75));
    }

    private void initlidateContent() {

        try {

            String jsonData = getAssetsData("discover.json");
            DiscoverIndexResult discoverIndexResult = JSON.parseObject(jsonData, DiscoverIndexResult.class);

            final List<DiscoverOper> opers = discoverIndexResult.getDiscoverList();
            DiscoverIndexUtil.filterNullOperList(opers);
            discoverIndexResult.setLocalLevel2List(DiscoverIndexUtil.merageOperLevel2List(opers));

            if (CollectionUtil.isEmpty(discoverIndexResult.getDiscoverList()) && CollectionUtil.isEmpty(discoverIndexResult.getLocalLevel2List()))
                return;

            invalidateLevel1View(discoverIndexResult.getDiscoverList());
            invalidateLevel2View(discoverIndexResult.getLocalLevel2List());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
    }

    private void invalidateLevel1View(List<DiscoverOper> discoverList) {

        mLevel1Adapter.setData(discoverList);
        mLevel1Adapter.notifyDataSetChanged();
    }

    private void invalidateLevel2View(List<DiscoverOper> localLevel2List) {

        mLevel2Adapter.setData(localLevel2List);
        mLevel2Adapter.notifyDataSetChanged();
    }

    private void setLeftSelection() {

        try {

            int leftPos = setLeftPosition();
            if (leftPos == -1)
                return;

            if (!mErvLevel2.canScrollVertically(1))
                leftPos = mLevel1Adapter.getDataLastItemPosition();

            if (mLevel1LayoutMgr.findFirstVisibleItemPosition() > leftPos)
                mErvLevel1.smoothScrollToPosition(leftPos);
            else if (mLevel1LayoutMgr.findLastVisibleItemPosition() < leftPos)
                mErvLevel1.smoothScrollToPosition(leftPos);

            if (mCurrentLeftPos != leftPos) {

                // 居中滚动
                scrollListItemLevel1ViewToCenter(leftPos);
                mCurrentLeftPos = leftPos;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private int setLeftPosition() {

        int fristPosition = mLevel2LayoutMgr.findFirstVisibleItemPosition();
        int lastPosition = mLevel2LayoutMgr.findLastVisibleItemPosition();

        if (halfHeight == -1.0f)
            halfHeight = ((float) (mErvLevel2.getHeight()) * 0.5f);

        for (int i = fristPosition; i <= lastPosition; i++) {

            Object obj = mLevel2Adapter.getDataItem(i);
            if (obj instanceof DiscoverOper) {

                DiscoverOper discoverOper = (DiscoverOper) obj;
                if (discoverOper.isTypeTitle()) {

                    float currentTop = mErvLevel2.getChildAt(i - fristPosition).getTop();

                    if (currentTop > halfHeight)   //小于屏幕一半  切换 Select
                        return discoverOper.getParentPosition() - 1;
                    else
                        return discoverOper.getParentPosition();
                }
            }
        }
        return -1;
    }

    private void onListItemLeve1ViewClick(int dataPos) {

        // 选中列表Postition位置元素 并居中
        scrollListItemLevel1ViewToCenter(dataPos);
        // 根据Position查找列表对应Item锚点定位到顶部
        level2ListScrollToPosition(dataPos);
    }

    private void onRvItemLevel2ViewClick(int dataPos) {

        Object object = mLevel2Adapter.getDataItem(dataPos);
        if (object instanceof DiscoverOper)
            Toast.makeText(this, String.format("点击%s", ((DiscoverOper) object).getTitle()), Toast.LENGTH_LONG).show();
    }

    private void scrollListItemLevel1ViewToCenter(int position) {

        try {

            View childAt = mErvLevel1.getChildAt(position - mLevel1LayoutMgr.findFirstVisibleItemPosition());
            if (childAt != null) {

                int y = (childAt.getTop() - mErvLevel1.getHeight() / 2);
                mErvLevel1.smoothScrollBy(0, y);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

            mLevel1Adapter.setSelectPos(position);
        }
    }

    private void level2ListScrollToPosition(int dataPos) {

        Object obj = mLevel1Adapter.getDataItem(dataPos);
        if (obj instanceof DiscoverOper) {

            DiscoverOper oper = (DiscoverOper) obj;
            targetPosition = mLevel2Adapter.getSelectPosition(oper.getElement_id());
            if (targetPosition > -1)
                mErvLevel2.smoothScrollToPosition(targetPosition);
        }
    }

    /**
     * 加载资源文件
     *
     * @param path
     * @return
     */
    private String getAssetsData(String path) {
        String result = "";
        try {
            //获取输入流
            InputStream mAssets = getAssets().open(path);
            //获取文件的字节数
            int lenght = mAssets.available();
            //创建byte数组
            byte[] buffer = new byte[lenght];
            //将文件中的数据写入到字节数组中
            mAssets.read(buffer);
            mAssets.close();
            result = new String(buffer);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return result;
        }
    }
}

