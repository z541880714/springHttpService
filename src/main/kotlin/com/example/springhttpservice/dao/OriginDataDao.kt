package com.example.springhttpservice.dao

import com.example.springhttpservice.model.OriginData
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface OriginDataDao : JpaRepository<OriginData, String> {

    fun findAllBy日期(date: Date): List<OriginData>
    fun findAllBy代码(code: String): List<OriginData>

    fun findBy代码And日期(code: String, date: Date): OriginData

}