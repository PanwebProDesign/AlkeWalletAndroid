package com.protectly.alkewallet.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.core.util.PatternsCompat
import com.protectly.alkewallet.databinding.ActivityLoginBinding
import com.protectly.alkewallet.viewmodel.LoginViewModel
import com.protectly.alkewallet.viewmodel.LoginViewModelFactory
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModelFactory = LoginViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)

        val sharedPreferences = getSharedPreferences("AlkeWallet", MODE_PRIVATE)
        val emailIngresado = sharedPreferences.getString("email", null)
        if (emailIngresado != null) {
            binding.textFieldEmail.setText(emailIngresado)
        }

        binding.btngotocreateacc.setOnClickListener { goToRegisterAcc() }
        binding.btnLoginL.setOnClickListener { login() }

        viewModel.loginResultLiveData.observe(this) { loginOk ->
            if (loginOk) {
                goToHomePage()
            } else {
                Toast.makeText(this, "Error en el login", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login() {
        val emailIngresado = binding.textFieldEmail.text.toString()
        val passIngresado = binding.textFieldPass.text.toString()

        if (validateEmail(binding.textFieldEmail, binding.textFieldEmailError) &&
            validatePass(binding.textFieldPass, binding.textFieldPassError)) {

            val sharedPreferences = getSharedPreferences("AlkeWallet", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("email", emailIngresado)
            editor.apply()

            // Llamar al método del ViewModel para realizar el login
            viewModel.login(emailIngresado, passIngresado)
            Log.d("prueba", "Email: $emailIngresado, Password: $passIngresado")


        } else {
            Toast.makeText(this, "Error en los datos de entrada", Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToRegisterAcc() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }

    private fun goToHomePage() {
        val intent = Intent(this, HomePageActivity::class.java)
        intent.putExtra("LOGIN_USER", "loginuser")
        startActivity(intent)
    }

    private fun validateEmail(email: TextInputEditText, email2: TextInputLayout): Boolean {
        val emailText = email.text.toString().trim()
        return when {
            emailText.isEmpty() -> {
                email2.error = "Requerido"
                false
            }
            !PatternsCompat.EMAIL_ADDRESS.matcher(emailText).matches() -> {
                email2.error = "Correo no válido"
                false
            }
            else -> {
                email2.error = null
                true
            }
        }
    }

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
