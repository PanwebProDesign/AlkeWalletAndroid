package com.protectly.alkewallet

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.util.PatternsCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //declaramos boton LOGIN y crear cuenta
        val btnLogin = findViewById<Button>(R.id.btngotologin_l)
        val btnGoToRegister = findViewById<TextView>(R.id.go_to_create_account)


        //declaramos las variables para email y su visor de error
        val email = findViewById<TextInputEditText>(R.id.textFieldEmail)
        val errorEmailTF = findViewById<TextInputLayout>(R.id.textFieldEmailError)
        //lo mismo para el password
        val pass = findViewById<TextInputEditText>(R.id.textFieldPass)
        val errorPassTF = findViewById<TextInputLayout>(R.id.textFieldPassError)

        //Text wacher para hacer el cambio inmediato
//        email.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, start: Int, count: Int, after: Int) {}
//            override fun onTextChanged(p0: CharSequence?, start: Int, before: Int, count: Int) {}
//            override fun afterTextChanged(s: Editable?) {
//                validateEmail(email, email2)
//            }
//        })




        //btn ir registro
        btnGoToRegister.setOnClickListener { goToRegisterAcc() }

        //btn loguearse
        btnLogin.setOnClickListener{
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