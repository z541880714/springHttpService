package com.example.springhttpservice.db.bean

import lombok.Data
import java.io.Serializable
import javax.persistence.*


@Data
@Entity
@Table(name = "StockData")
class StockData : Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private var id: Long = 0
    private var name: String = ""

}