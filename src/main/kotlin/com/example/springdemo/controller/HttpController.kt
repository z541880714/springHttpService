package com.example.springdemo.controller

import com.example.springdemo.bean.DisposeExcelFiles
import com.example.springdemo.bean.HttpResPonse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartHttpServletRequest

@RestController
class HttpController {
    @Autowired
    lateinit var httpResPonse: HttpResPonse

    @Autowired
    lateinit var disposeExcel: DisposeExcelFiles

    @GetMapping("/")
    fun httpRequest(@RequestHeader("Host") host: String, request: HttpServletRequest): ResponseEntity<String> {
        println("=============================> host:$host, request:${request.requestURL}")
        return httpResPonse.responseHelloWorld()
    }

    @PostMapping("/upload")
    fun uploadFile(@RequestHeader("Host") host: String, request: HttpServletRequest): ResponseEntity<String> {
        println("host: $host,  url:${request.requestURL}")

        val type = request.getParameter("type")
        val action = request.getParameter("action") // origin / compare
        println("action: $action")
        if (type.isEmpty()) error("request type is empty")
        when (type) {
            "file" -> {
                val multiMap = request.run { this as MultipartHttpServletRequest }.fileMap
                val file = multiMap["file"] ?: return httpResPonse.responseNoFile
                val fileName = file.originalFilename
                val ins = file.inputStream
                when (action) {
                    "origin" -> {
                        disposeExcel.excelOrigin(fileName, ins)
                    }

                    "compare" -> {
                        disposeExcel.excelCompare(fileName, ins)
                    }
                }
            }

            else -> {
                //其他的类型不处理...
            }
        }
        return httpResPonse.responseOK
    }

}