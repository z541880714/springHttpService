package com.example.springhttpservice.service

import com.example.springhttpservice.dao.CalDataDao
import com.example.springhttpservice.model.CalData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CalDataService(@Autowired val dao: CalDataDao) {

    fun saveAll(dataList: List<CalData>) = dao.saveAll(dataList)
}