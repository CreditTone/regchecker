package com.jisucloud.clawler.regagent.service

import com.jisucloud.clawler.regagent.SpiderMap
import com.jisucloud.clawler.regagent.entity.Info
import com.jisucloud.clawler.regagent.entity.Result
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class CheckService {

    /**
     * 同步检测
     */
    fun doCheck(info: Info): MutableList<Any> {
        var relustList = mutableListOf<Any>()
        SpiderMap.map.forEach { k, v ->
            if (!info.exclusions.contains(k)) {
                val checkResult = v.checkTelephone(info.account)
                relustList.add(Result(v.message(), v.platform(), v.platformName(), v.checkEmail(info.account), checkResult, v.fields))
            }
        }
        return relustList
    }

    /**
     * 多协程加速检测接口响应
     */
    fun doCheckAsync(info: Info): MutableList<Any> {
        var relustList = mutableListOf<Any>()
        val list = SpiderMap.map.map {
            return@map GlobalScope.async {
                val k = it.key
                val v = it.value
                if (!info.exclusions.contains(k)) {
                    val checkResult = v.checkTelephone(info.account)
                    relustList.add(Result(v.message(), v.platform(), v.platformName(), v.checkEmail(info.account), checkResult, v.fields))
                }
            }
        }
        runBlocking {
            list.map {
                it.await()
            }
        }
        return relustList
    }
}