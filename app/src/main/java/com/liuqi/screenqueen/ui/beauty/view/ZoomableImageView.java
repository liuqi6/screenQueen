package com.liuqi.screenqueen.ui.beauty.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

/**
 * @author liuqi 2017/9/28
 * @Package com.liuqi.screenqueen.ui.beauty.view
 * @Title: ZoomableImageView
 * @Description: (放缩图片)
 * Copyright (c) 传化公路港物流有限公司版权所有
 * Create DateTime: 2017/9/28
 */
public class ZoomableImageView extends AppCompatImageView {
    private ScaleGestureDetector mScaleDetector;
    private GestureDetector mGestureDetector;
    private float mCurrentScale = 1.0F;
    private Matrix mCurrentMatrix;
    private float mMidX;
    private float mMidY;
    private GestureDetector.OnGestureListener mOnGestureListener;

    public ZoomableImageView(Context context) {
        super(context);
        init();
    }

    public ZoomableImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZoomableImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        this.mCurrentMatrix = new Matrix();
        ScaleGestureDetector.OnScaleGestureListener scaleListener = new ScaleGestureDetector.SimpleOnScaleGestureListener() {
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                ZoomableImageView.this.mCurrentScale = ZoomableImageView.this.mCurrentScale * scaleFactor;
                if (ZoomableImageView.this.mMidX == 0.0F) {
                    ZoomableImageView.this.mMidX = (float) ZoomableImageView.this.getWidth() / 2.0F;
                }
                if (ZoomableImageView.this.mMidY == 0.0F) {
                    ZoomableImageView.this.mMidY = (float) ZoomableImageView.this.getHeight() / 2.0F;
                }
                ZoomableImageView.this.mCurrentMatrix.postScale
                        (scaleFactor, scaleFactor, ZoomableImageView.this.mMidX, ZoomableImageView.this.mMidY);
                ZoomableImageView.this.invalidate();
                return true;
            }

            public void onScaleEnd(ScaleGestureDetector detector) {
                super.onScaleEnd(detector);
                if (ZoomableImageView.this.mCurrentScale < 1.0F) {
                    ZoomableImageView.this.reset();
                }

                ZoomableImageView.this.checkBorder();
            }
        };
        this.mScaleDetector = new ScaleGestureDetector(this.getContext(), scaleListener);
        GestureDetector.OnGestureListener gestureListener = new GestureDetector.OnGestureListener() {
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                if (ZoomableImageView.this.mCurrentScale > 1.0F) {
                    ZoomableImageView.this.mCurrentMatrix.postTranslate(-distanceX, -distanceY);
                    ZoomableImageView.this.invalidate();
                    ZoomableImageView.this.checkBorder();
                }

                return true;
            }

            @Override
            public boolean onDown(MotionEvent e) {
                return mOnGestureListener.onDown(e);
            }

            @Override
            public void onShowPress(MotionEvent e) {
                mOnGestureListener.onShowPress(e);
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return mOnGestureListener.onSingleTapUp(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                mOnGestureListener.onLongPress(e);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return mOnGestureListener.onFling(e1, e2, velocityX, velocityY);
            }
        };
        this.mGestureDetector = new GestureDetector(this.getContext(), gestureListener);
    }

    private void checkBorder() {
        RectF rectF = this.getDisplayRect(this.mCurrentMatrix);
        boolean reset = false;
        float dx = 0.0F;
        float dy = 0.0F;
        if (rectF.left > 0.0F) {
            dx = (float) this.getLeft() - rectF.left;
            reset = true;
        }

        if (rectF.top > 0.0F) {
            dy = (float) this.getTop() - rectF.top;
            reset = true;
        }

        if (rectF.right < (float) this.getRight()) {
            dx = (float) this.getRight() - rectF.right;
            reset = true;
        }

        if (rectF.bottom < (float) this.getHeight()) {
            dy = (float) this.getHeight() - rectF.bottom;
            reset = true;
        }

        if (reset) {
            this.mCurrentMatrix.postTranslate(dx, dy);
            this.invalidate();
        }

    }

    private RectF getDisplayRect(Matrix matrix) {
        RectF rectF = new RectF((float) this.getLeft(), (float) this.getTop(), (float) this.getRight(), (float) this.getBottom());
        matrix.mapRect(rectF);
        return rectF;
    }

    public void setImageURI(Uri uri) {
        this.reset();
        super.setImageURI(uri);
    }

    public void setImageBitmap(Bitmap bm) {
        this.reset();
        super.setImageBitmap(bm);
    }

    protected void onDraw(Canvas canvas) {
        int saveCount = canvas.save();
        canvas.concat(this.mCurrentMatrix);
        super.onDraw(canvas);
        canvas.restoreToCount(saveCount);
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.mScaleDetector.onTouchEvent(event);
        if (!this.mScaleDetector.isInProgress()) {
            this.mGestureDetector.onTouchEvent(event);
        }

        return true;
    }

    private void reset() {
        this.mCurrentMatrix.reset();
        this.mCurrentScale = 1.0F;
        this.invalidate();
    }

    public void setOnGestureListener(GestureDetector.OnGestureListener listener) {
        this.mOnGestureListener = listener;
    }


}
