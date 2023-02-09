package com.example.springdemo.bean

import java.util.*


//竞价评级
enum class PriceExpect(val value: Int) {
    EMPTY(0), MORE(1), NONE(-1);
}

// 判断 excel 的数值 是否有效.
fun String.isValid() = isEmpty() || this == "--"

//涨停原因.

//  代码	    名称	    主力控盘比例	零散散户变化	涨幅	    今昨早盘竞价量比值	主力净量	主力净额	    换手	    量比	    成交量	    行业	                概念	                                                        金叉个数	周涨停	竞价评级	5日涨幅	现价	    流通市值	    市盈(动)	涨停原因	首次涨停时间	涨停封成比	市场关注度
//SH605599	菜百股份	22.81	    14.12	    -1.96%	6.43	        -0.15	-8315508	0.99%	5.85	577.12万	轻工制造-家用轻工-饰品	【北京国资改革;电子商务;黄金概念;冬奥会;冬奥纪念品;地方国资改革;网络直播】	1	--	    --	    -1.28%	10.02	5878511600	15.7	--	    --	        --	        --
//SH605598	上海港湾	20.46	    378.37	    0.62%	1.21	        -2.31	-22161946	44.95%	2.25	1922.34万	建筑装饰-建筑装饰-专业工程	【核准制次新股;新股与次新股;基建工程;一带一路;半年报预增;高送转预期】	0	2	    看多	    25.40%	21.03	908358610	16.12	--	    --	        --	        668
data class OriginData(
        val codeStr: String, //代码
        val name: String, //名称
        val zhulikpb: Float, //主力控盘比
        val lsshbh: Float, //零散散户变化
        val zhangfu: Float, //涨幅     -1.96%  比分比
        val jjl: Float, //今昨早盘竞价量比值
        val zhulijl: Float,  //主力净量
        val zhulije: Float,  //主力净额
        val huanshou: Float,  //换手   0.99%
        val liangbi: Float, //量比
        val chjl: Float, // 成交量 ,  单位 万
        val hangye: String,//行业
        val gainian: String,//概念
        val jchgsh: Int, //金叉个数
        val zhzht: Int, //周涨停 -- 1, 2等
        val jjpj: PriceExpect, // 竞价评级
        val wurizhf: Float,// 5日涨幅  -1.28%
        val xianjia: Float,// 现价
        val liutongshzh: Long, //流通市值
        val shiyd: Float, //市盈动
        val zhtyy: String,//涨停原因.
        val time: Date, // 首次涨停时间
        val zhtfchb: Float, //涨停封城比
        val shchgzhd: Int, //市场关注度
) {
    companion object {
        fun instance(vararg params: String) = OriginData(
                codeStr = params[0], name = params[1],
                zhulikpb = params[3].run { if (isValid()) 0f else this.toFloat() },
                lsshbh = params[4].run { if (isValid()) 0f else this.toFloat() },



        )
    }
}


