package com.example.crudproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private var btnUp : Button? = null
    private var upname : EditText? = null
    private var upemail : EditText? = null
    private var uppassword: EditText? = null
    private var databaseHandler: DataBaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val context = this
        databaseHandler= DataBaseHandler(this)

        btnUp = findViewById(R.id.btn_up) as Button
        upname = findViewById(R.id.upName) as EditText
        upemail = findViewById(R.id.upEmail) as EditText
        uppassword = findViewById(R.id.upPassword) as EditText

        btnUp!!.setOnClickListener{
            if(upname!!.text.toString().length>0 && upemail!!.text.toString().length>0 && uppassword!!.text.toString().length>0){
                if(uppassword!!.text.toString().length>3){
                    databaseHandler!!.signUp(upname!!.text.toString(), upemail!!.text.toString(), uppassword!!.text.toString())
                    Toast.makeText(context, "Your Email Has Been Registered", Toast.LENGTH_SHORT).show()
                    var login=databaseHandler!!.logIn(upemail!!.text.toString(), uppassword!!.text.toString())
                    val intent = Intent(applicationContext, UserActivity::class.java)
                    intent.putExtra("userId", login.toString())
                    startActivity(intent)
                }else{
                    Toast.makeText(context, "The lenght of the Password mush be more than 3", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context, "Please fill all data's", Toast.LENGTH_SHORT).show()
            }
        }
        up_login.setOnClickListener({
            val intent = Intent(applicationContext, LogInActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
    override fun onBackPressed() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}
