package com.jisucloud.clawler.regagent.service.impl.kt

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider
import com.jisucloud.clawler.regagent.util.JJsoupUtil
import org.jsoup.Connection
import org.springframework.stereotype.Component
import com.jisucloud.clawler.regagent.service.UsePapaSpider

@UsePapaSpider
class WeiDaiQianBaoService : PapaSpider {
    override fun tags() = arrayOf("借贷" , "p2p");
    override fun home(): String = "casheasy.cn"
    //https://sj.qq.com/myapp/detail.htm?apkName=com.rybring.weili

    override fun message(): String {
        return "微贷钱包，智能贷款推荐，为你寻找适合的网贷产品"

    }

    override fun platform(): String {
        return "WeiDaiQianBao"
    }

    override fun platformName(): String {
        return "微贷钱包"
    }

    override fun checkTelephone(account: String): Boolean {
        try {
            val session = JJsoupUtil.newProxySession()
            val response = session.connect("http://www.casheasy.cn:8082/login?username=$account&password=B7w3PkTyPU8lY8%2BwmaYjkA%3D%3D")
                    .method(Connection.Method.POST)
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
	
	override fun getTestTelephones() : Set<String> {
		return Sets.newHashSet("13261165342", "18210538513");
	}

}
