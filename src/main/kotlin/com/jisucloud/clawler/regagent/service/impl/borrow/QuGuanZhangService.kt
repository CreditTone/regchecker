package com.jisucloud.clawler.regagent.service.impl.kt

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider
import com.jisucloud.clawler.regagent.util.JJsoupUtil
import org.jsoup.Connection
import org.springframework.stereotype.Component
import com.jisucloud.clawler.regagent.service.UsePapaSpider

@UsePapaSpider
class QuGuanZhangService : PapaSpider {
    override fun tags() = arrayOf("借贷" , "p2p");

    override fun home(): String = "finsphere.cn"

    override fun message(): String {
        return "趣管账是一款专为工薪阶层打造，用手机借贷提供分期消费、小额借款的移动互联网信贷产品，其宗旨是为20-45周岁的人群提供便捷迅速的金融信贷服务。国内首批利用大数据，人工智能实现风控审核的信贷服务平台。"

    }

    override fun platform(): String {
        return "QuGuanZhang"
    }

    override fun platformName(): String {
        return "趣管账"
    }

    override fun checkTelephone(account: String): Boolean {
        try {
            val session = JJsoupUtil.newProxySession()
            val response = session.connect("https://www.finsphere.cn/abook/data/ws/rest/user/login")
                    .requestBody("""
                        {
                            "mobileNo": "$account",
                            "deviceId": "163b9a2f-fe31-3641-8e6b-dcb95069849c",
                            "password": "670B14728AD9902AECBA32E22FA4F6BD",
                            "imei": "AC4CC966B434697952CEC6ED0C28EC66",
                            "needToken": false,
                            "appVerison": "3.3.0",
                            "channel": "A0004",
                            "loginKind": "normal"
                        }
                    """.trimIndent())
                    .method(Connection.Method.POST)
                    .header("Content-Type", "application/json; charset=utf-8")
                    .execute()
            val body = response.body()
            //{"resCode":"1","resMsg":"调用成功!","data":{"operationResult":false,"displayInfo":"你的手机号暂未注册，请用验证码直接登录注册","displayLevel":"2"}}
            println(platformName() + "官网接口返回：" + body)
            return !body.contains("你的手机号暂未注册")
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

//fun main(args: Array<String>) {
//    val checkTelephone = QuGuanZhangService().checkTelephone("13261165342")
//    println(checkTelephone)
//}