package com.example.springhttpservice.dao

import com.example.springhttpservice.model.UserTest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TestRepository : JpaRepository<UserTest, Long> {
    override fun findAll(): MutableList<UserTest>
}
