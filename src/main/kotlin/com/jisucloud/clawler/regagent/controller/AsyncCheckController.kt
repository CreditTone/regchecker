package com.jisucloud.clawler.regagent.controller

import com.alibaba.fastjson.JSON
import com.jisucloud.clawler.regagent.entity.Info
import com.jisucloud.clawler.regagent.service.CheckService
import com.jisucloud.clawler.regagent.service.TorIDCardSearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.web.bind.annotation.*

/**
 * 用于批量查询是否注册
 */
@RestController
class AsyncCheckController {

    @Autowired
    lateinit var checkService: CheckService

    @PostMapping("async/check", produces = [APPLICATION_JSON_UTF8_VALUE], consumes = [APPLICATION_JSON_UTF8_VALUE])
    @ResponseBody
    fun doAsyncCheck(@RequestBody requestBody: String): Map<String, Any>? {
        try {
            val jsonInfo = JSON.parseObject(requestBody, Info::class.java)
            val result = checkService.doAsyncCheckAndSave(jsonInfo)
            return mapOf(
                    "code" to "200",
                    "success" to true,
                    "msg" to "成功",
                    "result" to result
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return mapOf(
                    "code" to "400",
                    "success" to false,
                    "msg" to e.localizedMessage
            )
        }
        return null
    }

    @GetMapping("async/get", produces = [APPLICATION_JSON_UTF8_VALUE])
    @ResponseBody
    fun doSearchTor(@RequestParam account: String): Map<String, Any>? {
        val getResult = checkService.doAsyncGetResult(account)
        if (getResult.first) {
            return mapOf(
                    "code" to "200",
                    "success" to true,
                    "msg" to "成功",
                    "result" to getResult.second + ""
            )
        } else {
            return mapOf(
                    "code" to "400",
                    "success" to false,
                    "msg" to "未完成",
                    "result" to ""
            )
        }

    }
}