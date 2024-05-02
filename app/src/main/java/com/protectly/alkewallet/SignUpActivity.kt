package com.protectly.alkewallet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SignUpActivity : AppCompatActivity() {

    //Definimos el tag para la consola
    val TAG = "SignUpActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //declaramos el boton directamente
        var btnRegisterToLogin = findViewById<TextView>(R.id.btn_register_to_login)
        var btnRegister = findViewById<Button>(R.id.btn_register_r)

        btnRegisterToLogin.setOnClickListener { goToRegisterToLogin() }
        btnRegister.setOnClickListener { register() }


    }

    private fun register() {
        val i = Intent(this, HomePageActivity::class.java)
        i.putExtra("NEW_USER", "newuser")
        startActivity(i)
    }

    private fun goToRegisterToLogin() {
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
    }


}