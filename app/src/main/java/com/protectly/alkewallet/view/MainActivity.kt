package com.protectly.alkewallet.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.protectly.alkewallet.R
import com.protectly.alkewallet.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    //Definimos el tag para la consola
    val TAG = "MainActivityy"

    //declaramos viewBinding
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        //antes del oncreate se pone el spash
        val screenSplash = installSplashScreen()
        super.onCreate(savedInstanceState)

        //iniciamos viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        val i = Intent(this, RegisterActivity::class.java)
        startActivity(i)
    }


}