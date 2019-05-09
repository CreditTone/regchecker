package com.jisucloud.clawler.regagent.service.impl.kt

import com.jisucloud.clawler.regagent.service.PapaSpider
import com.jisucloud.clawler.regagent.util.JJsoupUtil
import org.jsoup.Connection
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESKeySpec

@Component
class HengYiDaiService : PapaSpider {
    //https://sj.qq.com/myapp/detail.htm?apkName=com.hengchang.client

    override fun message(): String {
        return "恒易贷是由北京网众共创科技有限公司推出的移动借贷产品智选APP,利用大数据、移动互联网等前沿技术，将海量丰富的各类互联网金融业务模式得以简单、直观的呈现给用户。"

    }

    override fun platform(): String {
        return "HengYiDai"
    }

    override fun platformName(): String {
        return "恒易贷"
    }

    override fun checkTelephone(account: String): Boolean {
        try {
            val jsonParams = """
                {
                    "utmCode":"302",
                    "tokenId":"AYc2dV8e1cclgcgXGAfkFHkQ0mKLb5E5uc5nJRyMCGndp_jmcnukujMIi1tX_cE2VyjkFrO7LF4s7gWoviFzPHmw80OshDDcIo-VJ-_SK4DQj5Un79IrgNCPlSfv0iuA0I-VJ-_SK4DQj5Un79IrgNCPlSfv0iuA0I-VJ-_SK4DQOo77oXk_ZXZt",
                    "userId":"",
                    "version":"2.9.9",
                    "osVersion":"android_5.1.1",
                    "userPhone":"$account",
                    "deviceCode":"358239055441820",
                    "sourceType":"3"
                }
            """.trimIndent()
            val session = JJsoupUtil.newProxySession()
            //老版本2.9.9接口，邦邦加固+DES加密
            val response = session.connect("https://hyd.hengchang6.com/hyd/services/hydUser/checkPhone")
                    .userAgent("Mozilla/5.0 (Linux; U; Android 5.1.1; zh-cn; Nexus 5 Build/LMY48M) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30")
                    .data("jsonParams", encrypt(jsonParams))
                    .method(Connection.Method.POST)
                    .execute()
            val body = decrypt(response.body())
            //{"rspCode":200,"rspMsg":"成功","showMsg":"成功","data":{"msg":"该手机号未注册，请先注册","code":"1"}}
            println(platformName() + "官网接口返回：" + body)
            return !body.contains("该手机号未注册")
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    override fun checkEmail(account: String): Boolean {
        return false
    }

    override fun getFields(): Map<String, String>? {
        return null
    }

    fun decrypt(data: String): String {
        val DES_KEY = """o9WL!惹@#8*3${'$'}D5m赢7&4(荡F*"""
        val bytes = Base64.getDecoder().decode(data.replace(Regex("[\\s]*"), ""))
        val key = SecretKeyFactory.getInstance("DES").generateSecret(DESKeySpec(DES_KEY.toByteArray()))
        val cipher = Cipher.getInstance("DES")
        cipher.init(2, key, SecureRandom())
        return String(cipher.doFinal(bytes))
    }

    fun encrypt(data: String): String {
        val DES_KEY = """o9WL!惹@#8*3${'$'}D5m赢7&4(荡F*"""
        val key = SecretKeyFactory.getInstance("DES").generateSecret(DESKeySpec(DES_KEY.toByteArray()))
        val cipher = Cipher.getInstance("DES")
        cipher.init(1, key, SecureRandom())
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.toByteArray())).replace(Regex("[\\s]*"), "")
    }

}

//fun main(args: Array<String>) {
//    val checkTelephone = HengYiDaiService().checkTelephone("13261165342")
//    println(checkTelephone)
//}