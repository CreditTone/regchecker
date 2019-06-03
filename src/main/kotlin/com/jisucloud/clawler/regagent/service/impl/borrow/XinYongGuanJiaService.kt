package com.jisucloud.clawler.regagent.service.impl.kt

import com.jisucloud.clawler.regagent.service.PapaSpider
import com.jisucloud.clawler.regagent.util.JJsoupUtil
import org.jsoup.Connection
import org.springframework.stereotype.Component

@Component
class XinYongGuanJiaService : PapaSpider {
    override fun tags(): Map<String, Array<String>> {
        return mapOf(
                "金融理财" to arrayOf("借贷")
        )
    }

    override fun home(): String = "51nbapi.com"
    //https://sj.qq.com/myapp/detail.htm?apkName=com.greate.myapplication

    override fun message(): String {
        return "信用管家是借钱贷款APP，凭身份证、信用分或信用卡等借钱贷款。借钱贷款，就找信用管家借钱！"

    }

    override fun platform(): String {
        return "XinYongGuanJia"
    }

    override fun platformName(): String {
        return "信用管家"
    }

    override fun checkTelephone(account: String): Boolean {
        try {
            val session = JJsoupUtil.newProxySession()
            val response = session.connect("https://api.51nbapi.com/mapi/cspuser/phone_user/login.json")
                    .requestBody("deviceId=865166021433753&password=356151536&appVersion=4.5.8&location=%E5%8C%97%E4%BA%AC%E5%B8%82&phoneType=oppo%20r9%20plusm%20a&phoneVersion=5.1.1&appChannel=yingyb&phone=$account&phoneSystem=A&appName=zhengxindaikuan")
                    .method(Connection.Method.POST)
                    .execute()
            //{"result":{"message":"对不起，当前手机号还未注册！","appName":"mapi","status":"error","code":"E2017K000000013","success":"false"}}
            val body = response.body()
            println(platformName() + "官网接口返回：" + body)
            return !body.contains("当前手机号还未注册")
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
//    val checkTelephone = XinYongGuanJiaService().checkTelephone("13261165342")
//    println(checkTelephone)
//}