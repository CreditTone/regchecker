package com.jisucloud.clawler.regagent.service

import com.alibaba.fastjson.JSON
import com.jisucloud.clawler.regagent.SpiderMap
import com.jisucloud.clawler.regagent.entity.Info
import com.jisucloud.clawler.regagent.entity.Result
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class CheckService {

    @Autowired
    lateinit var redisTemplate: StringRedisTemplate

    /**
     * 同步检测
     */
//    fun doCheck(info: Info): MutableList<Any> {
//        var resultList = mutableListOf<Any>()
//        SpiderMap.map.forEach { k, v ->
//            if (!info.exclusions.contains(k)) {
//                val checkResult = v.checkTelephone(info.account)
//                resultList.add(Result(v.message(), v.home(), v.platform(), v.platformName(), v.checkEmail(info.account), checkResult, v.fields, v.tags()))
//            }
//        }
//        return resultList
//    }

    /**
     * 多协程加速检测接口响应
     */
//    fun doCheckAsync(info: Info): MutableList<Any> {
//        var resultList = mutableListOf<Any>()
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
//                    resultList.add(Result(v.message(), v.home(), v.platform(), v.platformName(), v.checkEmail(info.account), checkResult, v.fields, v.tags()))
//                }
//            }
//        }
//        val job007 = GlobalScope.async {
//            //单独处理Reg007
//            resultList.addAll(Reg007Service().doCheckTelephone(info.account))
//        }
//        runBlocking {
//            list.map {
//                it.await()
//            }
//            job007.await()
//        }
//        return resultList
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
        var resultList = mutableListOf<Any>()
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
                    resultList.add(Result(v.message(), v.home(), v.platform(), v.platformName(), checkEmail, checkResult, v.fields, v.tags()))
                }
            }
        }
        runBlocking {
            list.map {
                it.await()
            }
//            job007.await()
        }
        return resultList
    }

    /**
     * 异步查询并存储到redis
     * key=info.account
     */
    fun doAsyncCheckAndSave(info: Info) {
        if (redisTemplate.hasKey("RegAgent-$info.account")) return
        GlobalScope.async {
            var resultList = mutableListOf<Any>()
            var list = SpiderMap.map.map {
                return@map async {
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
                        resultList.add(Result(v.message(), v.home(), v.platform(), v.platformName(), checkEmail, checkResult, v.fields, v.tags()))
                    }
                }
            }
            runBlocking {
                list.map {
                    it.await()
                }
                redisTemplate.boundValueOps("RegAgent-$info.account").set(JSON.toJSONString(resultList), 24, TimeUnit.HOURS)
            }
        }
    }

    /**
     * 获取异步查询结果
     * key=info.account
     */
    fun doAsyncGetResult(account: String): Pair<Boolean, String?> {
        return if (redisTemplate.hasKey("RegAgent-$account")) Pair(true, redisTemplate.boundValueOps(account).get()) else Pair(false, null)
    }
}