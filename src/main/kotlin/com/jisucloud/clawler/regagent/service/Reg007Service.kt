package com.jisucloud.clawler.regagent.service

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONPath
import com.jisucloud.clawler.regagent.entity.Result
import com.jisucloud.clawler.regagent.util.getRegexMatch
import org.jsoup.Connection
import org.jsoup.Jsoup

/**
 * reg007单独处理
 */
class Reg007Service {

    fun doCheckTelephone(account: String): MutableList<Any> {
        try {
            var response = Jsoup.connect("https://www.reg007.com/account/signin").execute()
            var cookies = response.cookies()
            val csrf = response.parse().selectFirst("[name=__csrf__]").`val`()
            Jsoup.connect("https://www.reg007.com/account/signin")
                    .cookies(cookies)
                    .data(mapOf(
                            "account" to "18210538513",
                            "password" to "admini",
                            "remember" to "on",
                            "__csrf__" to csrf
                    ))
                    .method(Connection.Method.POST)
                    .execute().run {
                        cookies.putAll(this.cookies())
                        this
                    }
            val document = Jsoup.connect("https://www.reg007.com/search?q=$account").cookies(cookies).execute().run {
                cookies.putAll(this.cookies())
                this.body()
            }
            val h = Regex("""var h="([a-z0-9]+)";""").find(document).run { this!!.groupValues[1] }
            var i = Regex("""var i=(\d+)""").find(document).run { this!!.groupValues[1].toInt() }

            var relustList = mutableListOf<Any>()

            for (t in 0..31) {
                Jsoup.connect("https://www.reg007.com/search/ajax")
                        .cookies(cookies)
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
                                        relustList.add(Result(desc, domain, domain, name, false, true, null))
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