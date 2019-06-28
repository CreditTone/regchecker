package com.jisucloud.clawler.regagent.service.impl.kt

import com.alibaba.fastjson.JSONPath
import com.jisucloud.clawler.regagent.service.PapaSpider
import com.jisucloud.clawler.regagent.util.JJsoupUtil
import org.jsoup.Connection
import org.springframework.stereotype.Component
import com.jisucloud.clawler.regagent.service.UsePapaSpider
import com.google.common.collect.Sets

@UsePapaSpider
class NanXingSiRenYiShengService : PapaSpider {
    override fun tags() = arrayOf("医疗");
    override fun home() = "ranknowcn.com"
    //https://sj.qq.com/myapp/detail.htm?apkName=com.medapp.man
	
	override fun getTestTelephones() : Set<String> {
		return Sets.newHashSet("13261165342", "18210538513" , "18210538510");
	}

    override fun message(): String {
        return "男性私人医生是一款针对男生生理健康及相关专科类病症，提供免费在线咨询的健康类服务软件。集合上千家医院的在职医生和资源，自动分配所在地就近医院医生为您提供健康指导，免去挂号难，排队久的困扰，提问流程简单便捷，让您随时随地享受VIP健康咨询服务。(建议允许定位以便能准确分配到所在地医院)。"

    }

    override fun platform(): String {
        return "NanXingSiRenYiSheng"
    }

    override fun platformName(): String {
        return "男性私人医生"
    }

    override fun checkTelephone(account: String): Boolean {
        try {
            val session = JJsoupUtil.newProxySession()
            val response = session.connect("http://new.medapp.ranknowcn.com/api/m.php?action=login&version=3.0")
                    .method(Connection.Method.POST)
                    .requestBody("password=oooooi&source=tencent&token=5cd0f47bdc43e&appid=3&switchType=0&deviceid=5cd0f47bdc43e&os=android&vocde=&age=unknown&imei=460383127194006&username=${account}&version=3.19.0428.1&phonemodel=Nexus+5&mobileTel=&")
                    .execute()
            //{"result":false,"msg":"此用户不存在"}
            val body = response.body()
            val msg = JSONPath.read(body, "$.msg") as String
            println(platformName() + "官网接口返回：" + body)
            println(platformName() + "官网接口返回：" + msg)
            return !msg.contains("用户不存在")
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
//    val checkTelephone = NanXingSiRenYiShengService().checkTelephone("13261165342")
//    println(checkTelephone)
//}