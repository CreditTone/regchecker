package com.jisucloud.clawler.regagent.service.impl.kt

import com.jisucloud.clawler.regagent.service.PapaSpider
import me.kagura.JJsoup
import org.jsoup.Connection
import org.springframework.stereotype.Component

@Component
class PingAnXiaoDaiService : PapaSpider {
    override fun tags() = arrayOf("借贷" , "p2p");

    override fun home(): String = "pingan.com"
    //https://sj.qq.com/myapp/detail.htm?apkName=com.xiaoedai.xed
    override fun message(): String {
        return "平安小额贷对用户借款需求进行智能匹配，为用户推荐更适合的低费率、高额度放款快的贷款产品。 业务涵盖小额贷款、金融贷款、极速现金贷款、大额贷款、信用卡贷款、等信贷服务及信用卡办理。"

    }

    override fun platform(): String {
        return "PingAnXiaoDai"
    }

    override fun platformName(): String {
        return "平安小额贷"
    }

    override fun checkTelephone(account: String): Boolean {
        return JJsoup.newSession().connect("http://18.162.179.74:8080/zhita_daichao_app//app_login/pwdlogin")
                .requestBody("phone=$account&pwd=shadiaoXX&company=%E5%80%9F%E5%90%A7&oneSourceName=%E5%B9%B3%E5%AE%89%E5%B0%8F%E9%A2%9D%E8%B4%B7&twoSourceName=yingyongbao")
                .method(Connection.Method.POST)
                .execute().run {
                    val body = body()
                    println(body())
                    !body.contains("用户名不存在")
                }

    }

    override fun checkEmail(account: String): Boolean {
        return false
    }

    override fun getFields(): Map<String, String>? {
        return null
    }

}