package com.example.crudproject

import java.io.Serializable

class ProductSendingModel : Serializable {
    var id: Int = 0
    var orderId: Int =0
    var date: String?=null
    var status: Int =0

    fun getIds(): Int {
        return id
    }

    fun setIds(id: Int) {
        this.id = id
    }

    fun getOrderIds(): Int {
        return orderId
    }

    fun setOrderIds(orderId: Int) {
        this.orderId = orderId
    }

    fun getDates(): String {
        return date.toString()
    }

    fun setDates(date: String) {
        this.date = date
    }

    fun getStatuss(): Int {
        return status
    }

    fun setStatuss(status: Int) {
        this.status = status
    }

}