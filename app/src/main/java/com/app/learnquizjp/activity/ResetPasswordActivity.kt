package com.app.learnquizjp.activity

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_reset_password.*
import android.widget.Toast
import android.text.TextUtils
import android.view.View
import com.app.learnquizjp.R


class ResetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.app.learnquizjp.R.layout.activity_reset_password)

        var auth : FirebaseAuth = FirebaseAuth.getInstance()

        tvLogin.setOnClickListener {
            startActivity(Intent(this@ResetPasswordActivity,LoginActivity::class.java))
            finish()
        }

        btnReset.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                val email = edtEmail.text.toString().trim()
                if (TextUtils.isEmpty(email)) {
                    edtEmail.error = getString(R.string.forgot_pass_empty_email)
                    return
                }
                auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@ResetPasswordActivity, getString(R.string.forgot_pass_sent_successful), Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@ResetPasswordActivity,LoginActivity::class.java))
                    } else {
                        Toast.makeText(this@ResetPasswordActivity, getString(R.string.forgot_pass_sent_fail), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

    }
}
