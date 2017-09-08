package com.liuqi.screenqueen.domin.network

import com.liuqi.screenqueen.domin.model.Cover
import com.liuqi.screenqueen.getHtml
import org.jsoup.Jsoup
import java.util.*

/**
 * @Package com.liuqi.screenqueen.domin.network
 * @Title:  BeautySource
 * @Description:   (function)
 * Copyright (c) liuqi owner
 * Create DateTime: 2017/9/2 0002
 * @author liuqi 2017/9/2 0002
 */
class BeautySource() {
    fun obtain(url: String, currentPage: Int): ArrayList<Cover> {
        var url1 = url
        if (1 == currentPage) {
            url1 = url + "?t=h"
        } else {
            url1 = url + "/page/" + currentPage + "?t=h"
        }
        val list = ArrayList<Cover>()

        val html = getHtml(url1)
        //log(html)
        val doc = Jsoup.parse(html)

        val elements = doc.select("div.list-box")

        for (element in elements) {
            val coverUrl = if (element.select("img").attr("src").contains("http")) {
                element.select("img").attr("src")
            } else {
                element.select("img").attr("src")
            }

            val title = element.select("a").attr("title")
            val link = element.select("a").attr("href")

            val cover = Cover(coverUrl, title, link)
            list.add(cover)
        }

        return list
    }
}