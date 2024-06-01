package com.protectly.alkewallet.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.protectly.alkewallet.R
import com.protectly.alkewallet.databinding.ActivityHomePageBinding
import com.protectly.alkewallet.databinding.DialogNoAccountBinding
import com.protectly.alkewallet.GlobalClassApp
import com.protectly.alkewallet.model.Transaction
import com.protectly.alkewallet.view.adapter.ListTransactionAdapter
import com.protectly.alkewallet.viewmodel.AssignAccountViewModel
import com.protectly.alkewallet.viewmodel.HomeViewModel

class HomePageActivity : AppCompatActivity() {

    val TAG = "HomePageActivity"

    // Instanciamos viewBinding
    private lateinit var binding: ActivityHomePageBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var assignAccountViewModel: AssignAccountViewModel

    lateinit var linearLayoutEmpty: LinearLayout
    lateinit var linearLayoutTransactions: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Iniciamos viewbinding
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializamos HomeViewModel
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        assignAccountViewModel = ViewModelProvider(this).get(AssignAccountViewModel::class.java)

        // ID para linear layout
        linearLayoutEmpty = findViewById(R.id.emptylayout_hp)
        // linearLayoutTransactions = findViewById(R.id.transactionlayout_hp)

        // Obtener el nombre del usuario desde GlobalClassApp
        val userName = "Hola, ${GlobalClassApp.userLogged?.first_name} ${GlobalClassApp.userLogged?.last_name}"
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
            } else {
                Toast.makeText(this, "Error al asignar cuenta", Toast.LENGTH_SHORT).show()
            }
        }

        assignAccountViewModel.errorMessageLiveData.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }

        // Verificar la cuenta del usuario
        homeViewModel.checkUserAccount()

        // Creamos la lista de usuarios y transacciones
        val transacciones = arrayOf(
            Transaction("Pedro", "Picapiedra", "22-05-2024", "$100.00", "https://i.ibb.co/XpXGQRv/user-1.png"),
            Transaction("John", "Doe", "2024-05-20", "$100.00", "https://i.ibb.co/8Kr66KJ/user-2.png"),
            Transaction("Jane", "Smith", "2024-05-21", "$200.00", "https://i.ibb.co/k2ZWbh7/user-3.png"),
            Transaction("Alice", "Johnson", "2024-05-22", "-$150.00", "https://i.ibb.co/QbmNchQ/user-4.png"),
            Transaction("Bob", "Brown", "2024-05-23", "$250.00", "https://i.ibb.co/1vFsvdF/user-5.png"),
            Transaction("Charlie", "Davis", "2024-05-24", "$300.00", "https://randomuser.me/api/portraits/men/43.jpg"),
            Transaction("Pedro", "Picapiedra", "22-05-2024", "$100.00", "https://randomuser.me/api/portraits/men/1.jpg"),
            Transaction("John", "Doe", "2024-05-20", "-$100.00", "https://randomuser.me/api/portraits/women/3.jpg"),
            Transaction("Jane", "Smith", "2024-05-21", "$200.00", "https://randomuser.me/api/portraits/women/13.jpg")
        )

        // Crear el adaptador
        val adapter = ListTransactionAdapter(transacciones.toList())
        binding.recyclerListUser.adapter = adapter
        binding.recyclerListUser.layoutManager = LinearLayoutManager(this)
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
