package com.example.springhttpservice.dao

import com.example.springhttpservice.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    override fun findAll(): MutableList<User>
}
