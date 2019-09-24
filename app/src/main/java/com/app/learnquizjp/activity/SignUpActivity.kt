package com.app.learnquizjp.activity

import android.os.Bundle
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*
import android.widget.Toast
import android.text.TextUtils
import android.view.View
import com.app.learnquizjp.R

/**
 * A login screen that offers login via email/password.
 */
class SignUpActivity : AppCompatActivity() {
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.app.learnquizjp.R.layout.activity_sign_up)
        var auth : FirebaseAuth = FirebaseAuth.getInstance()

        btnRegister.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View) {
                val email = edtEmail.text.toString().trim()
                val password = edtPassword.text.toString().trim()
                var re_password = edtRePassword.text.toString().trim()

                if (TextUtils.isEmpty(email)) {
                    edtEmail.error = getString(R.string.notify_empty_email)
                    return
                }
                if (TextUtils.isEmpty(password)) {
                    edtPassword.error = getString(R.string.notify_empty_pass)
                    return
                }
                if(TextUtils.isEmpty(re_password) || re_password != password){
                    edtRePassword.error = getString(R.string.signup_password_not_match)
                    return
                }
                if (password.length < 6) {
                    edtPassword.error = getString(R.string.notify_length_pass)
                    return
                }

                progressBar.visibility = View.VISIBLE
                //create user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this@SignUpActivity) { task ->
                    Toast.makeText(
                        this@SignUpActivity,
                        getString(R.string.register_successful) + task.isSuccessful,
                        Toast.LENGTH_SHORT
                    ).show()
                    progressBar.visibility = View.GONE
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful) {
                        Toast.makeText(
                            this@SignUpActivity, "Authentication failed." + task.exception!!,
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        startActivity(Intent(this@SignUpActivity, HomeActivity::class.java))
                        finish()
                    }
                }
            }
        })
        tvLogin.setOnClickListener {
            startActivity(Intent(this@SignUpActivity,LoginActivity::class.java))
            finish()
        }
    }

}