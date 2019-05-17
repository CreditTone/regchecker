package com.jisucloud.clawler.regagent.service.impl.kt

import com.alibaba.fastjson.JSONPath
import com.jisucloud.clawler.regagent.service.PapaSpider
import com.jisucloud.clawler.regagent.util.JJsoupUtil
import org.springframework.stereotype.Component

@Component
class DaZhiHuiService : PapaSpider {
    override fun tags(): Map<String, Array<String>> {
        return mapOf(
                "金融理财" to arrayOf("炒股")
        )
    }

    override fun home(): String = "gw.com.cn"
    //https://sj.qq.com/myapp/detail.htm?apkName=com.android.dazhihui

    override fun message(): String {
        return "大智慧-中国90%的投资者选择用大智慧炒股软件,提供沪深港美股实时高速行情，Level-2行情，财经热点新闻及全方面投资资讯。"

    }

    override fun platform(): String {
        return "DaZhiHui"
    }

    override fun platformName(): String {
        return "大智慧"
    }

    override fun checkTelephone(account: String): Boolean {
        try {

            val session = JJsoupUtil.newProxySession()

            session.connect("https://i.gw.com.cn/UserCenter/page/account/forgetPass")
                    .validateTLSCertificates(false)
                    .execute()
            val response = session.connect("https://i.gw.com.cn/UserCenter/account/mobile/$account")
                    .header("X-Requested-Type", getToken(session.cookies()))
                    .validateTLSCertificates(false)
                    .execute()
            //未注册返回：{"code":"200","message":null,"data":null}
            //已注册返回：{"code":"200","message":null,"data":"d**********9"}
            println(response.body())
            return JSONPath.read(response.body(), "$.data") != null
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


    /**
     * 获取请求头"X-Requested-Type"的值
     * 具体参考https://i.gw.com.cn/UserCenter/web/js/pcaccount.js?v=1.0.26
     * 第677行account.util.alg.auth()方法的实现
     */
    fun getToken(session: Map<String, String>): String {
        var tokenG = session.get("_gpd") + ""
        var tokenC = session.get("_cis") + ""
        var tokenS = session.get("_sad") + ""
        var oathG = "${tokenG.get(tokenG.length - 2)}${tokenG.get(3)}${tokenG.get(6)}"
        var oathC = "${tokenC[tokenC.length - 10]}${tokenC.get(2)}${tokenC.get(11)}"
        var oathS = "${tokenS.get(2)}${tokenS.get(tokenS.length - 10)}${tokenS.get(tokenS.length - 1)}"
        var oath = oathG + oathC + oathS
        return oath
    }

}

//fun main(args: Array<String>) {
//    var checkTelephone = DaZhiHuiService().checkTelephone("18763623587")
//    println(checkTelephone)
//    checkTelephone = DaZhiHuiService().checkTelephone("18210538513")
//    println(checkTelephone)
//}