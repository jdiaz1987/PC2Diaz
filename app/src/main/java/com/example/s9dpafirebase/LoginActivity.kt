package com.example.s9dpafirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var auth = FirebaseAuth.getInstance()
        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val txtPassword = findViewById<EditText>(R.id.txtPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val txtCreateAccount = findViewById<TextView>(R.id.txtCreateAccount)

        btnLogin.setOnClickListener{
            val correo = txtEmail.text.toString()
            val clave = txtPassword.text.toString()

            auth.signInWithEmailAndPassword(correo,clave)
                .addOnCompleteListener(this){task->
                    if(task.isSuccessful){
                        startActivity(Intent(this,PrincipalActivity::class.java))
                    }else{
                        Snackbar
                            .make(findViewById(android.R.id.content)
                                ,"Las credenciales son inválidas..."
                                , Snackbar.LENGTH_LONG).show()
                    }
                }
        }

        txtCreateAccount.setOnClickListener{
            startActivity(Intent(this,PrincipalActivity::class.java))
        }


    }
}