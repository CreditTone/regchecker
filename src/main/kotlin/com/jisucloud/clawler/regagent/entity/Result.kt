package com.jisucloud.clawler.regagent.entity

data class Result(val message: String?, val platform: String, val platformName: String, val checkEmail: Boolean, val checkTelephone: Boolean, val fields: Map<String, String>?)