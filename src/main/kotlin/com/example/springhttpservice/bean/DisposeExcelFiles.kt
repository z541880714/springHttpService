package com.example.springhttpservice.bean

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.poifs.filesystem.NPOIFSFileSystem
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


//处理excel 文件
@Service
class DisposeExcelFiles {

    //07 以后的 高版本 excel  XSSFWorkbook
    private fun convertToWorkbook(input: InputStream): XSSFWorkbook {
        val workbook = XSSFWorkbook(input)
        val sheetCount = workbook.numberOfSheets
        println("sheet count:$sheetCount")
        return workbook
    }

    //excel 比较文件 HSSFWorkbook
    fun excelCompare(fileName: String?, input: InputStream) {
        val workbook = convertToWorkbook(input)

    }

    //原始文件,读取..
    fun excelOrigin(fileName: String?, input: InputStream) {
        val workbook = convertToWorkbook(input)
        val sheet = workbook.getSheetAt(0)
    }

}