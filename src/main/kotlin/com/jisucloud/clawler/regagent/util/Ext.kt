package com.jisucloud.clawler.regagent.util

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

