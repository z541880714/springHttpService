package com.example.springhttpservice.service

import com.example.springhttpservice.dao.OriginDataDao
import com.example.springhttpservice.model.OriginData
import com.example.springhttpservice.utils.dateFormat1
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.util.Date

/**
 * 查询数据库的近 N 天 的模版方法...
 */
fun <T> findDataInDaysTemplate(start: Date, dayCount: Int, findData: (Date) -> List<T>): List<T> {
    var count = dayCount
    val dateTemp = Date(start.time)
    var localDate = LocalDate.of(start.year, start.month, start.date)
    var totalQueryCount = 100 //最多查询100次, 防止死循环
    val result = arrayListOf<T>()
    while (count > 0 && totalQueryCount > 0) {
        val list = findData(dateTemp)
        if (list.isNotEmpty()) {
            result.addAll(list)
            println("list size:${list.size}, data:$dateTemp")
            count--
        }
        localDate = localDate.minusDays(1)
        dateTemp.year = localDate.year
        dateTemp.month = localDate.monthValue
        dateTemp.date = localDate.dayOfMonth
        totalQueryCount--
    }
    return result
}

fun <T> findAllBy日期Template(dateList: List<Date>, find: (Date) -> List<T>): List<T> {
    val result = arrayListOf<T>()
    dateList.forEach {
        find(it).apply { result.addAll(this) }
    }
    return result
}


@Service
class OriginDataService(@Autowired val dao: OriginDataDao) {


    fun findDataInDays(start: Date, dayCount: Int): Map<Date, List<OriginData>> {
        return findDataInDaysTemplate(start, dayCount) {
            dao.findAllBy日期(it)
        }.groupBy { it.日期 }
    }

    fun findAllBy日期(dateList: List<Date>) = findAllBy日期Template(dateList) {
        dao.findAllBy日期(it)
    }.groupBy { it.日期 }

    fun findAllBy日期(date: Date) = dao.findAllBy日期(date)


    fun findAll(): List<OriginData> = dao.findAll()

    fun saveAll(dataList: ArrayList<OriginData>) {
        dao.saveAll(dataList)
    }

    fun findBy代码And日期(code: String, dateStr: String): OriginData {
        return dao.findBy代码And日期(code, dateFormat1.parse(dateStr))
    }


    fun findAllBy代码(code: String) = dao.findAllBy代码(code)
    fun exsistBy日期(startDate: Date) = dao.existsBy日期(startDate)
    fun deleteBy日期(date: Date) = dao.deleteBy日期(date)

    fun deleteAll() = dao.deleteAll()


}