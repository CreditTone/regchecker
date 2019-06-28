package com.jisucloud.clawler.regagent.service.impl.kt

import com.jisucloud.clawler.regagent.service.PapaSpider
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.springframework.stereotype.Component
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.UsePapaSpider

@UsePapaSpider
class G_BankerService : PapaSpider {
    override fun tags() = arrayOf("理财", "黄金投资")

    override fun home() = "g-banker.com"

    override fun message() = "黄金钱包是领先风投软银中国投资的互联网实物黄金投资、消费平台，为普通消费者和投资者提供“低价买黄金”、“黄金商城”和“黄金回收”等一站式服务，价格实时直通上海黄金交易所，最低买入或卖出单位为1克"

    override fun platform() = "gbanker"

    override fun platformName() = "黄金钱包"

    override fun checkTelephone(account: String): Boolean {
        return Jsoup.connect("https://www.g-banker.com/user/updateUserLogin")
                .requestBody("{\"telephone\":\"$account\",\"password\":\"ed89w89dwe\"}")
                .header("Content-Type", "application/json;charset=UTF-8")
                .method(Connection.Method.POST)
                .ignoreContentType(true)
                .execute().body().run {
                    return@run !contains("手机号未注册")
                }

    }

    override fun checkEmail(account: String) = false

    override fun getFields() = null
	
	override fun getTestTelephones() : Set<String> {
		return Sets.newHashSet("13261165342", "18210538513");
	}

}

//fun main() {
//    println(G_BankerService().checkTelephone("13261165342"))
//}