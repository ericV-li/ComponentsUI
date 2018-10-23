package com.eric.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author li
 * @Package com.eric.view.RefreshLayout
 * @Title: RefreshLayout
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 下拉刷新容器，后续会添加顶部这种样式
 */

@TargetApi(Build.VERSION_CODES.DONUT)
public class RefreshLayout extends SwipeRefreshLayout implements OnScrollListener {

    /**
     * 刷新回调
     */
    private OnViewRefreshListener mListener;

    /**
     * 滚动监听
     */
    private OnListViewScrollListener mOnListViewScrollListener;

    /**
     * 滑动状态监听
     */
    private OnListViewScrollStateChanged mOnListViewScrollStateChanged;

    /**
     * 滑动到最下面时的上拉操作
     */
    private int mTouchSlop;

    /**
     * 记录手指按下的Y位置（相对于容器的位置坐标）
     */
    private float startY;

    /**
     * 记录手指按下的X位置（相对于容器的位置坐标）
     */
    private float startX;

    /**
     * 记录viewPager或listview或gridview是否拖拽的标记
     */
    private boolean mIsChildDragging;
    /**
     * listview实例
     */
    private RefreshListView mLJRefreshListView;

    /**
     * ListView的加载中footer
     */
    private View mFootContainerView;
    /**
     * ListView的已加载所有footer
     */
    private View mFootLoadEnd;

    /**
     * 底部加载更多控件
     */
    private TextView mFootLoadEndText;

    /**
     * 按下时的y坐标
     */
    private int mYDown;

    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;

    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    private boolean mIsLoading = false;

