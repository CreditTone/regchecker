package com.jisucloud.clawler.regagent.service

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONPath
import com.jisucloud.clawler.regagent.entity.Result
import com.jisucloud.clawler.regagent.util.JJsoupUtil
import com.jisucloud.clawler.regagent.util.StaticValue
import com.jisucloud.clawler.regagent.util.getRegexMatch
import org.jsoup.Connection

/**
 * reg007单独处理
 */
class Reg007Service {

    fun doCheckTelephone(account: String): MutableList<Any> {
        try {
            val session = JJsoupUtil.newProxySession()
            session.cookies(StaticValue.REG007_COOKIES)

            val document = session.connect("https://www.reg007.com/search?q=$account").execute().body()
            val h = Regex("""var h="([a-z0-9]+)";""").find(document).run { this!!.groupValues[1] }
            var i = Regex("""var i=(\d+)""").find(document).run { this!!.groupValues[1].toInt() }

            var relustList = mutableListOf<Any>()

            for (t in 0..31) {
                session.connect("https://www.reg007.com/search/ajax")
                        .data(
                                mapOf(
                                        "q" to "$account",
                                        "h" to "$h",
                                        "i" to "$i",
                                        "t" to "$t"
                                )
                        )
                        .method(Connection.Method.POST)
                        .header("X-Requested-With", "XMLHttpRequest")
                        .header("Origin", "https://www.reg007.com")
                        .header("Host", "www.reg007.com")
                        .ignoreContentType(true)
                        .timeout(1000 * 50)
                        .execute().run {
                            val body = this.body()
                            val isInt = JSONPath.read(this.body(), "$.data") is Int
                            if (!isInt) {
                                val size = JSONPath.read(this.body(), "$.data.length()")
                                size?.run {
                                    i += this as Int
                                    for (x in 0..(size as Int - 1)) {
                                        val name = JSONPath.read(body, "$.data[$x].name") as String
                                        val desc = JSONPath.read(body, "$.data[$x].desc") as String
                                        var domain = JSONPath.read(body, "$.data[$x].home") as String
                                        domain = domain.getRegexMatch("""http[s]?://([A-Za-z0-9.:-]*)""")
                                        relustList.add(Result(desc, domain, domain, name, false, true, null, null))
                                    }
                                }
                            }
                        }
            }
            println("手机号:$account 在reg007检测返回：${JSON.toJSONString(relustList)}")
            return relustList
        } catch (e: Exception) {
            println("Reg007 检测$account 出现异常：${e.localizedMessage}")
        }
        return mutableListOf()
    }

}