package com.example.crudproject
import java.io.Serializable

class UserModel : Serializable {

    var name: String? = null
    var email: String? = null
    var password: String? = null
    var id: Int = 0

    fun getNames(): String {
        return name.toString()
    }

    fun setNames(name: String) {
        this.name = name
    }

    fun getEmails(): String {
        return email.toString()
    }

    fun setEmails(email: String) {
        this.email = email
    }

    fun getPasswords(): String {
        return password.toString()
    }

    fun setPasswords(password: String) {
        this.password = password
    }

    fun getIds(): Int {
        return id
    }

    fun setIds(id: Int) {
        this.id = id
    }


}
