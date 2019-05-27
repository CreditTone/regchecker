package com.jisucloud.clawler.regagent.entity

import com.jisucloud.clawler.regagent.enums.AccountType

data class Info(val account: String, val accountType: AccountType, val exclusions: Array<String>, val reg007CallBackUrl: String?)