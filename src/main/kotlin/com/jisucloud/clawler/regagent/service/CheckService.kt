package com.jisucloud.clawler.regagent.service

import com.alibaba.fastjson.JSON
import com.jisucloud.clawler.regagent.SpiderMap
import com.jisucloud.clawler.regagent.entity.Info
import com.jisucloud.clawler.regagent.entity.Result
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.http.MediaType
import org.springframework.stereotype.Service

@Service
class CheckService {

    @Autowired
    lateinit var redisTemplate: StringRedisTemplate

    /**
     * 同步检测
     */
//    fun doCheck(info: Info): MutableList<Any> {
//        var relustList = mutableListOf<Any>()
//        SpiderMap.map.forEach { k, v ->
//            if (!info.exclusions.contains(k)) {
//                val checkResult = v.checkTelephone(info.account)
//                relustList.add(Result(v.message(), v.home(), v.platform(), v.platformName(), v.checkEmail(info.account), checkResult, v.fields, v.tags()))
//            }
//        }
//        return relustList
//    }

    /**
     * 多协程加速检测接口响应
     */
//    fun doCheckAsync(info: Info): MutableList<Any> {
//        var relustList = mutableListOf<Any>()
//        var list = SpiderMap.map.map {
//            return@map GlobalScope.async {
//                val k = it.key
//                val v = it.value
//                if (!info.exclusions.contains(k)) {
//                    var checkResult = false
//                    try {
//                        checkResult = v.checkTelephone(info.account)
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                    }
//                    relustList.add(Result(v.message(), v.home(), v.platform(), v.platformName(), v.checkEmail(info.account), checkResult, v.fields, v.tags()))
//                }
//            }
//        }
//        val job007 = GlobalScope.async {
//            //单独处理Reg007
//            relustList.addAll(Reg007Service().doCheckTelephone(info.account))
//        }
//        runBlocking {
//            list.map {
//                it.await()
//            }
//            job007.await()
//        }
//        return relustList
//    }

    fun doCheckAsync2(info: Info): MutableList<Any> {
//        GlobalScope.async {
//            //单独处理Reg007
//            val list007 = Reg007Service().doCheckTelephone(info.account)
//            Jsoup.connect(info.reg007CallBackUrl)
//                    .method(Connection.Method.POST)
//                    .requestBody(JSON.toJSONString(list007))
//                    .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
//                    .execute()
//
//        }
        var relustList = mutableListOf<Any>()
        var list = SpiderMap.map.map {
            return@map GlobalScope.async {
                val k = it.key
                val v = it.value.javaClass.newInstance() as PapaSpider
                if (!info.exclusions.contains(k)) {
                    var checkResult = false
                    try {
                        checkResult = v.checkTelephone(info.account)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                    //单纯是手机好的时候不调用Email检测方法
                    val checkEmail = if (Regex("\\d{11}").matches(info.account)) false else v.checkEmail(info.account)
                    relustList.add(Result(v.message(), v.home(), v.platform(), v.platformName(), checkEmail, checkResult, v.fields, v.tags()))
                }
            }
        }
        runBlocking {
            list.map {
                it.await()
            }
//            job007.await()
        }
        return relustList
    }
}