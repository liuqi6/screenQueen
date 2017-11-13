package com.liuqi.screenqueen.stamp

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import com.liuqi.screenqueen.R

/**
 * @author liuqi 2017/9/2 0002
 * @Package com.liuqi.screenqueen.stamp.StampView
 * @Title: StampView
 * @Description: (function)
 * Copyright (c) liuqi owner
 * Create DateTime: 2017/11/13 0002
 */
class StampView : View {
    private var mStampString: String = "签"
    private var mStampFontColor = Color.RED
    private var mStampBg = Color.TRANSPARENT
    private var mStampShape: Drawable? = null
    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */

    private var mTextPaint: TextPaint? = null
    private var mBgPaint: TextPaint? = null
    private var mTextWidth: Float = 0.toFloat()
    private var mTextHeight: Float = 0.toFloat()


    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
                attrs, R.styleable.StampView, defStyle, 0)

        mStampString = a.getString(
                R.styleable.StampView_stamp_text)
        mStampFontColor = a.getColor(
                R.styleable.StampView_stamp_font_color,
                mStampFontColor)
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mStampBg = a.getInt(
                R.styleable.StampView_stamp_shape,
                mStampBg)

        if (a.hasValue(R.styleable.StampView_stamp_bg)) {
            mStampShape = a.getDrawable(
                    R.styleable.StampView_stamp_bg)
        }

        a.recycle()

        // Set up a default TextPaint object
        mTextPaint = TextPaint()
        mTextPaint!!.flags = Paint.ANTI_ALIAS_FLAG
        mTextPaint!!.textAlign = Paint.Align.LEFT

    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom

        // Draw the text.
        canvas.drawText(""!!,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + (contentHeight + mTextHeight) / 2,
                mTextPaint!!)

        // Draw the example drawable on top of the text.
        if (mStampShape != null) {
            mStampShape!!.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight)
            mStampShape!!.draw(canvas)
        }
    }
}
