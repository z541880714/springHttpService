package com.example.springhttpservice.controller

import com.example.springhttpservice.model.User
import com.example.springhttpservice.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.Mapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.random.Random


@RestController
@RequestMapping("/test")
class TestController {

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/users")
    fun getAllUsers() = ResponseEntity.ok().body(userService.getAllUsers())

    @GetMapping("/save")
    fun save(): ResponseEntity<User> {
        val user = User(Random.nextLong(), Random.nextInt().toString())
        userService.save(user)
        return ResponseEntity.ok().body(user)
    }
}