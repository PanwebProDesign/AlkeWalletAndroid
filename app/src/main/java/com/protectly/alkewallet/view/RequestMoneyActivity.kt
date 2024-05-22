package com.protectly.alkewallet.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.protectly.alkewallet.databinding.ActivityRequestMoneyBinding

class RequestMoneyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRequestMoneyBinding

    val TAG ="RequestMoneyActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackAddToHome.setOnClickListener {goToHomeOfRequest()}
        binding.addmoney222.setOnClickListener { goToHomeOfRequest() }


    }

    private fun goToHomeOfRequest() {
        val i = Intent(this, HomePageActivity::class.java)
        startActivity(i)
    }
}