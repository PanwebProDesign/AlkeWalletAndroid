package com.protectly.alkewallet.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.protectly.alkewallet.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    val TAG = "ProfileActivity"
    //instanciamos viewbinding
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //iniciamos viewbinding
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        binding.btnMyInfo.setOnClickListener {
//            val intent = Intent(this, MyInfoActivity::class.java)
//            startActivity(intent)
//
//        }







    }
}