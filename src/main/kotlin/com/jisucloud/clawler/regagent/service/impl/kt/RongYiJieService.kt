package com.jisucloud.clawler.regagent.service.impl.kt

import com.jisucloud.clawler.regagent.service.PapaSpider
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.springframework.stereotype.Component

@Component
class RongYiJieService : PapaSpider {
    //https://sj.qq.com/myapp/detail.htm?apkName=com.rybring

    override fun message(): String {
        return "容易借APP搜罗新全的小额极速贷、大额分期贷、新口子、。为你推荐更低利率，更快放款的贷款口子。下款率高达90%，快至30分钟放款。"

    }

    override fun platform(): String {
        return "RongYiJie"
    }

    override fun platformName(): String {
        return "容易借"
    }

    override fun checkTelephone(account: String): Boolean {
        try {
            val response = Jsoup.connect("http://www.casheasy.cn:8082/login?username=$account&password=B7w3PkTyPU8lY8%2BwmaYjkA%3D%3D")
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .execute()
            val body = response.body()
            System.err.println(body)
            //{"body":null,"header":{"requestDate":"uQNfetqztYz9W8ee3yPrNQ\u003d\u003d","requestTime":"lOI4yzNA2RM\u003d","requestId":null,"respCode":"84AwJuHl8ac\u003d","respMsg":"7ikiK/ueAXJ0F/Kl0m+5CFrwNKoE4fnA"}}
            //7ikiK/ueAXJ0F/Kl0m+5CFrwNKoE4fnA          未注册
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
//    val checkTelephone = RongYiJieService().checkTelephone("13261165342")
//    println(checkTelephone)
//}