package com.example.crudproject

import java.io.Serializable

class DetailOrderModel: Serializable {
    var id: Int=0
    var orderId: Int=0
    var productId: Int =0
    var totalProduct: Int=0

    fun getIds(): Int {
        return id
    }

    fun setIds(id: Int) {
        this.id = id
    }

    fun getOrderIds(): Int {
        return orderId
    }

    fun setOrders(orderId: Int) {
        this.orderId = orderId
    }
    fun getProductIds(): Int {
        return productId
    }

    fun setProductIds(productId: Int) {
        this.productId = productId
    }

    fun getTotalProducts(): Int {
        return totalProduct
    }

    fun setTotalProducts(totalProduct: Int) {
        this.totalProduct = totalProduct
    }
}