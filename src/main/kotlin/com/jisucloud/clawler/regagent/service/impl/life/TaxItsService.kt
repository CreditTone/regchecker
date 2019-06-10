package com.jisucloud.clawler.regagent.service.impl.kt

import com.alibaba.fastjson.JSONPath
import com.jisucloud.clawler.regagent.service.PapaSpider
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.util.*

@Component
class TaxItsService : PapaSpider {
    override fun tags() = arrayOf("个税", "工作");

    //https://sj.qq.com/myapp/detail.htm?apkName=cn.gov.tax.its
    override fun message() = "个人所得税APP是由国家税务总局主办，为贯彻落实党中央、国务院提出的个人所得税综合与分类相结合的税制改革要求，为保障全国自然人纳税人能够及时享受税改红利，而推出的一款APP。"

    override fun platform() = "GeRenSuoDeShui"

    override fun home() = "its.tax.sh.gov.cn"

    override fun platformName() = "个人所得税"

    override fun checkTelephone(account: String?): Boolean {
        val sbuuid = UUID.randomUUID().toString().replace("-", "").subSequence(0..16)
        return Jsoup.connect("https://mits.anhui.chinatax.gov.cn:17001/web/zh/login/zhmmdl")
                .requestBody("""
                {
                    "dlbz":"$account",
                    "mm":"NJ2jhyBb5UWIaANmTNj+Ugej5mcgl3bOpH3fajGyzk+KPydzDwWx90RnslNbneip7HpqkAGLPEHhNuwb9Bx+tU78fPMToKcFThmMGcni+jBi/Yg/+PL2qf3aX19/Jrfx34haTw4tQXcZsuIiPj0aHLXNvGuE2wOXrhTV26wPPIY=",
                    "appVersion":"1.1.8",
                    "sbuuid":"$sbuuid",
                    "sbcs":"LGE",
                    "sbczxt":"android"
                }
            """.trimIndent())
                .userAgent("Dalvik/2.1.0 (Linux; U; Android 5.1.1; Nexus 5 Build/LMY48M)")
                .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
                .ignoreContentType(true)
                .method(Connection.Method.POST)
                .execute().run {
                    //{"code":"NA_BUSINESS_VALID_ERROR_XMHMM_INCORRECT_LESS_COUNT_1001","params":[3],"message":"账号或密码错误,请重新输入,剩余3次机会","data":null,"appCodeForEx":null,"originalErrorCode":null,"rid":"957519105a1542f4821bf16d954f9dff"}
                    //{"code":"NA_BUSINESS_VALID_ERROR_XMHMM_INCORRECT_1024","params":null,"message":"账号或密码错误,请重新输入","data":null,"appCodeForEx":null,"originalErrorCode":null,"rid":"5ee3a53ed1eb47e898ebb9a3b684bfb3"}
                    //{"code":"NA_BUSINESS_VALID_ERROR_ZRRZH_LOCK_ZHMM_1005","params":null,"message":"账号或密码多次输入错误，请24小时之后再试或找回密码","data":null,"appCodeForEx":null,"originalErrorCode":null,"rid":"30284ab9966f4165a461e5de5eab83ca"}
                    //未注册时params==null
                    println("${platformName()}官网接口返回：${this.body()}")
                    JSONPath.read(this.body(), "$.message") != "账号或密码错误,请重新输入"
                }
    }

    override fun checkEmail(account: String?) = false

    override fun getFields() = null

}

//fun main() {
//    val b = TaxItsService().checkTelephone("13800138000")
//    println(b)
//}