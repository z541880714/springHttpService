package com.example.springhttpservice.bean

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service


@Service
class HttpResPonse {
    fun responseHelloWorld(): ResponseEntity<String> = ResponseEntity("Hello World!", HttpStatus.OK)
    val responseOK get() = ResponseEntity<String>("OK", HttpStatus.OK)
    val responseNoFile get() = ResponseEntity<String>("no file!!", HttpStatus.OK)

}