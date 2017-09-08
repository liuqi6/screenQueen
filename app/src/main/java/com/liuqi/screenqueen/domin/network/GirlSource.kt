package com.liuqi.screenqueen.domin.network


import com.liuqi.screenqueen.getHtml
import org.jsoup.Jsoup

/**
 * @author wupanjie
 */
class GirlSource() {
    fun obtain(url: String): Pair<String, String> {
        val html = getHtml(url)
        val doc = Jsoup.parse(html)
        var img = ""
        var link = ""
        val elements = doc.select("div.con")
        for (element in elements) {
            img = element.select("img").attr("src")
            val links = element.select("a")
            for (a in links) {
                if (a.select("span").text().contains("下一页")) {
                    link = a.attr("href")
                    break
                }

            }
        }
        val pair = Pair<String, String>(img, link)
        return pair
    }

}