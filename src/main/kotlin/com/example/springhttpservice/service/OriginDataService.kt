package com.example.springhttpservice.service

import com.example.springhttpservice.dao.OriginDataDao
import com.example.springhttpservice.model.OriginData
import com.example.springhttpservice.utils.dateFormat1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.Date


@Service
class OriginDataService(@Autowired val dao: OriginDataDao) {


    fun findDataInDays(start: Date, dayCount: Int): List<OriginData> {
        var count = dayCount
        val dateTemp = Date(start.time)
        var localDate = LocalDate.of(start.year, start.month, start.date)
        var totalQueryCount = 100 //最多查询100次, 防止死循环
        val result = arrayListOf<OriginData>()
        while (count > 0 && totalQueryCount > 0) {
            val list = dao.findAllBy日期(dateTemp)
            result.addAll(list)
            println("list size:${list.size}, data:$dateTemp")
            if (list.isNotEmpty()) count--
            localDate = localDate.minusDays(1)
            dateTemp.year = localDate.year
            dateTemp.month = localDate.monthValue
            dateTemp.date = localDate.dayOfMonth
            totalQueryCount--
        }
        return result
    }

    fun findAll(): List<OriginData> = dao.findAll()

    fun saveAll(dataList: ArrayList<OriginData>) {
        dao.saveAll(dataList)
    }


    fun findByKey(code: String, dateStr: String): OriginData {
        return dao.findBy代码And日期(code, dateFormat1.parse(dateStr))
    }

    fun findDataByDate(date: Date) = dao.findAllBy日期(date)

    fun findAllBy代码(code: String) = dao.findAllBy代码(code)


}