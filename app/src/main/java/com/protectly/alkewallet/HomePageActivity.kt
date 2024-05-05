package com.protectly.alkewallet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomePageActivity : AppCompatActivity() {

    val TAG = "HomePageActivity"

    lateinit var linearLayoutEmpty : LinearLayout
    lateinit var linearLayoutTransactions : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //id para linear layout
        linearLayoutEmpty = findViewById(R.id.emptylayout_hp)
        linearLayoutTransactions = findViewById(R.id.transactionlayout_hp)

        //creamos la variable y recivimos el dato de pantalla anterior
        val receivedRegister : String = intent.extras?.getString("NEW_USER").orEmpty()
        //validamos de que pagina viene
        validatePageRegister(receivedRegister)

        val btnSendMoney = findViewById<Button>(R.id.send1)
        val btnAddMoney = findViewById<Button>(R.id.add1)
        val btnGoToProfile = findViewById<ImageView>(R.id.goto_profile)

        btnSendMoney.setOnClickListener { goToSendMoney() }
        btnAddMoney.setOnClickListener { goToAddMoney() }
        btnGoToProfile.setOnClickListener { goToProfile()}
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