package com.eric.ui.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author li
 * @Package com.eric.ui.image
 * @Title: GalleryDialogAdapter
 * @Description: Copyright (c)
 * Create DateTime: 2016/10/19
 */
public class GridImageView extends View {

    private int dwidth;
    private int dheight;
    private int vwidth;
    private int vheight;

    private Matrix mDrawMatrix = new Matrix();
    private Drawable mDrawable;
    private Context mContext;
    public static final int CENTER_CROP = 0;
    public static final int CENTER_INSIDE = 1;
    private int scaleType = 0;

    public GridImageView(Context context) {
        super(context);
        this.mContext = context;
        // TODO Auto-generated constructor stub
    }

    public GridImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        // TODO Auto-generated constructor stub
    }

    public GridImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
        // TODO Auto-generated constructor stub
    }

    public void setScaleType(int scaleType) {
        this.scaleType = scaleType;
    }

    public void setViewBounds(int width, int height) {
        this.vwidth = width;
        this.vheight = height;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setImageDrawable(Drawable drawable) {
        updateDrawable(drawable);
        dwidth = drawable.getIntrinsicWidth();
        dheight = drawable.getIntrinsicHeight();
        mDrawable.setBounds(0, 0, dwidth, dheight);
        float scale;
        float dx = 0, dy = 0;

        switch (scaleType) {
            case CENTER_CROP:


                if (dwidth * vheight > vwidth * dheight) {
                    scale = (float) vheight / (float) dheight;
                    dx = (vwidth - dwidth * scale) * 0.5f;
                } else {
                    scale = (float) vwidth / (float) dwidth;
                    dy = (vheight - dheight * scale) * 0.5f;
                }

                mDrawMatrix.reset();
                mDrawMatrix.setScale(scale, scale);
                mDrawMatrix.postTranslate((int) (dx + 0.5f), (int) (dy + 0.5f));

                break;
            case CENTER_INSIDE:

                if (dwidth <= vwidth && dheight <= vheight) {
                    scale = 1.0f;
                } else {
                    scale = Math.min((float) vwidth / (float) dwidth, (float) vheight / (float) dheight);
                }

                dx = (int) ((vwidth - dwidth * scale) * 0.5f + 0.5f);
                dy = (int) ((vheight - dheight * scale) * 0.5f + 0.5f);

                mDrawMatrix.reset();
                mDrawMatrix.setScale(scale, scale);
                mDrawMatrix.postTranslate(dx, dy);
                break;
        }


        invalidate();
    }

    public void setImageBitmap(Bitmap bm) {
        setImageDrawable(new BitmapDrawable(mContext.getResources(), bm));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mDrawMatrix != null) {
            canvas.concat(mDrawMatrix);
        }
        if (mDrawable != null) {

            mDrawable.draw(canvas);
        }
//		if(bitmap!=null && mDrawMatrix!=null){
//			canvas.drawBitmap(bitmap, mDrawMatrix, new Paint());
//		}

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mDrawable != null) {
            mDrawable.setVisible(getVisibility() == VISIBLE, false);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mDrawable != null) {
            mDrawable.setVisible(false, false);
        }
    }


    private void updateDrawable(Drawable d) {
        if (mDrawable != null) {
            mDrawable.setCallback(null);
            unscheduleDrawable(mDrawable);
        }
        mDrawable = d;
        if (d != null) {
            d.setCallback(this);
            if (d.isStateful()) {
                d.setState(getDrawableState());
            }
            d.setLevel(0);
//            d.setLayoutDirection(getLayoutDirection());
//            mDrawableWidth = d.getIntrinsicWidth();
//            mDrawableHeight = d.getIntrinsicHeight();
//            applyColorMod();
//            configureBounds();
        }
    }

}
