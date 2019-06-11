package com.jisucloud.clawler.regagent.service.impl.kt

import com.jisucloud.clawler.regagent.service.PapaSpider
import com.jisucloud.clawler.regagent.util.OCRDecode
import me.kagura.JJsoup
import org.jsoup.Connection
import org.springframework.stereotype.Component
import java.util.*

@Component
class BoJinDaiService : PapaSpider {
    override fun tags() = arrayOf("理财")

    override fun home() = "bjdp2p.com"

    override fun message() = "黄金钱包是领先风投软银中国投资的互联网实物黄金投资、消费平台，为普通消费者和投资者提供“低价买黄金”、“黄金商城”和“黄金回收”等一站式服务，价格实时直通上海黄金交易所，最低买入或卖出单位为1克"

    override fun platform() = "bjdp2p"

    override fun platformName() = "博金贷"

    override fun checkTelephone(account: String): Boolean {
        var isReg = false
        for (i in 1..3) {
            try {
                val session = JJsoup.newSession()
                session.connect("https://www.bjdp2p.com/retrieve.page").execute()
                val bytes = session.connect("https://www.bjdp2p.com/rndCode.page?timestamp=${Date().time}").execute().bodyAsBytes()
                val imageCode = OCRDecode.decodeImageCode(bytes)
                val body = session.connect("https://www.bjdp2p.com/vaileRetrieve.page")
                        .data("phone", account)
                        .data("yzm", imageCode)
                        .method(Connection.Method.POST)
                        .ignoreContentType(true)
                        .execute().body()
                //{"success":false,"msg":"手机号不存在","obj":null,"url":"","additionalData":""}
                //{"success":false,"msg":"输入验证码错误","obj":null,"url":"","additionalData":""}
                if (body.contains("输入验证码错误")) {
                    continue
                }
                isReg = !body.contains("手机号不存在")
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

//fun main() {
//    println(BoJinDaiService().checkTelephone("13261165342"))
//}