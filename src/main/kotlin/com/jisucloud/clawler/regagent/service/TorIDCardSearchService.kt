package com.jisucloud.clawler.regagent.service

import org.jsoup.Connection
import org.jsoup.Jsoup
import org.springframework.stereotype.Service
import java.net.InetSocketAddress
import java.net.Proxy

@Service
class TorIDCardSearchService {
    //http://ykeexkooowtngd54.onion/

    /**
     * @param account 手机号/座机号/身份证
     */
    fun doSearch(account: String): MutableMap<String, String> {
        return Jsoup.connect("http://ykeexkooowtngd54.onion/s.php")
                .data("search", account)
                .proxy(Proxy(Proxy.Type.SOCKS, InetSocketAddress("149.129.90.138", 9150)))
                .method(Connection.Method.POST)
                .execute()
                .run {
                    val map = mutableMapOf<String, String>()
                    this.parse().body().html().replace("<br>", "").lines().map {
                        val split = it.split("：")
                        when (split[0]) {
                            "姓名" -> map.put("name", split[1])
                            "女F男M" -> map.put("gender", split[1])
                            "身份证" -> map.put("idcard", split[1])
                            "生日" -> map.put("birthday", split[1])
                            "地址" -> map.put("address", split[1])
                            "邮编" -> map.put("zipCode", split[1])
                            "手机" -> map.put("phone", split[1])
                            "座机" -> map.put("tel", split[1])
                            "传真" -> map.put("fax", split[1])
                            "邮箱" -> map.put("email", split[1])
                            "开房时间" -> map.put("room_time", split[1])
                            "数据库编号" -> map.put("db_id", split[1])
                            else -> ""
                        }

                    }
                    return@run map
                }

    }

}