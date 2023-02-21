package com.example.springhttpservice.controller

import com.example.springhttpservice.model.CalData
import com.example.springhttpservice.service.CalDataService
import com.example.springhttpservice.service.OriginDataService
import com.example.springhttpservice.utils.getFormattedDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/cal")
class CalController(
        @Autowired val calService: CalDataService, @Autowired val originDataService: OriginDataService) {

    private fun filterTabs(content: String, flagStr: String): Boolean {
        val flags = flagStr.split("-").filter { it.isNotEmpty() }
        flags.forEach {
            when (it[0]) {
                '&' -> if (!content.contains(it.substring(1))) return false
                '!' -> if (content.contains(it.substring(1))) return false
            }
        }
        return true
    }


    /**
     *@param date 日期字符串:  格式为  20220910
     * @param count:Int 以[date] 开始, 往前一共数count 天.
     * @param condition :String 概念,行业 相关信息...  &xxxx-!xxxx-&xxxx-&xxxx-!xxxx
     */
    @GetMapping("/date={date}/count={count}/condition={condition}")
    fun queryData(
            @PathVariable("date") date: String,
            @PathVariable("count") count: Int,
            @PathVariable("condition") condition: String): ResponseEntity<Any> {
        println("==========>queryData !!!")
        if (!condition.matches(Regex("^([&!]\\S+-)*([&!]\\S+)$")))
            return ResponseEntity.ok("condition format is error: ${condition}")

//        val mapOriginData = originDataService.findDataInDays(getFormattedDate(date), count)
        val mapCalData = calService.findDataInDays(getFormattedDate(date), count)
//        val mapCalData = calService.findAllBy日期(mapOriginData.keys.toList())
        // 根据 condition 对查到的数据进行过滤...
        val filterCalData = mapCalData.map {
            it.key to it.value.filter { data ->
                filterTabs(data.行业_概念, condition)
            }
        }.toMap()

        return ResponseEntity.ok(filterCalData)
    }

}