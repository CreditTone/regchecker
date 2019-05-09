package com.jisucloud.clawler.regagent.schedule

import com.jisucloud.clawler.regagent.util.JJsoupUtil
import com.jisucloud.clawler.regagent.util.StaticValue
import org.jsoup.Connection
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Reg007Login {

    @Scheduled(fixedDelay = 3600000)
    fun reg007ReLogin() {
        System.err.println("--------------------重新登录Reg007------>")
        val session = JJsoupUtil.newProxySession()

        val csrf = session.connect("https://www.reg007.com/account/signin").execute().parse().selectFirst("[name=__csrf__]").`val`()
        session.connect("https://www.reg007.com/account/signin")
                .data(mapOf(
                        "account" to "18210538513",
                        "password" to "admini",
                        "remember" to "on",
                        "__csrf__" to csrf
                ))
                .method(Connection.Method.POST)
                .execute()
        StaticValue.REG007_COOKIES = session.cookies()
    }

}