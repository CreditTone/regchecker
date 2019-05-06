package com.jisucloud.clawler.regagent.controller

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.stereotype.Controller
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
internal class CheckControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun doCheck() {
        var mvcResult = mvc.perform(
                MockMvcRequestBuilders.post("/check")
                        .content(
                                """
                                {
                                    "account":"18799990000",
                                    "accountType":"PHONE",
                                    "exclusions":["12306","CMCC"]
                                }
                            """.trimIndent()
                        )
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
        System.err.println("注册检测接口返回：" + mvcResult.response.contentAsString)
    }
}