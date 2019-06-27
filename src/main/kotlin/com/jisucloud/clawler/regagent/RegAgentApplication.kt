package com.jisucloud.clawler.regagent

import com.jisucloud.clawler.regagent.service.PapaSpider
import com.jisucloud.clawler.regagent.util.JJsoupUtil
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import java.net.Authenticator
import java.net.PasswordAuthentication

@EnableScheduling
@SpringBootApplication
class RegAgentApplication

fun main() {
    initJJsoupProxy()
    val applicationContext = runApplication<RegAgentApplication>()
}

/**
 * 设置全局代理，所有Jsoup请求直接走阿布云
 */
fun initJJsoupProxy() {
    JJsoupUtil.useProxy = true
    System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "")
    Authenticator.setDefault(object : Authenticator() {
        public override fun getPasswordAuthentication(): PasswordAuthentication {
            return PasswordAuthentication("H6224X2YF291C2AD", "2EADA65DEE87F60C".toCharArray())
        }
    })
}

