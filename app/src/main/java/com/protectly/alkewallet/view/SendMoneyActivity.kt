package com.protectly.alkewallet.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.protectly.alkewallet.databinding.ActivitySendMoneyBinding
import com.protectly.alkewallet.viewmodel.SendMoneyViewModel

class SendMoneyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySendMoneyBinding
    private lateinit var sendMoneyViewModel: SendMoneyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sendMoneyViewModel = ViewModelProvider(this).get(SendMoneyViewModel::class.java)

        binding.btnGoToHome.setOnClickListener { goToHomePage() }

        binding.btnSend.setOnClickListener {
            val concept = binding.editTextConcept.text.toString()
            val amountText = binding.editTextAmount.text.toString()

            if (concept.isNotEmpty() && amountText.isNotEmpty()) {
                val amount = amountText.toDoubleOrNull()
                if (amount != null) {
                    sendMoneyViewModel.sendPayment(concept, amount)
                } else {
                    Toast.makeText(this, "Por favor, ingrese un monto válido", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, ingrese todos los datos", Toast.LENGTH_SHORT).show()
            }
        }

        sendMoneyViewModel.paymentResultLiveData.observe(this) { paymentResponse ->
            if (paymentResponse != null && paymentResponse.message == "OK") {
                Toast.makeText(this, "Transferencia realizada", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomePageActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Error al enviar el pago", Toast.LENGTH_SHORT).show()
            }
        }

        sendMoneyViewModel.errorMessageLiveData.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun goToHomePage() {
        val intent = Intent(this, HomePageActivity::class.java)
        startActivity(intent)
        finish()
    }
}
