package com.eric.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @author li
 * @Package com.eric.view.MyListView
 * @Title: MyListView
 * @Description: Copyright (c)
 * Create DateTime: 2015/10/19
 * 封装下拉刷新Listview
 */

public class MyListView extends ListView implements OnScrollListener {

    /**
     * 当前控件状态：下拉后释放，进行动画加载
     */
    private final static int RELEASE_To_REFRESH = 0;

    /**
     * 当前控件状态：下拉刷新
     */
    private final static int PULL_To_REFRESH = 1;

    /**
     * 当前控件状态：刷新
     */
    private final static int REFRESHING = 2;

    /**
     * 当前控件状态：结束
     */
    private final static int DONE = 3;

    /**
     * 当前控件状态：加载
     */
    private final static int LOADING = 4;

    /**
     * 实际的padding的距离与界面上偏移距离的比例
     */
    private final static int RATIO = 3;

    /**
     * 填充器
     */
    private LayoutInflater inflater;

    /**
     * 头部控件
     */
    private LinearLayout headView;

    /**
     * 头部提示文字控件
     */
    private TextView tipsTextview;

    /**
     * 最近一次更新的文字控件
     */
    private TextView lastUpdatedTextView;

    /**
     * 头部的向下箭头
     */
    private ImageView arrowImageView;

    /**
     * 进度框
     */
    private ProgressBar progressBar;

    /**
     * 旋转动画
     */
    private RotateAnimation animation;

    /**
     * 重置旋转动画
     */
    private RotateAnimation reverseAnimation;

    /**
     * 用于保证startY的值在一个完整的touch事件中只被记录一次
     */
    private boolean isRecored;

    /**
     * 头部内容宽度
     */
    private int headContentWidth;

    /**
     * 头部内容高度
     */
    private int headContentHeight;

    /**
     * 开始点击的Y坐标
     */
    private int startY;

    /**
     * 第一个可见item的索引
     */
    private int firstItemIndex;

    /**
     * 状态
     */
    private int state;

    /**
     * 是否可返回
     */
    private boolean isBack;

    /**
     * 刷新监听
     */
    private OnRefreshListener refreshListener;

    /**
     * 是否刷新
     */
    private boolean isRefreshable;

    public MyListView(Context context) {
        super(context);
        init(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        //setCacheColorHint(context.getResources().getColor(R.color.transparent));
        inflater = LayoutInflater.from(context);

        headView = (LinearLayout) inflater.inflate(R.layout.baseview_view_listview_head, null);

        arrowImageView = (ImageView) headView.findViewById(R.id.head_arrowImageView);
        arrowImageView.setMinimumWidth(70);
        arrowImageView.setMinimumHeight(50);
        progressBar = (ProgressBar) headView.findViewById(R.id.head_progressBar);
        tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
        lastUpdatedTextView = (TextView) headView.findViewById(R.id.head_lastUpdatedTextView);

        measureView(headView);
        headContentHeight = headView.getMeasuredHeight();
        headContentWidth = headView.getMeasuredWidth();

        headView.setPadding(0, -1 * headContentHeight, 0, 0);
        headView.invalidate();

       // Log.v("size", "width:" + headContentWidth + " height:" + headContentHeight);

        addHeaderView(headView, null, false);
        setOnScrollListener(this);

        animation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(250);
        animation.setFillAfter(true);

        reverseAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation
                .RELATIVE_TO_SELF, 0.5f);
        reverseAnimation.setInterpolator(new LinearInterpolator());
        reverseAnimation.setDuration(200);
        reverseAnimation.setFillAfter(true);

        state = DONE;
        isRefreshable = false;
    }

    /**
     * 滚动监听回调
     */
    public void onScroll(AbsListView arg0, int firstVisiableItem, int arg2, int arg3) {
        firstItemIndex = firstVisiableItem;
    }

    /**
     * 滚动状态回调
     */
    public void onScrollStateChanged(AbsListView arg0, int arg1) {
    }

    /**
     * 手动调用刷新方法
     */
    public void setRefresh() {
        state = REFRESHING;
        changeHeaderViewByState();
        if (getChildCount() > 0) {
            setSelection(0);
            scrollTo(0, 0);
        }
        onRefresh();
    }

