package com.liuqi.screenqueen.ui.beauty

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import com.liuqi.screenqueen.R
import com.liuqi.screenqueen.domin.model.BeautyPage
import com.liuqi.screenqueen.domin.network.GirlSource
import com.liuqi.screenqueen.domin.utils.FileUtil
import com.liuqi.screenqueen.toast
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_beauty_detail.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.io.FileOutputStream


class BeautyDetailActivity : AppCompatActivity() {
    lateinit var url: String
    lateinit var girlDetail: BeautyPage
    lateinit var mGestureDetector: GestureDetector
    lateinit var mScaleDetector: ScaleGestureDetector
    private val FLING_MIN_DISTANCE = 50
    private val FLING_MIN_VELOCITY = 0
    private var mCurrentMatrix: Matrix? = null

    companion object {
        val INTENT_COVER_URL = "cover"
        val INTENT_URL = "url"
        val INTENT_TITLE = "title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beauty_detail)
        mGestureDetector = GestureDetector(this, onGestureListener);
        init();
    }

    private fun init() {
        val coverUrl = intent.getStringExtra(INTENT_COVER_URL)
        var title = intent.getStringExtra(INTENT_TITLE)
        url = intent.getStringExtra(INTENT_URL)
        toolbar.title = title
        detailImage.setOnClickListener {
            url = girlDetail.next
            load2()
        }

        detailImage.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View): Boolean {
                download(girlDetail.current)
                return true
            }
        })
        detailImage.setOnTouchListener { v, event -> mGestureDetector.onTouchEvent(event) }
        this.mCurrentMatrix = Matrix()
        detailImage.setOnGestureListener(onGestureListener)
    }

    override fun onResume() {
        super.onResume()
        load2()
    }

    private fun load2() = async() {
        girlDetail = GirlSource().obtain(url)
        val data = girlDetail as BeautyPage

        uiThread {
            Picasso.with(this@BeautyDetailActivity).load(girlDetail.current).centerInside().into(detailImage)
        }
    }

    private fun download(url: String) {
        //Picasso下载
        Picasso.with(this).load(url).placeholder(R.drawable.abc_ic_star_black_48dp).error(R.drawable.ic_error).into(object : Target {
            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                val imageName = System.currentTimeMillis().toString() + ".jpg"

                val dcimFile = FileUtil
                        .getOutFile(FileUtil.PATH_PHOTOGRAPH, imageName)

                Log.i("BeautyDetailActivity", "bitmap=" + bitmap)
                var ostream: FileOutputStream? = null
                try {
                    ostream = FileOutputStream(dcimFile)
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream)
                    ostream!!.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                toast("图片下载至:" + dcimFile!!)

            }

            override fun onBitmapFailed(drawable: Drawable) {
                toast("图片下载失败:")
            }

            override fun onPrepareLoad(placeHolderDrawable: Drawable) {

            }
        })

    }

    internal var onGestureListener: GestureDetector.OnGestureListener = object : GestureDetector.OnGestureListener {
        override fun onDown(e: MotionEvent): Boolean {
            return false
        }

        override fun onShowPress(e: MotionEvent) {

        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            url = girlDetail.next
            load2()
            return false
        }

        override fun onScroll(e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
            return false
        }

        override fun onLongPress(e: MotionEvent) {
            download(girlDetail.current)
        }

        override fun onFling(e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float): Boolean {
            if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                // Fling left
                url = girlDetail.pre
                load2()
            } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
                    && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
                // Fling right
                url = girlDetail.next
                load2()
            }
            return false;
        }
    }

}
