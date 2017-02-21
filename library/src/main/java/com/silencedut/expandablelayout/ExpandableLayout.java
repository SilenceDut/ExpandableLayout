package com.silencedut.expandablelayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * Created by SilenceDut on 16/6/6.
 */

public class ExpandableLayout extends LinearLayout {
    private static final String TAG = ExpandableLayout.class.getSimpleName();

    private Settings mSettings ;
    private int mExpandState;
    private ValueAnimator mExpandAnimator;
    private ValueAnimator mParentAnimator;
    private AnimatorSet mExpandScrollAnimotorSet;
    private  int mExpandedViewHeight;
    private  boolean mIsInit = true;

    private ScrolledParent mScrolledParent;

    private OnExpandListener mOnExpandListener;
    public ExpandableLayout(Context context) {
        super(context);
        init(null);
    }

    public ExpandableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ExpandableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs) {
        setClickable(true);
        setOrientation(VERTICAL);
        this.setClipChildren(false);
        this.setClipToPadding(false);
        mExpandState = ExpandState.PRE_INIT;
        mSettings = new Settings();
        if(attrs!=null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableLayout);
            mSettings.expandDuration = typedArray.getInt(R.styleable.ExpandableLayout_expDuration, Settings.EXPAND_DURATION);
            mSettings.expandWithParentScroll = typedArray.getBoolean(R.styleable.ExpandableLayout_expWithParentScroll,false);
            mSettings.expandScrollTogether = typedArray.getBoolean(R.styleable.ExpandableLayout_expExpandScrollTogether,true);
            typedArray.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        if(childCount!=2) {
            throw new IllegalStateException("ExpandableLayout must has two child view !");
        }
        if(mIsInit) {
            ((MarginLayoutParams)getChildAt(0).getLayoutParams()).bottomMargin=0;
            MarginLayoutParams marginLayoutParams = ((MarginLayoutParams)getChildAt(1).getLayoutParams());
            marginLayoutParams.bottomMargin=0;
            marginLayoutParams.topMargin=0;
            marginLayoutParams.height = 0;
            mExpandedViewHeight = getChildAt(1).getMeasuredHeight();
            mIsInit =false;
            mExpandState = ExpandState.CLOSED;
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mSettings.expandWithParentScroll) {
            mScrolledParent = Utils.getScrolledParent(this);
        }
    }

    private int getParentScrollDistance () {
        int distance =0;

        if(mScrolledParent==null) {
            return distance;
        }

        distance = (int) (getY()+getMeasuredHeight()+mExpandedViewHeight
                -mScrolledParent.scrolledView.getMeasuredHeight());
        for(int index =0;index<mScrolledParent.childBetweenParentCount;index++) {
            ViewGroup parent = (ViewGroup) getParent();
            distance+=parent.getY();
        }

        return distance;
    }


    private void verticalAnimate(final int startHeight, final int endHeight ) {
        int distance = getParentScrollDistance();

        final View target = getChildAt(1);
        mExpandAnimator = ValueAnimator.ofInt(startHeight,endHeight);
        mExpandAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                target.getLayoutParams().height= ( int) animation.getAnimatedValue();
                target.requestLayout();
            }
        });

        mExpandAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if(endHeight-startHeight<0) {
                    mExpandState=ExpandState.CLOSED;
                    if(mOnExpandListener!=null) {
                        mOnExpandListener.onExpand(false);
                    }
                }else {
                    mExpandState=ExpandState.EXPANDED;
                    if(mOnExpandListener!=null) {
                        mOnExpandListener.onExpand(true);
                    }
                }
            }
        });

        mExpandState=mExpandState==ExpandState.EXPANDED?ExpandState.CLOSING :ExpandState.EXPANDING;
        mExpandAnimator.setDuration(mSettings.expandDuration);
        if(mExpandState==ExpandState.EXPANDING&&mSettings.expandWithParentScroll&&distance>0) {

            mParentAnimator = Utils.createParentAnimator(mScrolledParent.scrolledView,distance,mSettings.expandDuration);

            mExpandScrollAnimotorSet = new AnimatorSet();

            if(mSettings.expandScrollTogether) {
                mExpandScrollAnimotorSet.playTogether(mExpandAnimator,mParentAnimator);
            }else {
                mExpandScrollAnimotorSet.playSequentially(mExpandAnimator,mParentAnimator);
            }
            mExpandScrollAnimotorSet.start();

        }else {
            mExpandAnimator.start();
        }
    }

    public void setExpand(boolean expand) {
        if(mExpandState == ExpandState.PRE_INIT) {
            return;
        }
        getChildAt(1).getLayoutParams().height=expand?mExpandedViewHeight:0;
        requestLayout();
        mExpandState=expand?ExpandState.EXPANDED:ExpandState.CLOSED;
    }

    public boolean isExpanded() {
        return mExpandState==ExpandState.EXPANDED;
    }

    public void toggle() {
        if(mExpandState==ExpandState.EXPANDED) {
            close();
        }else if(mExpandState==ExpandState.CLOSED) {
            expand();
        }
    }

    public void expand() {
        verticalAnimate(0,mExpandedViewHeight);
    }

    public void close() {
        verticalAnimate(mExpandedViewHeight,0);
    }

    @Override
    public boolean performClick() {
        toggle();
        return super.performClick();
    }

    public interface OnExpandListener {
        void onExpand(boolean expanded) ;
    }

    public void setOnExpandListener(OnExpandListener onExpandListener) {
        this.mOnExpandListener = onExpandListener;
    }


    public void setExpandScrollTogether(boolean expandScrollTogether) {
        this.mSettings.expandScrollTogether = expandScrollTogether;
    }

    public void setExpandWithParentScroll(boolean expandWithParentScroll) {
        this.mSettings.expandWithParentScroll = expandWithParentScroll;
    }

    public void setExpandDuration(int expandDuration) {
        this.mSettings.expandDuration = expandDuration;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(mExpandAnimator!=null&&mExpandAnimator.isRunning()) {
            mExpandAnimator.cancel();
            mExpandAnimator.removeAllUpdateListeners();
        }
        if(mParentAnimator!=null&&mParentAnimator.isRunning()) {
            mParentAnimator.cancel();
            mParentAnimator.removeAllUpdateListeners();
        }
        if(mExpandScrollAnimotorSet!=null) {
            mExpandScrollAnimotorSet.cancel();
        }
    }
}
