package com.liuqi.screenqueen.ui.beauty

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.liuqi.screenqueen.R
/**
 * @author liuqi 2017/9/2 0002
 * @Package com.liuqi.screenqueen.ui.beauty.BeautyManageActivity
 * @Title: BeautyManageActivity
 * @Description: (function)
 * Copyright (c) liuqi owner
 * Create DateTime: 2017/9/2 0002
 */
class BeautyManageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beauty_manage)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
