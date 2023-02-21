package com.example.springhttpservice.controller

import com.example.springhttpservice.excel.DisposeExcelFiles
import com.example.springhttpservice.service.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest


const val RESPONSE_CODE_FILE_NO_DATA = 5
const val RESPONSE_CODE_FILE_EXIST = 4
const val RESPONSE_CODE_FILE_EXTERNAL_TYPE_ERROR = 3
const val RESPONSE_CODE_FILE_NAME_FORMAT_ERROR = 2
const val RESPONSE_CODE_NO_FILE_FOUNDED = 1
const val RESPONSE_CODE_SUCCESS = 0

@RestController
class UploadController(@Autowired val userService: UserService) {

    @Autowired
    lateinit var disposeExcel: DisposeExcelFiles


    @GetMapping("/")
    fun httpRequest(@RequestHeader("Host") host: String,
                    request: HttpServletRequest): ResponseEntity<String> {
        println("=============================> host:$host, request:${request.requestURL}")
        return ResponseEntity("Hello World!", HttpStatus.OK)
    }

    @PostMapping("/upload")
    fun uploadFile(@RequestHeader("Host") host: String, request: HttpServletRequest): ResponseEntity<String> {
        println("host: $host,  url:${request.requestURL}")

        val type = request.getParameter("type")
        if (type.isEmpty()) error("request type is empty")
        when (type) {
            "file" -> {
                val multiMap = request.run { this as MultipartHttpServletRequest }.multiFileMap
                if (multiMap.size == 0) {
                    return ResponseEntity<String>("no file!!", HttpStatus.OK)
                }
                val files =
                        multiMap["file"] ?: return ResponseEntity<String>("no file param !!", HttpStatus.OK)
                val resultStr = disposeFile(files)
                return ResponseEntity<String>(resultStr, HttpStatus.OK)
            }
        }
        return ResponseEntity<String>("OK", HttpStatus.OK)
    }


    /**
     * @return 0:成功 1: httpResPonse.responseNoFile,
     * 2:httpResPonse.responseFileNameFormatError
     * 3: httpResPonse.responseFileExternalTypeError
     * 4: 数据已经存在.
     * 5: 批量上传, 不在回复.
     */
    private fun disposeFile(files: List<MultipartFile>): String {
        val resultSb = arrayListOf<String>()

        files.sortedBy { it.originalFilename }.forEach { file ->
            println("start dispose file , name:${file.originalFilename}")
            val fileName = file.originalFilename
            //无此文件.
            if (fileName.isNullOrEmpty()) {
                resultSb.add("fileName:${fileName}, error: RESPONSE_CODE_FILE_NAME_FORMAT_ERROR \n")
                return@forEach
            }
            //日期格式不对.
            if (!fileName.matches("^20\\d{6}.xlsx$".toRegex())) {
                resultSb.add("fileName:$fileName, error: RESPONSE_CODE_FILE_NAME_FORMAT_ERROR \n")
                return@forEach
            }
            val external = fileName.substringAfter('.')
            //文件名格式不对.
            if (external != "xlsx") {
                resultSb.add("fileName:$fileName, error: RESPONSE_CODE_FILE_EXTERNAL_TYPE_ERROR \n")
                return@forEach
            }
            val ins = file.inputStream
            val rec = disposeExcel.excelOrigin(fileName.substringBefore('.'), ins)
            when (rec) {
                RESPONSE_CODE_FILE_EXIST -> {
                    resultSb.add("fileName:$fileName, error: file already exist! \n")
                }

                RESPONSE_CODE_SUCCESS -> {
                    resultSb.add("fileName:$fileName, uploadSuccess \n")
                }
            }
        }

        return resultSb.toString()
    }


}