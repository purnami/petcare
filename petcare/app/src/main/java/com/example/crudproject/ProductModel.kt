package com.example.crudproject
import java.io.Serializable

class ProductModel : Serializable{
    var name: String? = null
    var price: Int = 0
    var stock: Int = 0
    var id: Int = 0

    fun getNames(): String {
        return name.toString()
    }

    fun setNames(name: String) {
        this.name = name
    }

    fun getPrices(): Int {
        return price.toString().toInt()
    }

    fun setPrices(prices: Int) {
        this.price = prices
    }

    fun getStocks(): Int {
        return stock.toString().toInt()
    }

    fun setStocks(stock: Int) {
        this.stock = stock
    }

    fun getIds(): Int {
        return id
    }

    fun setIds(id: Int) {
        this.id = id
    }
}
