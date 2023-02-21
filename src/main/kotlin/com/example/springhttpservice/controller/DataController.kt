package com.example.springhttpservice.controller

import com.example.springhttpservice.model.OriginData
import com.example.springhttpservice.service.CalDataService
import com.example.springhttpservice.service.OriginDataService
import com.example.springhttpservice.utils.dateFormat1
import com.example.springhttpservice.utils.getFormattedDate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import java.util.*


@Controller
@RequestMapping("/data")
class DataController {

    @Autowired
    lateinit var originDataService: OriginDataService

    @Autowired
    lateinit var calService: CalDataService

    @GetMapping("/code={code}/date={date}")
    fun findOriginDataById(@PathVariable("code") code: String,
                           @PathVariable("date") dateStr: String): ResponseEntity<OriginData> {
        println("param: id:$code")
        val result = originDataService.findBy代码And日期(code, dateStr)
        return ResponseEntity.ok().body(result)
    }

    @GetMapping("/code={code}")
    fun findOriginDataWithCode(@PathVariable code: String): ResponseEntity<List<OriginData>> {
        val result = originDataService.findAllBy代码(code)
        return ResponseEntity.ok().body(result)
    }

    @GetMapping("/count={count}")
    fun originDataWithDays(@PathVariable("count") count: Int): ResponseEntity<Map<Date, List<OriginData>>> {
        println("count:$count")
        val list = originDataService.findDataInDays(Date(), count)
        return ResponseEntity.ok().body(list)
    }

    @GetMapping("/date={date}/count={count}")
    fun originDataWithDaysWithCount(@PathVariable("count") count: Int, @PathVariable(
            "date") dateStr: String): ResponseEntity<Map<Date, List<OriginData>>> {
        println("count:$count")
        val list = originDataService.findDataInDays(dateFormat1.parse(dateStr), count)
        return ResponseEntity.ok().body(list)
    }


    @GetMapping("/getAll")
    fun allData(): ResponseEntity<List<OriginData>> {
        val list = originDataService.findAll()
        return ResponseEntity.ok().body(list)
    }

    @GetMapping("/all/date={date}")
    fun findAllByDate(@PathVariable("date") dateStr: String): ResponseEntity<List<OriginData>> {
        val date = getFormattedDate(dateStr)
        val dataList = originDataService.findAllBy日期(date)
        return ResponseEntity.ok(dataList)
    }

    @GetMapping("/delete/{date}")
    fun deleteDataByDate(@PathVariable("date") dateStr: String): ResponseEntity<String> {
        originDataService.deleteBy日期(getFormattedDate(dateStr))
        return ResponseEntity.ok("ok")
    }

    @GetMapping("/deleteAll")
    fun deleteAll(): ResponseEntity<String> {
        originDataService.deleteAll()
        calService.deleteAll()
        return ResponseEntity.ok("ok")
    }
}