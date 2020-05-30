package com.example.crudproject

class User {
    var id: Int=0
    var name: String=""
    var email: String=""
    var password: String=""

    constructor(name:String, email:String, password:String){
        this.name=name
        this.email=email
        this.password=password
    }

    constructor(){

    }
}