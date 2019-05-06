package com.jisucloud.clawler.regagent

import com.jisucloud.clawler.regagent.service.PapaSpider
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RegAgentApplication

fun main(args: Array<String>) {
    val applicationContext = runApplication<RegAgentApplication>(*args)
    //将PapaSpider接口的所有实现类放到静态变量SpiderMap.map中
    val agents = applicationContext.getBeansOfType(PapaSpider::class.java)
    agents.values.forEach {
        SpiderMap.map.put(it.platform(), it)
    }
}

object SpiderMap {
    var map = mutableMapOf<String, PapaSpider>()
}