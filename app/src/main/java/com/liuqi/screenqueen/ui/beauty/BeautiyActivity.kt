package com.liuqi.screenqueen.ui.beauty

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.liuqi.screenqueen.R
import com.liuqi.screenqueen.domin.model.Cover
import com.liuqi.screenqueen.domin.network.BeautySource
import com.liuqi.screenqueen.ui.beauty.adapter.CoverAdapter
import kotlinx.android.synthetic.main.activity_beautiy.*
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.util.*

class BeautiyActivity : AppCompatActivity() {
    companion object {
        val AIM_URL = "http://www.4600m.net/fenlei/tupian/meinv"
    }

    var mData = ArrayList<Cover>()
    lateinit var adapter: CoverAdapter
    var currentPage = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beautiy)
        initView()
    }

    private fun initView() {
        beautyList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView?.canScrollVertically(1)!!) {
                    ++currentPage
                    load()
                }
            }
        })

        beautyList.layoutManager = GridLayoutManager(this, 2)
        adapter = CoverAdapter { _: View, i: Int -> jump2Detail(i) }
        beautyList.adapter = adapter

        beautyRefresh.setOnRefreshListener {
            currentPage = 1
            load()
        }
        beautyRefresh.post { beautyRefresh.isRefreshing = true }
        load()
    }

    /**
     * click event to detail activity
     */
    private fun jump2Detail(position: Int) {
        var intent = Intent(this, BeautyDetailActivity().javaClass)

        intent.putExtra(BeautyDetailActivity.INTENT_COVER_URL, mData[position].coverUrl)
        intent.putExtra(BeautyDetailActivity.INTENT_URL, mData[position].link)
        intent.putExtra(BeautyDetailActivity.INTENT_TITLE, mData[position].title)
        startActivity(intent)
    }

    private fun load() {
        async() {
            val data = BeautySource().obtain(AIM_URL, currentPage)

            uiThread {
                mData = data
                adapter.refreshData(data)
                beautyRefresh.isRefreshing = false
            }
        }
    }


}
