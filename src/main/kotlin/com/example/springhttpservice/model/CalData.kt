package com.example.springhttpservice.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table
data class CalData(
        @Id
        var 代码: String = "",
        var 名称: String = "",
        var 策略一: String = "",
        var 今日控盘: Float = 0f,
        var 昨日控盘: Float = 0f,
        var 前日控盘: Float = 0f,
        var 前昨控变: Float = 0f,
        var 昨今控变: Float = 0f,
        var 板块概念: String = "",
)




