package com.jisucloud.clawler.regagent.service.impl.kt

import com.jisucloud.clawler.regagent.service.PapaSpider
import com.jisucloud.clawler.regagent.util.OCRDecode
import me.kagura.JJsoup
import org.jsoup.Connection
import org.springframework.stereotype.Component
import java.util.*
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.UsePapaSpider

@UsePapaSpider
class BoJinDaiService : PapaSpider {
	
	override fun getTestTelephones() : Set<String> {
		return Sets.newHashSet("13261165342", "18210538513");
	}
	
    override fun tags() = arrayOf("理财", "P2P", "借贷")

    override fun home() = "bjdp2p.com"

    override fun message() = "博金贷(www.bjdp2p.com)互联网金融服务平台,由博能控股集团,江西省投资集团,大成国资,南治资产,南昌市小额贷款公司协会等共同出资组建并获得中国网贷平台。"

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