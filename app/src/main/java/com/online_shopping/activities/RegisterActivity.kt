package com.online_shopping.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.online_shopping.MainActivity
import com.online_shopping.databinding.ActivityRegisterBinding
import com.online_shopping.models.RegisterModel
import com.online_shopping.models.UserModel
import com.online_shopping.services.Service
import com.online_shopping.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    companion object{
        var userRegLogId = ""
        fun getRegUserId():String {
            return userRegLogId
        }
    }
    private lateinit var bind: ActivityRegisterBinding
    val service = ApiClient.getClient().create(Service::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.getSupportActionBar()?.hide()
        bind = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(bind.root)
        nameCheck()
        surnameCheck()
        phoneCheck()
        emailCheck()
        passCheck()
        bind.btnReg.setOnClickListener {
            registerCheck()
        }
    }
    private fun getUid(mail:String,pass:String){
        var dataService =service.userLogin(mail,pass)
        dataService.enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful) {
                    val u = response.body()
                    if (u?.user!!.get(0)!!.durum == true){
                        var userId = u.user!![0]!!.bilgiler!!.userId.toString()
                        if(userId != null) {
                            userRegLogId = userId
                        }
                    }
                }
            }
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "$t", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun registerCheck() {
        var nameCheck = bind.nameLayout.helperText == null
        var surCheck = bind.surNLayout.helperText == null
        var phoneCheck = bind.tvPhoneLayout.helperText == null
        var mailCheck = bind.tvMailRegLayout.helperText == null
        var passCheck = bind.PassRegLayout.helperText == null
        if (nameCheck && surCheck && phoneCheck && mailCheck && passCheck) {
            val userName = bind.etRegName.text.toString()
            val userSurname = bind.etSurname.text.toString()
            val userPhone = bind.etPhone.text.toString()
            val userMail = bind.etMail.text.toString()
            val userPass = bind.etRegPass.text.toString()
            register(userName, userSurname, userPhone, userMail, userPass)
        } else {
            Toast.makeText(this, "Invalid input. Check Again", Toast.LENGTH_SHORT).show()
        }
    }
    fun register(
        userName: String,
        userSurname: String,
        userPhone: String,
        userMail: String,
        userPass: String) {
        var dataService = service.userRegister(userName, userSurname, userPhone, userMail, userPass)
        dataService.enqueue(object : Callback<RegisterModel> {
            override fun onResponse(
                call: Call<RegisterModel>,
                response: Response<RegisterModel>
            ) {
                if (response.isSuccessful) {
                    val u = response.body()
                    if (u?.user!!.get(0)!!.durum == true) {
                        Toast.makeText(this@RegisterActivity, "${u.user[0].mesaj}", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        getUid(userMail,userPass)
                        startActivity(intent)
                        finishAffinity()
                    }
                    else {
                        Toast.makeText(this@RegisterActivity, "${u.user[0].mesaj}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<RegisterModel>, t: Throwable) {
                Toast.makeText(this@RegisterActivity, "$t", Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun nameCheck() {
        bind.etRegName.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                bind.nameLayout.helperText = validName()
            }
        }
    }
    private fun validName(): String? {
        val nameTxt = bind.etRegName.text.toString()
        if (nameTxt.isEmpty()) {
            return "Invalid Name"
        }
        return null
    }

    private fun surnameCheck() {
        bind.etSurname.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                bind.surNLayout.helperText = validSurname()
            }
        }
    }

    private fun validSurname(): String? {
        val surnameTxt = bind.etSurname.text.toString()
        if (surnameTxt.isEmpty()) {
            return "Invalid Surname"
        }
        return null
    }

    private fun phoneCheck() {
        bind.etPhone.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                bind.tvPhoneLayout.helperText = validPhone()
            }
        }
    }

    private fun validPhone(): String? {
        val emailTxt = bind.etPhone.text.toString()
        if (!Patterns.PHONE.matcher(emailTxt).matches()) {
            return "Invalid Phone"
        }
        return null
    }

    private fun emailCheck() {
        bind.etMail.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                bind.tvMailRegLayout.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailTxt = bind.etMail.text.toString()
        if (!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()) {
            return "Invalid Email"
        }
        return null
    }

    private fun passCheck() {
        bind.etRegPass.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                bind.PassRegLayout.helperText = validPassword()
            }
        }
    }

    private fun validPassword(): String? {
        val passwordTxt = bind.etRegPass.text.toString()
        if (passwordTxt.length < 8) {
            return "At least 8 characters"
        }
        // must one upper character
        if (!passwordTxt.matches(".*[0-9].*".toRegex())) {
            return "At least 1 number characters"
        }
        return null
    }
}