package com.protectly.alkewallet.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.protectly.alkewallet.databinding.ActivitySendMoneyBinding

class SendMoneyActivity : AppCompatActivity() {

    val TAG = "SendMoneyActivity"
    private lateinit var binding: ActivitySendMoneyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendMoneyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackAddToHomex.setOnClickListener {goToHomeOfRequest()}
        binding.addmoney222x.setOnClickListener { goToHomeOfRequest() }
    }

    private fun goToHomeOfRequest() {
        val i = Intent(this, HomePageActivity::class.java)
        startActivity(i)
    }
}