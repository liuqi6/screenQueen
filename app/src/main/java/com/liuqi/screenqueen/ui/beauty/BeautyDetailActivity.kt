package com.liuqi.screenqueen.ui.beauty

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.liuqi.screenqueen.R
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
    lateinit var girlDetail: Pair<String, String>

    companion object {
        val INTENT_COVER_URL = "cover"
        val INTENT_URL = "url"
        val INTENT_TITLE = "title"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beauty_detail)
        init();
    }

    private fun init() {
        val coverUrl = intent.getStringExtra(INTENT_COVER_URL)
        var title = intent.getStringExtra(INTENT_TITLE)
        url = intent.getStringExtra(INTENT_URL)
        toolbar.title = title
        detailImage.setOnClickListener { load2() }
        detailImage.setOnLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View): Boolean {
                download(girlDetail.first)
                return true
            }
        })
    }

    override fun onResume() {
        super.onResume()
        load2()
    }

    private fun load2() = async() {
        girlDetail = GirlSource().obtain(url)
        val data = girlDetail as Pair

        uiThread {
            Picasso.with(this@BeautyDetailActivity).load(girlDetail.first).into(detailImage)
            url = girlDetail.second
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
}
