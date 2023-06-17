package com.example.s9dpafirebase.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.findFragment
import com.example.s9dpafirebase.R
import com.example.s9dpafirebase.models.CourseModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_registro, container, false)

        val txtCurso: EditText = view.findViewById(R.id.txtCurso)
        val txtNota: EditText = view.findViewById(R.id.txtNota)
        val btnGuardarCurso: Button = view.findViewById(R.id.btnGuardarCurso)
        val db = FirebaseFirestore.getInstance()
        val collectionRef = db.collection("courses")
//        val auth = FirebaseAuth.getInstance()

        btnGuardarCurso.setOnClickListener {

//            auth
//                .createUserWithEmailAndPassword("prueba@test.com","12345678")
//                .addOnSuccessListener { obj ->
//                }

            val curso = txtCurso.text.toString()
            val nota = txtNota.text.toString()
            val nuevoCurso = CourseModel(curso,nota)
            collectionRef.add(nuevoCurso)
                .addOnSuccessListener { documentReference ->
                    Snackbar
                        .make(view
                            ,"Registro exitoso ID: ${documentReference.id}"
                            , Snackbar.LENGTH_LONG).show()
                }
                .addOnFailureListener{ error ->
                    Snackbar
                        .make(view
                            ,"Ocurrió un error: $error"
                            , Snackbar.LENGTH_LONG).show()
                }
        }
        return view
    }
}