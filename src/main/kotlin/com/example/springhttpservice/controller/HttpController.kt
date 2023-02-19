package com.example.springhttpservice.controller

import com.example.springhttpservice.bean.DisposeExcelFiles
import com.example.springhttpservice.bean.HttpResponse
import com.example.springhttpservice.service.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest
import java.io.File

@RestController
class HttpController(@Autowired val userService: UserService) {
    @Autowired
    lateinit var httpResPonse: HttpResponse

    @Autowired
    lateinit var disposeExcel: DisposeExcelFiles


    @GetMapping("/")
    fun httpRequest(@RequestHeader("Host") host: String,
                    request: HttpServletRequest): ResponseEntity<String> {
        println("=============================> host:$host, request:${request.requestURL}")
        return httpResPonse.responseHelloWorld()
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
                    return httpResPonse.responseNoFile
                }
                val files = multiMap["file"] ?: return httpResPonse.responseNoFile
                files.forEach {
                    when (disposeFile(it)) {
                        1 -> return httpResPonse.responseNoFile
                        2 -> return httpResPonse.responseFileNameFormatError
                        3 -> return httpResPonse.responseFileExternalTypeError
                    }
                }
            }
        }
        return httpResPonse.responseOK
    }

    /**
     * @return 0:成功 1: httpResPonse.responseNoFile,
     * 2:httpResPonse.responseFileNameFormatError
     * 3: httpResPonse.responseFileExternalTypeError
     */
    private fun disposeFile(file: MultipartFile): Int {
        println("start dispose file , name:${file.originalFilename}")
        val fileName = file.originalFilename
        //无此文件.
        if (fileName.isNullOrEmpty()) return 1
        //日期格式不对.
        if (!fileName.matches("^20\\d{6}.xlsx$".toRegex())) return 2
        val external = fileName.substringAfter('.')
        //文件名格式不对.
        if (external != "xlsx") return 3
        val ins = file.inputStream
        disposeExcel.excelOrigin(fileName.substringBefore('.'), ins)
        return 0
    }


}