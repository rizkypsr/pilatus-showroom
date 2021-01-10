package com.showroom.pilatus.data

class ResponseModel {

    var success = 0
    lateinit var message: String
    var user = User()
    var product: ArrayList<Product> = ArrayList()
}