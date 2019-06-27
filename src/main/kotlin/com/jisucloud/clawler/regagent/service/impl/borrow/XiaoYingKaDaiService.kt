package com.jisucloud.clawler.regagent.service.impl.kt

import com.jisucloud.clawler.regagent.service.PapaSpider
import com.jisucloud.clawler.regagent.util.JJsoupUtil
import com.jisucloud.clawler.regagent.util.md5DigestAsHex
import org.springframework.stereotype.Component
import java.util.*
import kotlin.random.Random
import kotlin.random.nextULong
import com.jisucloud.clawler.regagent.service.UsePapaSpider

@UsePapaSpider
class XiaoYingKaDaiService : PapaSpider {
    override fun tags() = arrayOf("借贷" , "p2p");
    //https://sj.qq.com/myapp/detail.htm?apkName=com.xiaoying.cardloan
    override fun home() = "xiaoying.com"

    override fun message() = "小赢卡贷是“美国纽交所上市公司”、“NBA中国官方合作伙伴”小赢科技旗下，为帮助广大借款用户维护信用而生的借贷服务平台，年化利率低至9.98%、还款周期灵活等产品特点，能有效平衡还款压力。小赢卡贷帮助借款用户解决了借款难的问题，具有较大的社会效益。"

    override fun platform() = "XiaoYingKaDai"

    override fun platformName() = "小赢卡贷"

    override fun checkEmail(account: String) = false

    override fun getFields() = null

    override fun checkTelephone(account: String): Boolean {
        try {
            val session = JJsoupUtil.newProxySession()
            val macid = Random.nextULong().toString().subSequence(0..15)
            val md5key = "xiaoyingkadai"
            val kvs = "${md5key}channel=10000298&language=zh-Hans-CN&mac_id=$macid&mobile=$account&os=android&os_version=5.1.1&soft_version=2.1.1&ut=${Date().time}$md5key"
            val sign2 = kvs.md5DigestAsHex()

            //{"errcode":-2000,"errstr":"手机号不存在，请重新输入","errDetail":"","jsbridge":null,"data":{"code":0}}
            return session.connect("https://cardloan.xiaoying.com/2.1/user/find_pwd_precheck?${kvs.replace(md5key, "")}&sign=$sign2")
                    .execute().run {
                        val body = body()
                        println(platformName() + "官网接口返回：" + body)
                        !body.contains("手机号不存在")
                    }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }


}

//fun main() {
//    var checkTelephone = XiaoYingKaDaiService().checkTelephone("18763623587")
//    println(checkTelephone)
//}