package com.example.crudproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class LogInActivity : AppCompatActivity() {

    private var inemail:EditText?=null
    private var inpassword:EditText?=null
    private var btnIn: Button?=null
    private var databaseHandler: DataBaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        databaseHandler= DataBaseHandler(this)

        inemail=findViewById(R.id.inEmail) as EditText
        inpassword=findViewById(R.id.inPassword) as EditText
        btnIn=findViewById(R.id.btn_in) as Button

        btnIn!!.setOnClickListener {
            if(inemail!!.text.toString().length>0 && inpassword!!.text.toString().length>0){
                var user=databaseHandler!!.logIn(inemail!!.text.toString(),inpassword!!.text.toString())
                if(inemail!!.text.toString().equals("admin") && inpassword!!.text.toString().equals("admin1")){
                    val intent = Intent(applicationContext, AdminActivity::class.java)
                    startActivity(intent)
                }else if(user!=null){
                    val intent = Intent(applicationContext, UserActivity::class.java)
                    intent.putExtra("userId", user.toString())
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "You haven't registered, Please Sign Up !", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Please fill all data's", Toast.LENGTH_SHORT).show()
            }
        }
        in_signup.setOnClickListener({
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        })
    }
    override fun onBackPressed() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }
}
