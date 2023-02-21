package com.example.springhttpservice.utils

import java.text.SimpleDateFormat
import java.util.Date


val dateTimeFormat1 = SimpleDateFormat("yyyyMMdd HH:mm:ss")
val timeFormat1 = SimpleDateFormat("HH:mm:ss")
val dateFormat1 = SimpleDateFormat("yyyyMMdd")

/**
 *日期格式化 :  yyyyMMdd HH:mm:ss
 */
fun getFormattedDate(dateStr: String, timeStr: String = "00:00:00"): Date {
    if (!dateStr.matches("^\\d{8}$".toRegex())) error("输入的日期格式不对: $dateStr")
    val _timeStr = if (!timeStr.matches("^\\d\\d?:\\d\\d?:\\d\\d?$".toRegex())) "00:00:00" else timeStr
    val date = dateTimeFormat1.parse("$dateStr 00:00:00")
    val nums = _timeStr.split(":")
    date.hours = nums[0].toInt()
    date.minutes = nums[1].toInt()
    date.seconds = nums[2].toInt()
    return date
}
