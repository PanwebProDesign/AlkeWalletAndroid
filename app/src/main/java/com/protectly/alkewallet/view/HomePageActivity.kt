package com.protectly.alkewallet.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.protectly.alkewallet.R
import com.protectly.alkewallet.databinding.ActivityHomePageBinding

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
        linearLayoutTransactions = findViewById(R.id.transactionlayout_hp)

        //creamos la variable y recivimos el dato de pantalla anterior
        val receivedRegister : String = intent.extras?.getString("NEW_USER").orEmpty()
        //validamos de que pagina viene
        validatePageRegister(receivedRegister)

        binding.send1.setOnClickListener { goToSendMoney() }
        binding.add1.setOnClickListener { goToAddMoney() }
        binding.gotoProfile.setOnClickListener { goToProfile()}
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

    private fun validatePageRegister(page : String) {
        if (page == "newuser" ){
            Toast.makeText(this,"Registro", Toast.LENGTH_LONG).show()
            linearLayoutEmpty.visibility = View.VISIBLE
            linearLayoutTransactions.visibility = View.GONE
        }
        else {
            Toast.makeText(this,"Login", Toast.LENGTH_LONG).show()
            linearLayoutEmpty.visibility = View.GONE
            linearLayoutTransactions.visibility = View.VISIBLE
        }
    }
}