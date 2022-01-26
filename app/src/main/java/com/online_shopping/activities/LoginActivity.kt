package com.online_shopping.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.online_shopping.MainActivity
import com.online_shopping.databinding.ActivityLoginBinding
import com.online_shopping.models.UserModel
import com.online_shopping.utils.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var bind:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityLoginBinding.inflate(layoutInflater)
        this.getSupportActionBar()?.hide()
        setContentView(bind.root)
        emailCheck()
        passCheck()
        bind.btnLogin.setOnClickListener {
            loginCheck()
        }
    }
    fun userRegister(view: View) {
        val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
        startActivity(intent)
    }

    fun loginCheck() {
        val emailCheck = bind.tvMailLayout.helperText == null
        val validPassword = bind.tvPassLayout.helperText == null
        if (emailCheck && validPassword) {
            val mail = bind.etMail.text.toString()
            val pass = bind.etPass.text.toString()
            login(mail, pass)
        } else {
            Toast.makeText(this, "Bilgiler Eksik", Toast.LENGTH_SHORT).show()
        }
    }
    companion object{
        var userLogId :String = ""
        fun getUserId():String {
            return userLogId
        }
    }

    private fun login(mail:String, pass:String) {
        val service =  ApiClient.getClient().create(com.online_shopping.services.Service::class.java)
        var dataService =service.userLogin(mail,pass)
        dataService.enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful) {
                    val u = response.body()
                    if (u?.user!!.get(0)!!.durum == true){
                        var userId = u.user!![0]!!.bilgiler!!.userId.toString()
                        userLogId = userId
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    }else{
                        Toast.makeText(this@LoginActivity, "Kullanici bilgileri hatali", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "$t", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun emailCheck(){
        bind.etMail.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus){
                bind.tvMailLayout.helperText = validEmail()
            }
        }
    }

    private fun validEmail(): String? {
        val emailTxt = bind.etMail.text.toString()
        if(!Patterns.EMAIL_ADDRESS.matcher(emailTxt).matches()){
            return "Invalid Email"
        }
        return null
    }
    private fun passCheck(){
        bind.etPass.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus){
                bind.tvPassLayout.helperText = validPassword()
            }
        }
    }
    private fun validPassword(): String? {
        val passwordTxt = bind.etPass.text.toString()
        if(passwordTxt.length<8){
            return "At least 8 characters"
        }
        // must one upper character
        if(!passwordTxt.matches(".*[0-9].*".toRegex())){
            return "At least 1 number characters"
        }
        return null
    }

}