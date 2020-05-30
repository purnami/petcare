package com.example.crudproject

import java.io.Serializable

class BasketModel: Serializable {
    var id: Int=0
    var userIds: Int=0
    var productId: Int=0
    var totalProduct: Int=0
    var isChecked: Int=0

    fun getid(): Int {
        return id
    }
    fun setid(id: Int) {
        this.id = id
    }
    fun getuserid(): Int {
        return userIds
    }
    fun setuserid(userIds: Int) {
        this.userIds = userIds
    }
    fun getproductid(): Int {
        return productId
    }
    fun setproductid(productId: Int) {
        this.productId = productId
    }
    fun gettotalproduct(): Int {
        return totalProduct
    }
    fun settotalproduct(totalProduct: Int) {
        this.totalProduct = totalProduct
    }
    fun getischecked(): Int {
        return isChecked
    }
    fun setischecked(isChecked: Int) {
        this.isChecked = isChecked
    }
}