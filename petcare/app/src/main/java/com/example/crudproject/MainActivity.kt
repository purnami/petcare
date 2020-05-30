package com.example.crudproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var backPressedTime : Long =0
    private var backToast: Toast? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        in_btn.setOnClickListener({
            val intent = Intent(applicationContext, LogInActivity::class.java)
            startActivity(intent)
        })

        up_btn.setOnClickListener({
            val intent = Intent(applicationContext, SignUpActivity::class.java)
            startActivity(intent)
        })
    }
    override fun onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast?.cancel()
            moveTaskToBack(true)
        }else{
            backToast= Toast.makeText(baseContext,"Press again to exit", Toast.LENGTH_SHORT)
            backToast?.show()
        }
        backPressedTime = System.currentTimeMillis()

    }
}
