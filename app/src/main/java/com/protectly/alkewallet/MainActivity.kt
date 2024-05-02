package com.protectly.alkewallet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat



class MainActivity : AppCompatActivity() {

    //Definimos el tag para la consola
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {

        //antes del oncreate se pone el spash
        val screenSplash = installSplashScreen()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //****Primero tiene que quedar el splash screen
        //true muestra siempre y lo pega false carga y se va!
        screenSplash.setKeepOnScreenCondition { false }

        //Declaramos e instanciamos las variables
        val btnGoToLogin = findViewById<TextView>(R.id.goToLogin)
        val btnGoToRegister = findViewById<Button>(R.id.buttonAddAccount)

        //funcion del boton
        btnGoToLogin?.setOnClickListener { goToLogin() }
        btnGoToRegister?.setOnClickListener { goToRegister() }

    }

    //Creamos la funcion de ir al Login
    private fun goToLogin() {
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
    }

    //Creamos la funcion de ir al registro
    private fun goToRegister() {
        val i = Intent(this, SignUpActivity::class.java)
        startActivity(i)
    }


}