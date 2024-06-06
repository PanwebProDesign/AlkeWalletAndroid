package com.protectly.alkewallet.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.protectly.alkewallet.R
import com.protectly.alkewallet.databinding.ActivityHomePageBinding
import com.protectly.alkewallet.databinding.DialogNoAccountBinding
import com.protectly.alkewallet.GlobalClassApp
import com.protectly.alkewallet.view.adapter.ListTransactionAdapter
import com.protectly.alkewallet.viewmodel.AssignAccountViewModel
import com.protectly.alkewallet.viewmodel.HomeViewModel
import com.protectly.alkewallet.viewmodel.TransactionViewModel

class HomePageActivity : AppCompatActivity() {

    val TAG = "HomePageActivity"

    // Instanciamos viewBinding
    private lateinit var binding: ActivityHomePageBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var assignAccountViewModel: AssignAccountViewModel
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Iniciamos viewbinding
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializamos HomeViewModel
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        assignAccountViewModel = ViewModelProvider(this).get(AssignAccountViewModel::class.java)
        transactionViewModel = ViewModelProvider(this).get(TransactionViewModel::class.java)

        // Inicializar layouts
        val linearLayoutEmpty = binding.emptylayoutHp
        val linearLayoutTransactions = binding.transactionlayoutHp

        // Obtener el nombre del usuario desde GlobalClassApp
        val userName = "Hola, ${GlobalClassApp.userLogged?.first_name}"
        binding.userNameHome.text = userName

        binding.send1.setOnClickListener { goToSendMoney() }
        binding.add1.setOnClickListener { goToAddMoney() }
        binding.gotoProfile.setOnClickListener { goToProfile() }

        // Observamos los resultados de la verificación de la cuenta
        homeViewModel.accountCheckLiveData.observe(this) { hasAccount ->
            if (hasAccount) {
                Toast.makeText(this, "Sí hay cuenta", Toast.LENGTH_SHORT).show()
            } else {
                showNoAccountDialog()
            }
        }

        homeViewModel.userBalanceLiveData.observe(this) { balance ->
            binding.userSaldo.text = "$$balance"
        }

        homeViewModel.errorMessageLiveData.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        assignAccountViewModel.accountCreatedLiveData.observe(this) { accountCreated ->
            if (accountCreated) {
                Toast.makeText(this, "Cuenta asignada exitosamente", Toast.LENGTH_SHORT).show()
                //aqui volveremos a cargar el homePageActivity
                val intent = Intent(this, HomePageActivity::class.java)
                startActivity(intent)
                finish()

            } else {
                Toast.makeText(this, "Error al asignar cuenta", Toast.LENGTH_SHORT).show()
            }
        }

        assignAccountViewModel.errorMessageLiveData.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        transactionViewModel.transactionsLiveData.observe(this) { transactions ->
            if (transactions.isNotEmpty()) {
                val adapter = ListTransactionAdapter(transactions)
                binding.recyclerListUser.adapter = adapter
                binding.recyclerListUser.layoutManager = LinearLayoutManager(this)
                linearLayoutEmpty.visibility = View.GONE
                linearLayoutTransactions.visibility = View.VISIBLE
            } else {
                linearLayoutEmpty.visibility = View.VISIBLE
                linearLayoutTransactions.visibility = View.GONE
            }
        }

        transactionViewModel.errorMessageLiveData.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        // Verificar la cuenta del usuario
        homeViewModel.checkUserAccount()

        // Obtener transacciones
        transactionViewModel.fetchTransactions()
    }

    private fun showNoAccountDialog() {
        val dialogBinding = DialogNoAccountBinding.inflate(LayoutInflater.from(this))
        val builder = AlertDialog.Builder(this)
            .setView(dialogBinding.root)
            .setCancelable(false)

        val dialog = builder.create()

        dialogBinding.btnAccept.setOnClickListener {
            // Lógica para asignar una cuenta al usuario
            GlobalClassApp.userLogged?.id?.let { userId ->
                assignAccountViewModel.assignAccountToUser(userId)
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun goToProfile() {
        val i = Intent(this, ProfileActivity::class.java)
        startActivity(i)
    }

    private fun goToAddMoney() {
        val i = Intent(this, RequestMoneyActivity::class.java)
        startActivity(i)
    }

    private fun goToSendMoney() {
        val i = Intent(this, SendMoneyActivity::class.java)
        startActivity(i)
    }
}
