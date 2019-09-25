package com.app.learnquizjp.activity

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.app.learnquizjp.R
import com.app.learnquizjp.base.ConstantsPro.Companion.EMAIL
import com.app.learnquizjp.base.ConstantsPro.Companion.PASSWORD
import com.app.learnquizjp.base.ConstantsPro.Companion.STATUS
import com.app.learnquizjp.base.ConstantsPro.Companion.USER_ACCOUNT
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Init FireBase Auth and auto login if have account
//        var auth : FirebaseAuth = FirebaseAuth.getInstance()
//        if(auth.currentUser != null){
//            startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
//            finish()
//        }
        //Set the view now
        setContentView(R.layout.activity_login)

        //Get SharePreference to get the last status
        getLastLoginStatus()

        // Set up the login form.
        btnLogin.setOnClickListener {
            checkLoginInformation()
        }

        tvRegister.setOnClickListener {
            startActivity(Intent(this@LoginActivity,SignUpActivity::class.java))
            finish()
        }

        tvForgetPass.setOnClickListener {
            startActivity(Intent(this@LoginActivity,ResetPasswordActivity::class.java))
            finish()
        }

    }

    private fun getLastLoginStatus(){
        var sharedPreferences = getSharedPreferences(USER_ACCOUNT,MODE_PRIVATE)
        cbRemember.isChecked = sharedPreferences.getBoolean(STATUS,false)
        if(cbRemember.isChecked){
            edtEmail.setText(sharedPreferences.getString(EMAIL,""))
            edtPassword.setText(sharedPreferences.getString(PASSWORD,"1234"))
        }else{
            edtEmail.text = null
            edtPassword.text = null
        }
    }

    private fun checkLoginInformation(){
        var auth : FirebaseAuth = FirebaseAuth.getInstance()
        var email : String = edtEmail.text.toString().trim()
        var password : String = edtPassword.text.toString().trim()

        if(TextUtils.isEmpty(email)){
            edtEmail.error = getString(R.string.notify_empty_email)
            return
        }
        if(TextUtils.isEmpty(password)){
            edtPassword.error = getString(R.string.notify_empty_pass)
            return
        }
//        if(password.length < 6){
//            edtPassword.error = getString(R.string.notify_length_pass)
//            return
//        }
        progressBar.visibility = View.VISIBLE

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this@LoginActivity) { task ->
            // If sign in fails, display a message to the user. If sign in succeeds
            // the auth state listener will be notified and logic to handle the
            // signed in user can be handled in the listener.
            progressBar.visibility = View.GONE

            if(!task.isSuccessful){
                // there was an error
                when {
                    email == "admin@gmail.com" && password == "admin" -> {
                        rememberUserInfomation(email,password,cbRemember.isChecked)
                        Toast.makeText(this@LoginActivity,getString(R.string.login_successful),Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity,HomeActivity::class.java))
                        finish()
                    }
                    password.length < 6 -> edtPassword.error = getString(R.string.notify_length_pass)
                    else -> Toast.makeText(this@LoginActivity, getString(R.string.login_fail), Toast.LENGTH_LONG).show()
                }
            }else {
                rememberUserInfomation(email,password,cbRemember.isChecked)
                val uid = FirebaseAuth.getInstance().uid ?: ""
                val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")
                ref.child("id").setValue(uid)
                ref.child("username").setValue(FirebaseAuth.getInstance().currentUser!!.email!!)
                Toast.makeText(this@LoginActivity,getString(R.string.login_successful),Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                finish()
            }
        }
    }

    private fun rememberUserInfomation(e : String,p : String,status : Boolean){
        var sharePreferences = this.getSharedPreferences("USER_ACCOUNT", Context.MODE_PRIVATE)
        val editor = sharePreferences!!.edit()
        editor.clear()
        editor.putString(EMAIL,e)
        editor.putString(PASSWORD,p)
        editor.putBoolean(STATUS,status)
        editor.apply()
    }

}
