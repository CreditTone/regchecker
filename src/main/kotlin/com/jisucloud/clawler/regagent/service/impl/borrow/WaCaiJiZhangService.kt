//package com.jisucloud.clawler.regagent.service.impl.kt
//
//import com.jisucloud.clawler.regagent.service.PapaSpider
//import com.jisucloud.clawler.regagent.util.JJsoupUtil
//import org.jsoup.Connection
//import org.springframework.stereotype.Component
//
//@Component
//class WaCaiJiZhangService : PapaSpider {
//    override fun tags(): Map<String, Array<String>> {
//        return mapOf(
//                "金融理财" to arrayOf("理财记账")
//        )
//    }
//
//    override fun home(): String = "wacai.com"
//    //https://sj.qq.com/myapp/detail.htm?apkName=com.wacai365
//
//    override fun message(): String {
//        return "挖财记账，能省钱的个人家庭财务管家，三秒记一笔，明细报表，让你知道钱花哪里了。"
//
//    }
//
//    override fun platform(): String {
//        return "WaCaiJiZhang"
//    }
//
//    override fun platformName(): String {
//        return "挖财记账"
//    }
//
//    override fun checkTelephone(account: String): Boolean {
//        try {
//            val session = JJsoupUtil.newProxySession()
//            val response = session.connect("https://user.wacai.com/resetPwdByMob_api/sendVerCode")
//                    .requestBody("""
//                    {"mob":"$account"}
//                """.trimIndent())
//                    .header("Content-Type", "application/json; charset=utf-8")
//                    .method(Connection.Method.POST)
//                    .execute()
//            //{"code":"2015","error":"操作过于频繁, 请稍候再试"}
//            //{"code":"2104","error":"手机未注册"}
//            val body = response.body()
//            System.err.println(body)
//            println(platformName() + "官网接口返回：" + body)
//            return !body.contains("手机未注册")
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return false
//        }
//
//    }
//
//    override fun checkEmail(account: String): Boolean {
//        return false
//    }
//
//    override fun getFields(): Map<String, String>? {
//        return null
//    }
//
//}
//
////fun main(args: Array<String>) {
////    val checkTelephone = WaCaiJiZhangService().checkTelephone("13261165342")
////    println(checkTelephone)
////}