package com.example.s9dpafirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.s9dpafirebase.R
import com.example.s9dpafirebase.models.CourseModel
import com.example.s9dpafirebase.models.UserModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_user)

        val txtEmailReg:EditText = findViewById(R.id.txtEmailReg)
        val txtPasswordReg:EditText = findViewById(R.id.txtPasswordReg)
        val txtFullNameReg:EditText = findViewById(R.id.txtFullNameReg)
        val btnRegister:Button = findViewById(R.id.btnRegister)
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("users")

        btnRegister.setOnClickListener{
            val correo = txtEmailReg.text.toString()
            val password = txtPasswordReg.text.toString()
            val fullname = txtFullNameReg.text.toString()

            if (correo.isEmpty() || password.isEmpty() || fullname.isEmpty()) {
                Snackbar
                    .make(findViewById(android.R.id.content)
                        ,"Debe ingresar los datos completos"
                        , Snackbar.LENGTH_LONG).show()
            } else {
                auth
                    .createUserWithEmailAndPassword(correo,password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful){
                            val nuevoUser = UserModel(correo,fullname)
                            collectionRef.add(nuevoUser)
                                .addOnSuccessListener { documentReference ->
                                    Snackbar
                                        .make(findViewById(android.R.id.content)
                                            ,"Registro exitoso ID: ${documentReference.id}"
                                            , Snackbar.LENGTH_LONG).show()
                                }
                                .addOnFailureListener{ error ->
                                    Snackbar
                                        .make(findViewById(android.R.id.content)
                                            ,"Ocurrió un error: $error"
                                            , Snackbar.LENGTH_LONG).show()
                                }
                            startActivity(Intent(this,LoginActivity::class.java))
                        } else{
                            Snackbar
                                .make(findViewById(android.R.id.content)
                                    ,"Ocurrió un error en el registro"
                                    , Snackbar.LENGTH_LONG).show()
                        }

                    }
            }


        }

    }
}