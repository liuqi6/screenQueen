package com.liuqi.screenqueen.domin.network


import com.liuqi.screenqueen.domin.model.BeautyPage
import com.liuqi.screenqueen.getHtml
import com.liuqi.screenqueen.log
import org.jsoup.Jsoup

/**
 * @author liuqi 2017/9/2 0002
 * @Package com.liuqi.screenqueen.domin.network.GirlSource
 * @Title: GirlSource
 * @Description: (function)
 * Copyright (c) liuqi owner
 * Create DateTime: 2017/9/2 0002
 */
class GirlSource() {
    fun obtain(url: String): BeautyPage {
        val html = getHtml(url)
        val doc = Jsoup.parse(html)
        val page = BeautyPage("", "", "");
        var img = ""
        var linkpre = ""
        var linknext = ""
        log("html---->" + html)
        log("url---->" + url)
        val elements = doc.select("div.con")
        for (element in elements) {
            img = element.select("img").attr("src")
            val links = element.select("a")
            for (a in links) {
                if (a.select("span").text().contains("下一页")) {
                    linknext = a.attr("href")
                }
                if (a.select("i").text().contains("上一页")) {
                    linkpre = a.attr("href")
                }
            }
            page.pre = linkpre;
            page.current = img;
            page.next = linknext;
        }
        return page
    }

}