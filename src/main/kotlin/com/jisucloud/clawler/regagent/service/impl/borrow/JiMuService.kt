package com.jisucloud.clawler.regagent.service.impl.kt

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider
import com.jisucloud.clawler.regagent.util.OCRDecode
import me.kagura.JJsoup
import org.jsoup.Connection
import org.springframework.stereotype.Component
import java.util.*
import com.jisucloud.clawler.regagent.service.UsePapaSpider

@UsePapaSpider
class JiMuService : PapaSpider {
    override fun tags() = arrayOf("理财", "P2P", "借贷")

    override fun home() = "jimu.com"

    override fun message() = "积木盒子是国内领先的网络借贷信息中介平台，专注于运用互联网和技术手段打通金融服务中存在的痛点，为个人、微型企业提供稳健、高效、轻松的借贷撮合服务。"

    override fun platform() = "jimu"

    override fun platformName() = "积木盒子"

    override fun checkTelephone(account: String): Boolean {
        var isReg = false
        for (i in 1..3) {
            try {
                val session = JJsoup.newSession()
                session.connect("https://www.jimu.com/User/Forgot/Password").execute()
                val bytes = session.connect("https://www.jimu.com/CaptchaImage?once=${Date().time}").execute().bodyAsBytes()
                val imageCode = OCRDecode.decodeImageCode(bytes)
                val body = session.connect("https://www.jimu.com/User/Forgot/Password")
                        .data("mobile", account)
                        .data("CaptchaInputText", imageCode)
                        .method(Connection.Method.POST)
                        .ignoreContentType(true)
                        .execute().body()
                if (body.contains("验证码错误")) {
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
	
	override fun getTestTelephones() : Set<String> {
		return Sets.newHashSet("13261165342", "18210538513");
	}

}

//fun main() {
//    println(JiMuService().checkTelephone("13261165342"))
//}