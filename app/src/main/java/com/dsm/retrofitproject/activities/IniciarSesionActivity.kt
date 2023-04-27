package com.dsm.retrofitproject.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.dsm.retrofitproject.R
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class IniciarSesionActivity : AppCompatActivity() {
    private var emailTV: EditText? = null
    private var passwordTV: EditText? = null
    private var loginBtn: Button? = null
    private var registrarBtn: Button? = null
    private var progressBar: ProgressBar? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_iniciar_sesion)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        mAuth = FirebaseAuth.getInstance()
        initializeUI()
        loginBtn!!.setOnClickListener{
            loginUserAccount()
        }
        registrarBtn!!.setOnClickListener{
            val intent = Intent(this@IniciarSesionActivity, RegistrarseActivity::class.java)
            startActivity(intent)
        }
    }
    private fun loginUserAccount(){
        progressBar?.setVisibility(View.VISIBLE)
        val email:String
        val psswd:String
        email=emailTV?.getText().toString()
        psswd=passwordTV?.getText().toString()
        if(TextUtils.isEmpty(email)){
            emailTV?.error = "Ingrese su correo electrónico"
            emailTV?.requestFocus()
            progressBar?.setVisibility(View.GONE)
            return
        }
        if(TextUtils.isEmpty(psswd)){
            passwordTV?.error = "Ingrese una contraseña"

            passwordTV?.requestFocus()
            progressBar?.setVisibility(View.GONE)
            return
        }
        mAuth?.signInWithEmailAndPassword(email,psswd)?.addOnCompleteListener {
                task ->
            if(task.isSuccessful){
                Toast.makeText(  this, "Inicio de sesión satisfactorio", Toast.LENGTH_LONG).show()

                progressBar?.setVisibility(View.GONE)
                val intent = Intent(this@IniciarSesionActivity, MainActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(  this, "Fallo el inicio de sesión, por favor intenta más tarde",
                    Toast.LENGTH_LONG).show()

                progressBar?.setVisibility(View.GONE)
            }
        }

    }
    private fun initializeUI() {
        emailTV = findViewById<EditText>(R.id.etUsuario)
        passwordTV = findViewById<EditText>(R.id.etContra)
        registrarBtn = findViewById<Button>(R.id.RegistrarseButton)
        loginBtn = findViewById<Button>(R.id.btnIniciarSesion)
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
    }
}
