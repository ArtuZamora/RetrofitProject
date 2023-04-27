package com.dsm.retrofitproject.activities

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.dsm.retrofitproject.R
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class RegistrarseActivity : AppCompatActivity() {
    private var emailTV: EditText? = null
    private var passwordTV: EditText? = null
    private var passwordTVR: EditText? = null
    private var signup: Button? = null
    private var signinScreen: Button? = null
    private var progressBar: ProgressBar? = null

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_registrarse)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_previous)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        mAuth= FirebaseAuth.getInstance()
        initializeUI()


        signup!!.setOnClickListener { registerNewUser() }
        signinScreen!!.setOnClickListener {  val intent = Intent(this@RegistrarseActivity, IniciarSesionActivity::class.java)
            startActivity(intent)

        }
    }
    private fun registerNewUser(){
        progressBar!!.visibility= View.VISIBLE
        val email:String
        val psswd:String
        val psswdR:String
        email=emailTV!!.text.toString()
        psswd=passwordTV!!.text.toString()
        psswdR=passwordTVR!!.text.toString()
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
        if(TextUtils.isEmpty(psswdR)){
            passwordTVR?.error = "Ingrese el valor de repetir contraseña"
            passwordTVR?.requestFocus()
            progressBar?.setVisibility(View.GONE)

            return
        }

        mAuth!!.createUserWithEmailAndPassword(email, psswd).addOnCompleteListener {
                task ->
            if(task.isSuccessful){
                Toast.makeText(  this, "Registro satisfactorio", Toast.LENGTH_LONG).show()
                Log.d(ContentValues.TAG, "User locale updated successfully")
                progressBar!!.setVisibility(View.GONE)
                val intent = Intent(this@RegistrarseActivity, IniciarSesionActivity::class.java)
                startActivity(intent)

            }
            else{
                Toast.makeText(  this, "El registro falló, intente más tarde", Toast.LENGTH_LONG).show()
                Log.e(ContentValues.TAG, "Error updating user locale: ${task.exception}")
                progressBar!!.setVisibility(View.GONE)


            }
        }

    }

    private fun initializeUI() {
        emailTV = findViewById(R.id.etUsuarioR)
        passwordTV = findViewById(R.id.etContraR)
        passwordTVR = findViewById(R.id.etRepetirContraR)
        signup = findViewById(R.id.RegistrarseButton)
        signinScreen=findViewById(R.id.IniciarSesionScreen)
        progressBar = findViewById(R.id.progressBar)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here
        return when (item.itemId) {
            android.R.id.home -> {
                // Navigate to the previous activity
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}