    public boolean onTouchEvent(MotionEvent event) {

        if (isRefreshable) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (firstItemIndex == 0 && !isRecored) {
                    isRecored = true;
                    startY = (int) event.getY();
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (state != REFRESHING && state != LOADING) {
                    if (state == DONE) {
                        // 什么都不做
                    }
                    if (state == PULL_To_REFRESH) {
                        state = DONE;
                        changeHeaderViewByState();

                    }
                    if (state == RELEASE_To_REFRESH) {
                        state = REFRESHING;
                        changeHeaderViewByState();
                        onRefresh();

                    }
                }
                isRecored = false;
                isBack = false;
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                int tempY = (int) event.getY();
                if (!isRecored && firstItemIndex == 0) {
                    isRecored = true;
                    startY = tempY;
                }
                if (state != REFRESHING && isRecored && state != LOADING) {

                    // 保证在设置padding的过程中，当前的位置一直是在head，否则如果当列表超出屏幕的话，当在上推的时候，列表会同时进行滚动

                    // 可以松手去刷新了
                    if (state == RELEASE_To_REFRESH) {

                        setSelection(0);

                        // 往上推了，推到了屏幕足够掩盖head的程度，但是还没有推到全部掩盖的地步
                        if (((tempY - startY) / RATIO < headContentHeight) && (tempY - startY) > 0) {
                            state = PULL_To_REFRESH;
                            changeHeaderViewByState();

                        }
                        // 一下子推到顶了
                        else if (tempY - startY <= 0) {
                            state = DONE;
                            changeHeaderViewByState();

                        }
                        // 往下拉了，或者还没有上推到屏幕顶部掩盖head的地步
                        else {
                            // 不用进行特别的操作，只用更新paddingTop的值就行了
                        }
                    }
                    // 还没有到达显示松开刷新的时候,DONE或者是PULL_To_REFRESH状态
                    if (state == PULL_To_REFRESH) {

                        setSelection(0);

                        // 下拉到可以进入RELEASE_TO_REFRESH的状态
                        if ((tempY - startY) / RATIO >= headContentHeight) {
                            state = RELEASE_To_REFRESH;
                            isBack = true;
                            changeHeaderViewByState();

                        }
                        // 上推到顶了
                        else if (tempY - startY <= 0) {
                            state = DONE;
                            changeHeaderViewByState();

                        }
                    }

                    // done状态下
                    if (state == DONE) {
                        if (tempY - startY > 0) {
                            state = PULL_To_REFRESH;
                            changeHeaderViewByState();
                        }
                    }

                    // 更新headView的size
                    if (state == PULL_To_REFRESH) {
                        headView.setPadding(0, -1 * headContentHeight + (tempY - startY) / RATIO, 0, 0);

                    }

                    // 更新headView的paddingTop
                    if (state == RELEASE_To_REFRESH) {
                        headView.setPadding(0, (tempY - startY) / RATIO - headContentHeight, 0, 0);
                    }

                }
            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当状态改变时候，调用该方法，以更新界面
     */
    private void changeHeaderViewByState() {
        if (state == RELEASE_To_REFRESH) {
            arrowImageView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            tipsTextview.setVisibility(View.VISIBLE);
            lastUpdatedTextView.setVisibility(View.VISIBLE);
            arrowImageView.clearAnimation();
            arrowImageView.startAnimation(animation);
            tipsTextview.setText("松开刷新");
        } else if (state == PULL_To_REFRESH) {
            progressBar.setVisibility(View.GONE);
            tipsTextview.setVisibility(View.VISIBLE);
            lastUpdatedTextView.setVisibility(View.VISIBLE);
            arrowImageView.clearAnimation();
            arrowImageView.setVisibility(View.VISIBLE);
            // 是由RELEASE_To_REFRESH状态转变来的
            if (isBack) {
                isBack = false;
                arrowImageView.clearAnimation();
                arrowImageView.startAnimation(reverseAnimation);

                tipsTextview.setText("下拉刷新");
            } else {
                tipsTextview.setText("下拉刷新");
            }
        } else if (state == REFRESHING) {
            headView.setPadding(0, 0, 0, 0);
            progressBar.setVisibility(View.VISIBLE);
            arrowImageView.clearAnimation();
            arrowImageView.setVisibility(View.GONE);
            tipsTextview.setText("正在刷新...");
            lastUpdatedTextView.setVisibility(View.VISIBLE);
        } else if (state == DONE) {
            headView.setPadding(0, -1 * headContentHeight, 0, 0);
            progressBar.setVisibility(View.GONE);
            arrowImageView.clearAnimation();
            arrowImageView.setImageResource(R.drawable.baseview_listview_arrow_down);
            tipsTextview.setText("下拉刷新");
            lastUpdatedTextView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 设置刷新监听
     *
     * @param refreshListener 刷新监听器
     */
    public void setonRefreshListener(OnRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
        isRefreshable = true;
    }

    /**
     * 刷新监听接口
     */
    public interface OnRefreshListener {

        /**
         * 刷新
         */
        void onRefresh();
    }

    /**
     * 刷新结束
     *
     * @param date 最近更新时间
     */
    public void onRefreshComplete(String date) {
        state = DONE;
        if (!TextUtils.isEmpty(date)) {
            lastUpdatedTextView.setText("最近更新:" + date);
        }
        changeHeaderViewByState();
    }

    /**
     * 刷新
     */
    private void onRefresh() {
        if (refreshListener != null) {
            refreshListener.onRefresh();
        }
    }

    /**
     * 测量控件
     *
     * @param child 子控件
     */
    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    /**
     * 设置时间
     *
     * @param dateTime 时间（年月日）
     */
    public void setDateTime(String dateTime) {
        if (!TextUtils.isEmpty(dateTime)) {
            lastUpdatedTextView.setText("最近更新:" + dateTime);
        }
    }

}
