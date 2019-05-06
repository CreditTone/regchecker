package com.jisucloud.clawler.regagent.controller

import com.alibaba.fastjson.JSON
import com.jisucloud.clawler.regagent.entity.Info
import com.jisucloud.clawler.regagent.service.CheckService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

/**
 * 用于批量查询是否注册
 */
@RestController
class CheckController {

    @Autowired
    lateinit var checkService: CheckService

    @PostMapping("check", produces = [APPLICATION_JSON_UTF8_VALUE], consumes = [APPLICATION_JSON_UTF8_VALUE])
    @ResponseBody
    fun doCheck(@RequestBody requestBody: String): Map<String, Any>? {
        try {
            val jsonInfo = JSON.parseObject(requestBody, Info::class.java)
            val result = checkService.doCheck(jsonInfo)
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

}