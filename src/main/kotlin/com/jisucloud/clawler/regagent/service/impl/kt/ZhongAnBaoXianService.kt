package com.jisucloud.clawler.regagent.service.impl.kt

import com.jisucloud.clawler.regagent.service.PapaSpider
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.springframework.stereotype.Component

@Component
class ZhongAnBaoXianService : PapaSpider {
    //https://www.wandoujia.com/apps/com.zhongan.insurance/history

    override fun message(): String {
        return "众安app，致力于给所有用户提供更放心的保障！众安会帮您降低家庭意外风险，会帮您降低看病的医疗费用，会给您提供各种必要的人性金融保险服务！"

    }

    override fun platform(): String {
        return "ZhongAnBaoXian"
    }

    override fun platformName(): String {
        return "众安保险"
    }

    override fun checkTelephone(account: String): Boolean {
        try {
            //此接口为老版本v1.1.1接口
            val response = Jsoup.connect("https://app.zhongan.com/za-clare/app/user/sendCaptcha")
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .requestBody("""
                        {"phoneNo":"$account","type":"3"}
                    """.trimIndent())
                    .header("Content-type","application/json;charset=utf-8")
                    .execute()
            val body = response.body()
            //{"returnMsg":"账号不存在","returnCode":"52"}
            println(platformName() + "官网接口返回：" + body)
            return !body.contains("账号不存在")
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

}

//fun main(args: Array<String>) {
//    val checkTelephone = ZhongAnBaoXianService().checkTelephone("13261165342")
//    println(checkTelephone)
//}