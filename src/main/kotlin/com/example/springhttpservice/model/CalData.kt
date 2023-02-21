package com.example.springhttpservice.model

import jakarta.persistence.*
import java.util.Date

@Entity
@Table(indexes = [Index(columnList = "代码"), Index(columnList = "日期")])
data class CalData(@Id @GeneratedValue(strategy = GenerationType.AUTO) var id: Long = 0,
                   val 代码: String = "", //
                   val 名称: String = "", //
                   val 日期: Date = Date(), //
                   val last_0: Float = 0f, //
                   val last_1: Float = 0f, //
                   val last_2: Float = 0f, //
                   val last_3: Float = 0f, //
                   val last_4: Float = 0f, //
                   val last_5: Float = 0f, //
                   val last_6: Float = 0f, //
                   val last_7: Float = 0f, //
                   val 行业_概念: String = "") {
    val last_0_1: Float = last_0 - last_1
    val last_1_2: Float = last_1 - last_2
    val last_2_3: Float = last_2 - last_3
    val last_3_4: Float = last_3 - last_4
    val last_4_5: Float = last_4 - last_5
    val last_5_6: Float = last_5 - last_6
    val last_6_7: Float = last_6 - last_7
}