    /**
     * 是否可以加载更多
     */
    private boolean mHasMore = false;

    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mFootContainerView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.baseview_view_refresh_bottom, null);
        mFootLoadEnd = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.baseview_view_refresh_bottom_end, null);
        mFootLoadEndText = (TextView) mFootLoadEnd.findViewById(R.id.view_load_end_text);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 初始化ListView对象
        if (mLJRefreshListView == null) {
            if (getChildCount() > 0) {
                for (int i = 0; i < getChildCount(); i++) {
                    if (getChildAt(i) instanceof RefreshListView) {
                        mLJRefreshListView = (RefreshListView) getChildAt(i);
                        mLJRefreshListView.setOnScrollListener(this);
                        break;
                    }
                }
            }
        }
    }

    /**
     * 代码里调用下拉刷新，但是不显示下拉刷新loading框
     */
    public void setRefreshingNoLoading() {
        setProgressViewOffset(false, 0, (int) (-60 * getResources().getDisplayMetrics().density));
        setRefreshing(true);
        post(new Runnable() {
            @Override
            public void run() {
                setProgressViewOffset(false, (int) (-60 * getResources().getDisplayMetrics().density), (int) (64 * getResources
                        ().getDisplayMetrics().density));
            }
        });
    }

    /**
     * 手动调用刷新事件，开始刷新和关闭刷新
     *
     * @param refresh 是否刷新
     */
    @Override
    public void setRefreshing(boolean refresh) {
        super.setRefreshing(refresh);
        if (refresh) {
            mIsLoading = true;
            if (mListener != null) {
                mListener.onRefresh(false);
            }
        } else {
            mIsLoading = false;
        }
    }

    /**
     * 设置加载监听
     *
     * @param listener 监听
     */
    public void setOnRefreshListener(OnViewRefreshListener listener) {
        mListener = listener;
        setOnRefreshListener(new OnRefreshListener() {

            @Override
            public void onRefresh() {
                if (mListener != null) {
                    mListener.onRefresh(false);
                }
            }
        });
    }

    /**
     * 若有listview滚动时，可设置滚动监听
     *
     * @param listener 滚动监听
     */
    public void setOnListViewScrollListener(OnListViewScrollListener listener) {
        mOnListViewScrollListener = listener;
    }

    /**
     * listview 滑动状态监听
     *
     * @param listener 滑动状态监听
     */
    public void setOnListViewScrollStateChanged(OnListViewScrollStateChanged listener) {
        mOnListViewScrollStateChanged = listener;
    }

    /**
     * 为了对基础控件进行分装，外部不允许直接调用此方法
     *
     * @param listener 刷新监听
     */
    @Override
    @Deprecated
    public void setOnRefreshListener(OnRefreshListener listener) {
        super.setOnRefreshListener(listener);
    }

    /**
     * 设置是否可以上拉加载更多
     *
     * @param enable 是否可用
     */
    public void setLoadingMoreEnabled(boolean enable) {
        if (mLJRefreshListView != null) {
            mHasMore = enable;
            mLJRefreshListView.removeFooterView(mFootContainerView);
            mLJRefreshListView.removeFooterView(mFootLoadEnd);
        }
    }

    /**
     * 设置是否可以上拉加载更多
     *
     * @param enable 是否可用
     */
    public void setLoadingMoreEnabled(boolean enable, String loadFinishMsg) {
        if (mLJRefreshListView != null) {
            mHasMore = enable;
            mLJRefreshListView.removeFooterView(mFootContainerView);
            mLJRefreshListView.removeFooterView(mFootLoadEnd);
            if (!mHasMore && !TextUtils.isEmpty(loadFinishMsg)) {
                mLJRefreshListView.addFooterView(mFootLoadEnd);
                mFootLoadEndText.setText(loadFinishMsg);
            }
        }
    }

    /**
     * 刷新监听
     */
    public interface OnViewRefreshListener {

        /**
         * 刷新
         *
         * @param isLoadingMore 是否加载更多
         */
        void onRefresh(boolean isLoadingMore);
    }

    /**
     * 滚动监听
     */
    public interface OnListViewScrollListener {

        /**
         * 滚动
         *
         * @param view             AbsListView
         * @param firstVisibleItem 第一个可见item的position
         * @param visibleItemCount 可见item个数
         * @param totalItemCount   总数据个数
         */
        void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount);
    }

    /**
     * 滚动状态监听
     */
    public interface OnListViewScrollStateChanged {

        /**
         * 滚动状态改变
         *
         * @param view        AbsListView
         * @param scrollState 滚动状态。 参考AbsListView的OnScrollListener
         */
        void onScrollStateChanged(AbsListView view, int scrollState);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mYDown = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                mLastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                if (canLoadMore()) {
                    loadMore();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
     *
     * @return 是否可以加载更多
     */
    private boolean canLoadMore() {
        return isBottom() && !mIsLoading && isPullUp() && mHasMore;
    }

    /**
     * 判断是否到了最底部
     *
     * @return 是否到了最底部
     */
    private boolean isBottom() {
        if (mLJRefreshListView != null && mLJRefreshListView.getAdapter() != null) {
            return mLJRefreshListView.getLastVisiblePosition() == (mLJRefreshListView.getAdapter().getCount() - 1);
        }
        return false;
    }

    /**
     * 是否是上拉操作
     *
     * @return 是否上拉
     */
    private boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadMore() {
        if (mListener != null) {
            // 设置状态
            setLoading(true);
            mListener.onRefresh(true);
        }
    }

    /**
     * 设置是否加载更多
     *
     * @param loading 是否加载更多  true：加载更多，false:下拉刷新
     */
    private void setLoading(boolean loading) {
        mIsLoading = loading;
        if (mIsLoading) {
            mLJRefreshListView.removeFooterView(mFootContainerView);
            mLJRefreshListView.addFooterView(mFootContainerView);
        } else {
            mYDown = 0;
            mLastY = 0;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (mOnListViewScrollStateChanged != null) {
            mOnListViewScrollStateChanged.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (mOnListViewScrollListener != null) {
            mOnListViewScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
        // 滚动时到了最底部也可以加载更多
        if (canLoadMore()) {
            loadMore();
        }
    }

    @Override
    public void setVisibility(int visibility) {
        if (mLJRefreshListView != null) {
            mLJRefreshListView.setVisibility(visibility);
        }
        super.setVisibility(visibility);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 记录手指按下的位置
                startY = ev.getY();
                startX = ev.getX();
                // 初始化标记
                mIsChildDragging = false;
                break;
            case MotionEvent.ACTION_MOVE:
                // 如果子view(如：viewpager)正在拖拽中，那么不拦截它的事件，直接return false；
                if (mIsChildDragging) {
                    return false;
                }
                // 获取当前手指位置
                float endY = ev.getY();
                float endX = ev.getX();
                float distanceX = Math.abs(endX - startX);
                float distanceY = Math.abs(endY - startY);
                // 如果X轴位移大于Y轴位移，那么将事件交给子view(如：viewpager)处理。
                if (distanceX > mTouchSlop && distanceX > distanceY) {
                    mIsChildDragging = true;
                    return false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 初始化标记
                mIsChildDragging = false;
                break;
        }
        // 如果是Y轴位移大于X轴，事件交给swipeRefreshLayout处理。
        return super.onInterceptTouchEvent(ev);
    }
}
