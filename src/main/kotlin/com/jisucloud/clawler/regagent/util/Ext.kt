package com.jisucloud.clawler.regagent.util

import org.springframework.util.DigestUtils
import java.util.*

/**
 * 扩展String方便获取正则匹配的文本
 * 匹配不到时返回空字符串
 */
fun String.getRegexMatch(regex: String): String {
    try {
        return Regex(regex).find(this)!!.groupValues[1]
    } catch (e: Exception) {
    }
    return ""
}

/**
 * 扩展String，增加MD5加密
 */
fun String.md5DigestAsHex(): String = DigestUtils.md5DigestAsHex(this.toByteArray())

/**
 * 扩展ByteArray增加转Base64字符串方法
 */
fun ByteArray.toBase64PNG() = "data:image/png;base64,${Base64.getEncoder().encodeToString(this)}"