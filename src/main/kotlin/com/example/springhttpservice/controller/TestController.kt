package com.example.springhttpservice.controller

import com.example.springhttpservice.model.UserTest
import com.example.springhttpservice.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random


@RestController
@RequestMapping("/test")
class TestController {

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/users", "/all")

    fun getAllUsers() = ResponseEntity.ok().body(userService.getAllUsers())

    @GetMapping("/save")
    fun save(): ResponseEntity<UserTest> {
        val userTest = UserTest(Random.nextLong(), Random.nextInt().toString())
        userService.save(userTest)
        return ResponseEntity.ok().body(userTest)
    }
}