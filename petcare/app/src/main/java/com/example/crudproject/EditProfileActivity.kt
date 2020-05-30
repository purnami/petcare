package com.example.crudproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class EditProfileActivity : AppCompatActivity() {

    private var databaseHandler: DataBaseHandler? = null
    private var editname: EditText?=null
    private var editemail: EditText?=null
    private var editpassword: EditText?=null
    private var btnedit: Button?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        var userId = intent.getStringExtra("userId")
        databaseHandler = DataBaseHandler(this)

        editname=findViewById(R.id.editname) as EditText
        editemail=findViewById(R.id.editemail) as EditText
        editpassword=findViewById(R.id.editpassword) as EditText
        btnedit=findViewById(R.id.btnedit) as Button

        var user=databaseHandler!!.getprofileuser(userId.toInt())
        editname!!.setText(""+user.get(0).name)
        editemail!!.setText(""+user.get(0).email)
        editpassword!!.setText(""+user.get(0).password)
        btnedit!!.setOnClickListener {
            if(editname!!.text.toString().length>0 && editemail!!.text.toString().length>0 && editpassword!!.text.toString().length>0){
                if(editpassword!!.text.toString().length>3){
                    databaseHandler!!.updateUser(userId.toInt(),editname!!.text.toString(), editemail!!.text.toString(), editpassword!!.text.toString())
                    Toast.makeText(this, "Akun anda telah di edit", Toast.LENGTH_SHORT).show()
                    val intent = Intent(applicationContext, ProfileActivity::class.java)
                    intent.putExtra("userId", userId)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Panjang password harus lebih dari 3", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Silahkan isi semua data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
