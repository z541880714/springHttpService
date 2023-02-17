package com.example.springhttpservice.controller

import com.example.springhttpservice.bean.DisposeExcelFiles
import com.example.springhttpservice.bean.HttpResponse
import com.example.springhttpservice.model.User
import com.example.springhttpservice.service.UserService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartHttpServletRequest

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
                val fileName = files[0].originalFilename
                if (fileName.isNullOrEmpty()) return httpResPonse.responseNoFile
                println("fileName:$fileName")
                if (!fileName.matches(
                                "^20\\d{6}.xlsx$".toRegex())) return httpResPonse.responseFileNameFormatError

                val external = fileName.substringAfter('.')
                if (external != "xlsx") return httpResPonse.responseFileExternalTypeError
                val ins = files[0].inputStream
                disposeExcel.excelOrigin(fileName, ins)
            }
        }
        return httpResPonse.responseOK
    }



}