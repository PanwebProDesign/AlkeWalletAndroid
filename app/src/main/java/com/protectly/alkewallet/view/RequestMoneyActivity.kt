package com.protectly.alkewallet.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.protectly.alkewallet.GlobalClassApp
import com.protectly.alkewallet.databinding.ActivityRequestMoneyBinding
import com.protectly.alkewallet.viewmodel.RequestMoneyViewModel

class RequestMoneyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRequestMoneyBinding
    private lateinit var requestMoneyViewModel: RequestMoneyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestMoneyViewModel = ViewModelProvider(this).get(RequestMoneyViewModel::class.java)

        // Obtener el nombre del usuario desde GlobalClassApp
        val userName = "${GlobalClassApp.userLogged?.first_name} ${GlobalClassApp.userLogged?.last_name}"
        binding.userName.text = userName
        val userEmail = "${GlobalClassApp.userLogged?.email}"
        binding.userEmail.text = userEmail

        binding.btnBackToHome.setOnClickListener { goToHomePage() }

        binding.btnRequest.setOnClickListener {
            val concept = binding.editTextConcept.text.toString()
            val amountText = binding.editTextAmount.text.toString()

            if (concept.isNotEmpty() && amountText.isNotEmpty()) {
                val amount = amountText.toDoubleOrNull()
                val accountId = GlobalClassApp.userAccount?.id // Aquí se debe obtener el accountId
                if (amount != null && accountId != null) {
                    requestMoneyViewModel.requestMoney(accountId, concept, amount)
                } else {
                    Toast.makeText(this, "Por favor, ingrese un monto válido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, ingrese todos los datos", Toast.LENGTH_SHORT).show()
            }
        }

        requestMoneyViewModel.topupResultLiveData.observe(this) { paymentResponse ->
            if (paymentResponse != null && paymentResponse.status == 200) {
                Toast.makeText(this, "Depósito realizado: ${paymentResponse.error}", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomePageActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error al realizar el depósito", Toast.LENGTH_SHORT).show()
            }
        }

        requestMoneyViewModel.errorMessageLiveData.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToHomePage() {
        val intent = Intent(this, HomePageActivity::class.java)
        startActivity(intent)
        finish()
    }
}
