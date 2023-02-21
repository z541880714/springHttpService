package com.example.springhttpservice.service

import com.example.springhttpservice.dao.CalDataDao
import com.example.springhttpservice.model.CalData
import com.example.springhttpservice.model.OriginData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class CalDataService(@Autowired val dao: CalDataDao) {

    fun findDataInDays(start: Date, dayCount: Int): Map<Date, List<CalData>> {
        return findDataInDaysTemplate(start, dayCount) {
            dao.findAllBy日期(it)
        }.groupBy { it.日期 }
    }

    fun findAllBy日期(dateList: List<Date>) = findAllBy日期Template(dateList) {
        dao.findAllBy日期(it)
    }.groupBy { it.日期 }

    fun saveAll(dataList: List<CalData>) = dao.saveAll(dataList)

    fun deleteAll() = dao.deleteAll()
    fun existsBy日期(startDate: Date) = dao.existsBy日期(startDate)
}