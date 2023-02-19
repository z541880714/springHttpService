package com.example.springhttpservice.bean

import com.example.springhttpservice.model.OriginData
import com.example.springhttpservice.service.OriginDataService
import com.example.springhttpservice.utils.dateTimeFormat1
import com.example.springhttpservice.utils.getFormattedDate
import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.InputStream


//处理excel 文件
@Service
class DisposeExcelFiles {

    @Autowired
    private lateinit var originDataService: OriginDataService

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
     */
    fun excelOrigin(dateStr: String, input: InputStream) {
        val workbook = XSSFWorkbook(input)
        val sheet = workbook.getSheetAt(0)
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
            OriginData.instance(getFormattedDate(dateStr), *array).apply {
                originDataList.add(this)
            }
        }
        originDataService.saveAll(originDataList)
    }


}