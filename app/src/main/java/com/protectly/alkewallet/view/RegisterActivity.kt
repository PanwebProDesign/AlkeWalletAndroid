package com.protectly.alkewallet.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.protectly.alkewallet.databinding.ActivitySignUpBinding
import com.protectly.alkewallet.viewmodel.RegisterViewModel

class RegisterActivity : AppCompatActivity() {

    //Definimos el tag para la consola
    val TAG = "SignUpActivityy"
    //declaramos viewbinding
    private lateinit var binding: ActivitySignUpBinding
    //declaramos el viewModel
    private lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Definimos el viewbinding
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Inicializamos el viewModel
        viewModel = RegisterViewModel()

        binding.btnRegisterToLogin.setOnClickListener { goToRegisterToLogin() }
        binding.btnRegisterR.setOnClickListener { register() }

        // Observamos el resultado del registro
        viewModel.registerResultLiveData.observe(this) { registerOk ->
            if (registerOk) {
                goToHomePage()
            } else {
                Toast.makeText(this, "Error en el registro", Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun register() {

        val firstName = binding.textFieldFirstName.text.toString()
        val lastName = binding.textFieldLastName.text.toString()
        val email = binding.textFieldEmail.text.toString()
        val password = binding.textFieldPass.text.toString()

        // Llama al ViewModel para registrar al usuario
        viewModel.register(firstName, lastName, email, password)

        //aqui va el codigo para registrar al usuario

//        val i = Intent(this, HomePageActivity::class.java)
//        i.putExtra("NEW_USER", "newuser")
//        startActivity(i)
    }

    private fun goToHomePage() {
        val i = Intent(this, HomePageActivity::class.java)
        i.putExtra("NEW_USER", "newuser")
        startActivity(i)
    }


    private fun goToRegisterToLogin() {
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
    }


}