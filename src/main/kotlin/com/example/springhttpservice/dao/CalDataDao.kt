package com.example.springhttpservice.dao

import com.example.springhttpservice.model.CalData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CalDataDao : JpaRepository<CalData, Long> {


}