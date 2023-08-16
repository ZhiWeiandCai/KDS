package com.pax.kdsdemo.kitchen.utils

/**
 * Created by caizhiwei on 2023/8/2
 */
class RegexUtils {
    object Constants {
        // IP 地址的正则表达式
        private const val IP_ADDRESS_REGEX: String =
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"

        @JvmStatic fun isValidIpAddress(input: String?): Boolean {
            return input?.matches(Regex(IP_ADDRESS_REGEX)) ?: false
        }
    }
}