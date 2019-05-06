package com.jisucloud.clawler.regagent.service.impl.kt

import com.jisucloud.clawler.regagent.service.PapaSpider
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.springframework.stereotype.Component

@Component
class JieQianHuaBaService : PapaSpider {
    //https://sj.qq.com/myapp/detail.htm?apkName=com.rybring.huabei

    override fun message(): String {
        return "借钱花吧app，提高贷款机构的放款效率，高效满足借款人即时的借钱需求。想借就借呗想花就花呗，想有钱花分期贷款，都在借钱花APP"

    }

    override fun platform(): String {
        return "JieQianHuaBa"
    }

    override fun platformName(): String {
        return "借钱花吧"
    }

    override fun checkTelephone(account: String): Boolean {
        try {
            val response = Jsoup.connect("http://www.casheasy.cn:8082/login?username=$account&password=j9gIeoM%2BIU9%2BySW3C8hhPA%3D%3D")
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .execute()
            //{"body":null,"header":{"requestDate":"uQNfetqztYz9W8ee3yPrNQ\u003d\u003d","requestTime":"Vk0jgvEesOQ\u003d","requestId":null,"respCode":"84AwJuHl8ac\u003d","respMsg":"7ikiK/ueAXJ0F/Kl0m+5CFrwNKoE4fnA"}}
            val body = response.body()
            System.err.println(body)
            println(platformName() + "官网接口返回：" + body)
            return !body.contains("7ikiK/ueAXJ0F/Kl0m+5CFrwNKoE4fnA")
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
//    val checkTelephone = JieQianHuaBaService().checkTelephone("13261165342")
//    println(checkTelephone)
//}