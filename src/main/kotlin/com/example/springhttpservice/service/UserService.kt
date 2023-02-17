package com.example.springhttpservice.service

import com.example.springhttpservice.dao.UserRepository
import com.example.springhttpservice.model.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired val userDao: UserRepository) {

    fun getAllUsers(): List<User> = userDao.findAll()

    fun save(user: User) {
        userDao.save(user)
    }
}