package com.jisucloud.clawler.regagent.service.impl.kt

import com.jisucloud.clawler.regagent.service.PapaSpider
import com.jisucloud.clawler.regagent.util.OCRDecode
import me.kagura.JJsoup
import org.jsoup.Connection
import org.springframework.stereotype.Component

@Component
class LianZiDaiService : PapaSpider {
    override fun tags() = arrayOf("理财", "P2P", "借贷")

    override fun home() = "lianzidai.com"

    override fun message() = "连资贷是一家注重风险防控、致力诚信经营的互联网金融平台，创立于2014年9月。借助移动支付和大数据等先进互联网技术，满足借款人和出借人双方需求，实现多方共赢。"

    override fun platform() = "LianZiDai"

    override fun platformName() = "连资贷"

    override fun checkTelephone(account: String): Boolean {
        var isReg = false
        for (i in 1..3) {
            try {
                val session = JJsoup.newSession()
                session.connect("https://www.lianzidai.com/user/getpwd.html?showType=2").execute()
                val bytes = session.connect("https://www.lianzidai.com/validimg.html?t=${Math.random()}").execute().bodyAsBytes()
                val imageCode = OCRDecode.decodeImageCode(bytes)
                val body = session.connect("https://www.lianzidai.com/user/getpwd.html")
                        .data("mobilePhone", account)
                        .data("valicode", imageCode)
                        .data("getType", "2")
                        .data("step", "1")
                        .method(Connection.Method.POST)
                        .execute().body()
                if (body.contains("验证码错误")) {
                    continue
                }
                isReg = !body.contains("该手机号不存在")
                break
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return isReg
    }

    override fun checkEmail(account: String) = false

    override fun getFields() = null

}

fun main() {
    println(LianZiDaiService().checkTelephone("13261165342"))
}