package com.example.crudproject

import java.io.Serializable

class OrderModel: Serializable {

    var id: Int=0
    var userId: Int=0
    var totalProduct: Int =0
    var totalPrices: Int=0
    var name: String?=null
    var address: String?=null
    var phone: String?=null
    var send: Int=0
    var duration: Int=0
    var bank:String?=null
    var rek:String?=null
    var namerek:String?=null
    var date: String?=null
    var limitdate: String?=null
    var statuspay: Int=0

    fun getIds(): Int {
        return id
    }

    fun setIds(id: Int) {
        this.id = id
    }

    fun getUserIds(): Int {
        return userId
    }

    fun setUserIds(userId: Int) {
        this.userId = userId
    }

    fun getTotalProducts(): Int {
        return totalProduct
    }

    fun setTotalProducts(totalProduct: Int) {
        this.totalProduct = totalProduct
    }

    fun getTotalPricess(): Int {
        return totalPrices
    }

    fun setTotalPricess(totalPrices: Int) {
        this.totalPrices = totalPrices
    }

    fun getNames(): String {
        return name.toString()
    }

    fun setNames(name: String) {
        this.name = name
    }

    fun getaddress(): String {
        return address.toString()
    }

    fun setaddress(address: String) {
        this.address = address
    }

    fun getPhones(): String {
        return phone.toString()
    }

    fun setPhones(phone: String) {
        this.phone = phone
    }

    fun getSends(): Int {
        return send
    }

    fun setSends(send: Int) {
        this.send = send
    }

    fun getDurations(): Int {
        return duration
    }

    fun setDurations(duration: Int) {
        this.duration = duration
    }

    fun getBanks(): String {
        return bank.toString()
    }

    fun setBanks(bank: String) {
        this.bank = bank
    }

    fun getNamereks(): String {
        return namerek.toString()
    }

    fun setNamereks(namerek: String) {
        this.namerek = namerek
    }

    fun getDates(): String {
        return date.toString()
    }

    fun setDates(date: String) {
        this.date = date
    }

    fun getLimitdates(): String {
        return limitdate.toString()
    }

    fun setLimitdates(limitdate: String) {
        this.limitdate = limitdate
    }

    fun getReks(): String {
        return rek.toString()
    }

    fun setReks(rek: String) {
        this.rek = rek
    }

    fun getStatuspays(): Int {
        return statuspay
    }

    fun setStatuspays(statuspay: Int) {
        this.statuspay = statuspay
    }

}