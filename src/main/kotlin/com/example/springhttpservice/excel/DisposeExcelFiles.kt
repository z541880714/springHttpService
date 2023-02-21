package com.example.springhttpservice.excel

import com.example.springhttpservice.controller.RESPONSE_CODE_FILE_EXIST
import com.example.springhttpservice.controller.RESPONSE_CODE_SUCCESS
import com.example.springhttpservice.model.CalData
import com.example.springhttpservice.model.OriginData
import com.example.springhttpservice.service.CalDataService
import com.example.springhttpservice.service.OriginDataService
import com.example.springhttpservice.utils.dateTimeFormat1
import com.example.springhttpservice.utils.getFormattedDate
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStream
import java.util.*


//处理excel 文件
@Service
class DisposeExcelFiles {

    @Autowired
    private lateinit var originDataService: OriginDataService

    @Autowired
    private lateinit var calDataService: CalDataService

    //原始文件,读取..
    /**
     * 07 以后的 高版本 excel  XSSFWorkbook
     * 在通过 cell.cellStyle.getDataFormat() 获取 日期的格式..
     * yyyy-MM-dd----- 14
     * yyyy年m月d日--- 31
     * yyyy年m月------- 57
     * m月d日  ---------- 58
     * HH:mm----------- 20
     * H:mm:ss------21
     * h时mm分  ------- 32
     * @param dateStr  约定为日期格式: "yyyyMMdd" 如果格式不对, 那么放弃读取..
     * @return 0: ok , 1:数据已经存在.
     */
    fun excelOrigin(dateStr: String, input: InputStream): Int {
        val workbook = XSSFWorkbook(input)
        val sheet = workbook.getSheetAt(0)

        val startDate = getFormattedDate(dateStr)
        //检查 数据库中 是否已经保存 当天的数据??
        if (originDataService.exsistBy日期(startDate)) return RESPONSE_CODE_FILE_EXIST

        var index = 0 // 从第二行开始.
        val originDataList = arrayListOf<OriginData>()
        sheet.rowIterator().forEachRemaining {
            if (index++ == 0) return@forEachRemaining // 第一行标题不读..
            val array = Array<String>(24) { index ->
                val cell = it.getCell(index) ?: return@Array ""
                val stringValue = when (cell.cellType) {
                    Cell.CELL_TYPE_NUMERIC -> {
                        // cell.cellStyle.dataFormat}: 21-> h:mm:ss
                        if (cell.cellStyle.dataFormat.toInt() == 21) {
                            dateTimeFormat1.format(cell.dateCellValue)
                        } else cell.numericCellValue.toString()
                    }

                    Cell.CELL_TYPE_STRING -> {
                        cell.stringCellValue
                    }

                    Cell.CELL_TYPE_BOOLEAN -> {
                        cell.booleanCellValue.toString()
                    }

                    else -> {
                        ""
                    }
                }
                stringValue
            }
            OriginData.instance(startDate, *array).apply {
                originDataList.add(this)
            }
        }
        if (originDataList.size > 0) {
            originDataService.saveAll(originDataList)
            saveCalData(startDate, originDataList)
        }
        return RESPONSE_CODE_SUCCESS
    }

    /**
     * @param startDate 当前日期
     * @param originDataList 今日数据
     */
    private fun saveCalData(startDate: Date, originDataList: List<OriginData>) {
        if (originDataList.isEmpty()) return
        //获取 当前日期 近三天的数据
        val dataListIn3DaysMap = originDataService.findDataInDays(startDate, 8)

        //按时间倒序
        val dayIndexes = dataListIn3DaysMap.keys.sortedByDescending { it }

        println("map keys :indexes:$dayIndexes,  content:   ${dataListIn3DaysMap.keys}")

        //昨日,  如果没有此数据, 为null
        val date_last_1 = if (dayIndexes.size >= 2) dayIndexes[1] else null
        //前日, 如果没有数据 则为 null
        val date_last_2 = if (dayIndexes.size >= 3) dayIndexes[2] else null
        val date_last_3 = if (dayIndexes.size >= 4) dayIndexes[3] else null
        val date_last_4 = if (dayIndexes.size >= 5) dayIndexes[4] else null
        val date_last_5 = if (dayIndexes.size >= 6) dayIndexes[5] else null
        val date_last_6 = if (dayIndexes.size >= 7) dayIndexes[6] else null
        val date_last_7 = if (dayIndexes.size >= 8) dayIndexes[7] else null

        val calDataList = arrayListOf<CalData>()
        originDataList.forEach {
            // 今天的数据  : it
            //1天前
            val _last_1 = if (date_last_1 == null) 0f else dataListIn3DaysMap[date_last_1]!!
                    .find { data -> data.代码 == it.代码 }?.主力控盘比 ?: 0f
            //2天前
            val _last_2 = if (date_last_2 == null) 0f else dataListIn3DaysMap[date_last_2]!!
                    .find { data -> data.代码 == it.代码 }?.主力控盘比 ?: 0f
            // 3天前
            val _last_3 = if (date_last_3 == null) 0f else dataListIn3DaysMap[date_last_3]!!
                    .find { data -> data.代码 == it.代码 }?.主力控盘比 ?: 0f
            // 4天前
            val _last_4 = if (date_last_4 == null) 0f else dataListIn3DaysMap[date_last_4]!!
                    .find { data -> data.代码 == it.代码 }?.主力控盘比 ?: 0f
            // 5天前
            val _last_5 = if (date_last_5 == null) 0f else dataListIn3DaysMap[date_last_5]!!
                    .find { data -> data.代码 == it.代码 }?.主力控盘比 ?: 0f
            //6 天前
            val _last_6 = if (date_last_6 == null) 0f else dataListIn3DaysMap[date_last_6]!!
                    .find { data -> data.代码 == it.代码 }?.主力控盘比 ?: 0f
            //7天前
            val _last_7 = if (date_last_7 == null) 0f else dataListIn3DaysMap[date_last_7]!!
                    .find { data -> data.代码 == it.代码 }?.主力控盘比 ?: 0f
            CalData(
                    代码 = it.代码, 名称 = it.名称,
                    日期 = it.日期, last_0 = it.主力控盘比,
                    last_1 = _last_1,
                    last_2 = _last_2,
                    last_3 = _last_3,
                    last_4 = _last_4,
                    last_5 = _last_5,
                    last_6 = _last_6,
                    last_7 = _last_7,
            ).apply { calDataList.add(this) }

            calDataService.saveAll(calDataList)
        }
    }


}