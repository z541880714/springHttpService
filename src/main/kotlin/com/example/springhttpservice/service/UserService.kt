package com.example.springhttpservice.service

import com.example.springhttpservice.dao.TestRepository
import com.example.springhttpservice.model.UserTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired val userDao: TestRepository) {

    fun getAllUsers(): List<UserTest> = userDao.findAll()

    fun save(userTest: UserTest) {
        userDao.save(userTest)
    }
}