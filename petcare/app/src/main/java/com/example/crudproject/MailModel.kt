package com.example.crudproject

import java.io.Serializable

class MailModel : Serializable {
    var id: Int=0
    var userId: Int=0
    var message: String?=null
    var date: String?=null

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

    fun getMessages(): String {
        return message.toString()
    }

    fun setMessages(message: String) {
        this.message = message
    }

    fun getDates(): String {
        return date.toString()
    }

    fun setDates(date: String) {
        this.date = date
    }
}