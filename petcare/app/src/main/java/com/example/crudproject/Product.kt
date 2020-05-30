package com.example.crudproject

class Product {
    var id: Int=0
    var name: String=""
    var prices: Int = 0
    var stock: Int=0

    constructor(name:String, prices:Int, stock:Int){
        this.name=name
        this.prices=prices
        this.stock=stock
    }
    constructor(){

    }
}