package com.example.springhttpservice.model

import jakarta.persistence.*
import java.io.Serializable
import java.util.*


// 判断 excel 的数值 是否有效.
fun String.isValid() = isEmpty() || this == "--"

// 联合主键...
data class PrimaryKeyOriginData(var 代码: String = "", var 日期: Date = Date()) : Serializable

//涨停原因.

//  代码	    名称	    主力控盘比例	零散散户变化	涨幅	    今昨早盘竞价量比值	主力净量	主力净额	    换手	    量比	    成交量	    行业	                概念	                                                        金叉个数	周涨停	竞价评级	5日涨幅	现价	    流通市值	    市盈(动)	涨停原因	首次涨停时间	涨停封成比	市场关注度
//SH605599	菜百股份	22.81	    14.12	    -1.96%	6.43	        -0.15	-8315508	0.99%	5.85	577.12万	轻工制造-家用轻工-饰品	【北京国资改革;电子商务;黄金概念;冬奥会;冬奥纪念品;地方国资改革;网络直播】	1	--	    --	    -1.28%	10.02	5878511600	15.7	--	    --	        --	        --
//SH605598	上海港湾	20.46	    378.37	    0.62%	1.21	        -2.31	-22161946	44.95%	2.25	1922.34万	建筑装饰-建筑装饰-专业工程	【核准制次新股;新股与次新股;基建工程;一带一路;半年报预增;高送转预期】	0	2	    看多	    25.40%	21.03	908358610	16.12	--	    --	        --	        668
@Entity
//@IdClass(value = PrimaryKeyOriginData::class)
@Table(
        name = "originData", indexes = [
    Index(columnList = "代码"),
    Index(columnList = "日期"),
])
data class OriginData(
        @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Long = 0,
        val 代码: String = "", //代码
        val 日期: Date = Date(), //日期
        val 名称: String = "", //名称
        val 主力控盘比: Float = 0f, //主力控盘比
        val 零散散户变化: Float = 0f, //零散散户变化
        val 涨幅: Float = 0f, //涨幅     -1.96%  比分比
        val 今昨早盘竞价量比值: Float = 0f, //今昨早盘竞价量比值
        val 主力净量: Float = 0f,  //主力净量
        val 主力净额: Long = 0L,  //主力净额
        val 换手: Float = 0f,  //换手   0.99%
        val 量比: Float = 0f, //量比
        val 成交量: Float = 0f, // 成交量 ,  单位 万
        val 行业: String = "",//行业
        val 概念: String = "",//概念
        val 金叉个数: Int = 0, //金叉个数
        val 周涨停: Int = 0, //周涨停 -- 1, 2等
        val 竞价评级: String = "", // 竞价评级
        val 五日涨幅: Float = 0f,// 5日涨幅  -1.28%
        val 现价: Float = 0f,// 现价
        val 流通市值: Long = 0L, //流通市值
        val 市盈动: Float = 0f, //市盈动
        val 涨停原因: String = "",//涨停原因.
        val 首次涨停时间: String = "", // 首次涨停时间  时间格式 9:30:15
        val 涨停封成比: Float = 0f, //涨停封城比
        val 市场关注度: Int = 0, //市场关注度
) {

    companion object {
        fun instance(date: Date, vararg params: String) = OriginData(
                代码 = params[0].run { if (isValid()) "" else this },
                日期 = date,
                名称 = params[1].run { if (isValid()) "" else this },
                主力控盘比 = params[2].run { if (isValid()) 0f else this.toFloat() },
                零散散户变化 = params[3].run { if (isValid()) 0f else this.toFloat() },
                涨幅 = params[4].run { if (isValid()) 0f else this.toFloat() },
                今昨早盘竞价量比值 = params[5].run { if (isValid()) 0f else this.toFloat() },
                主力净量 = params[6].run { if (isValid()) 0f else this.toFloat() },
                主力净额 = params[7].run { if (isValid()) 0f else this.toFloat() }.toLong(),
                换手 = params[8].run { if (isValid()) 0f else this.toFloat() },
                量比 = params[9].run { if (isValid()) 0f else this.toFloat() },
                成交量 = params[10].run {
                    if (isValid()) 0f
                    else if (this.endsWith("万")) this.substringBefore("万").toFloat() * 10000
                    else if (this.endsWith("亿")) this.substringBefore("亿").toFloat() * 100000000
                    else this.toFloat()
                },
                行业 = params[11].run { if (isValid()) "" else this },
                概念 = params[12].run { if (isValid()) "" else this },
                金叉个数 = params[13].run { if (isValid()) 0 else this.toFloat().toInt() },
                周涨停 = params[14].run { if (isValid()) 0 else this.toFloat().toInt() },
                竞价评级 = params[15].run { if (isValid()) "" else this },
                五日涨幅 = params[16].run { if (isValid()) 0f else this.toFloat() },
                现价 = params[17].run { if (isValid()) 0f else this.toFloat() },
                流通市值 = params[18].run { if (isValid()) 0L else this.toFloat().toLong() },
                市盈动 = params[19].run { if (isValid()) 0f else this.toFloat() },
                涨停原因 = params[20].run { if (isValid()) "" else this },
                首次涨停时间 = params[21].run { if (isValid()) "00:00:00" else this },
                涨停封成比 = params[22].run { if (isValid()) 0f else this.toFloat() },
                市场关注度 = params[23].run { if (isValid()) 0 else this.toFloat().toInt() },
        )

        fun instanceTest(date: Date, vararg params: String) = OriginData(
                代码 = (Random().nextInt()).toString(),
                日期 = date,
        )
    }
}





