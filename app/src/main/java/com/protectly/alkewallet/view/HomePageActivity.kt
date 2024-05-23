package com.protectly.alkewallet.view

import android.content.Intent
import android.os.Bundle
import android.view.SurfaceControl
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.protectly.alkewallet.R
import com.protectly.alkewallet.adapter.ListTransactionAdapter
import com.protectly.alkewallet.databinding.ActivityHomePageBinding
import com.protectly.alkewallet.model.Transaction

class HomePageActivity : AppCompatActivity() {

    val TAG = "HomePageActivity"


    //instancioamos viewBinding
    private lateinit var binding: ActivityHomePageBinding

    lateinit var linearLayoutEmpty : LinearLayout
    lateinit var linearLayoutTransactions : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //iniciamos viewbinding
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //id para linear layout
        linearLayoutEmpty = findViewById(R.id.emptylayout_hp)
        //linearLayoutTransactions = findViewById(R.id.transactionlayout_hp)

        //creamos la variable y recivimos el dato de pantalla anterior
        //val receivedRegister : String = intent.extras?.getString("NEW_USER").orEmpty()
        //validamos de que pagina viene
        //validatePageRegister(receivedRegister)

        binding.send1.setOnClickListener { goToSendMoney() }
        binding.add1.setOnClickListener { goToAddMoney() }
        binding.gotoProfile.setOnClickListener { goToProfile()}

        //vamos a crear la lista de usuarios y transacciones
        val transacciones = arrayOf(
            Transaction("Pedro", "Picapiedra", "22-05-2024", "$100.00"),
            Transaction("John", "Doe", "2024-05-20", "$100.00"),
            Transaction("Jane", "Smith", "2024-05-21", "$200.00"),
            Transaction("Alice", "Johnson", "2024-05-22", "-$150.00"),
            Transaction("Bob", "Brown", "2024-05-23", "$250.00"),
            Transaction("Charlie", "Davis", "2024-05-24", "$300.00"),
            Transaction("Pedro", "Picapiedra", "22-05-2024", "$100.00"),
            Transaction("John", "Doe", "2024-05-20", "-$100.00"),
            Transaction("Jane", "Smith", "2024-05-21", "$200.00"),
            Transaction("Alice", "Johnson", "2024-05-22", "$150.00"),
            Transaction("Bob", "Brown", "2024-05-23", "-$250.00"),
            Transaction("Charlie", "Davis", "2024-05-24", "-$300.00")

        )

        //aqui vamos a crear el adaptador
        val adapter = ListTransactionAdapter(transacciones.toList())
        binding.recyclerListUser.adapter = adapter
        binding.recyclerListUser.layoutManager = LinearLayoutManager(this)







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

//    private fun validatePageRegister(page : String) {
//        if (page == "newuser" ){
//            Toast.makeText(this,"Registro", Toast.LENGTH_LONG).show()
//            linearLayoutEmpty.visibility = View.VISIBLE
//            linearLayoutTransactions.visibility = View.GONE
//        }
//        else {
//            Toast.makeText(this,"Login", Toast.LENGTH_LONG).show()
//            linearLayoutEmpty.visibility = View.GONE
//            linearLayoutTransactions.visibility = View.VISIBLE
//        }
//    }
}