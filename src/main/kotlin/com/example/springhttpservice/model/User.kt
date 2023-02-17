package com.example.springhttpservice.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "users")
class User(@Id var id: Long = 0,

           var name: String = "") {

}