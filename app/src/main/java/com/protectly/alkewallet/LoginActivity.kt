package com.protectly.alkewallet

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.PatternsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.protectly.alkewallet.databinding.ActivityLoginBinding


class LoginActivity : AppCompatActivity() {

    val TAG = "LoginActivity"

    //declaramos viewbinding
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //iniciamos viewbinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //declaramos las variables para email y su visor de error
        val email = findViewById<TextInputEditText>(R.id.textFieldEmail)
        val errorEmailTF = findViewById<TextInputLayout>(R.id.textFieldEmailError)
        //lo mismo para el password
        val pass = findViewById<TextInputEditText>(R.id.textFieldPass)
        val errorPassTF = findViewById<TextInputLayout>(R.id.textFieldPassError)

        //btn ir registro
        binding.btngotocreateacc.setOnClickListener { goToRegisterAcc() }


        //btn loguearse
        binding.btnLoginL.setOnClickListener {
            if (validateEmail(email, errorEmailTF)&& validatePass(pass, errorPassTF)){
                goToHomePage()
            }else {
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
            }
        }
    }




    //funcion del boton ir a registro
    private fun goToRegisterAcc() {
        val i = Intent(this, SignUpActivity::class.java)
        startActivity(i)
    }

    private fun goToHomePage() {
        val i = Intent(this, HomePageActivity::class.java)
        i.putExtra("LOGIN_USER", "loginuser")
        startActivity(i)
    }

    //Validacion para el email
    private fun validateEmail(email: TextInputEditText, email2: TextInputLayout) : Boolean {
        val emailText = email.text.toString().trim()
        return when {
            email.text.toString().trim().isEmpty() -> {
                email2.error = "Requerido"
                currentFocus
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(emailText).matches() -> {
                email2.error = "Correo no valido"
                false
            }
            else -> {
                if (!PatternsCompat.EMAIL_ADDRESS.matcher(emailText).matches()) {
                    email2.error = "Escriba un correo valido"
                    false
                } else {
                    email2.error = null
                    true
                }
            }
        }
    }

    //validacion para el password
    private fun validatePass(pass: TextInputEditText, errorPassTF: TextInputLayout): Boolean {
        val passText = pass.text.toString().trim()
        return when {
            passText.isEmpty() -> {
                errorPassTF.error = "Contraseña requerida"
                false
            }
            else -> {
                errorPassTF.error = null
                true
            }
        }

    }


}