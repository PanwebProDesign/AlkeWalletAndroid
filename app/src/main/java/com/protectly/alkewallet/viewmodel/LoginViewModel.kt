package com.protectly.alkewallet.viewmodel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.protectly.alkewallet.model.LoginRequest
import com.protectly.alkewallet.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    val loginResultLiveData = MutableLiveData<Boolean>()
    val loadingLiveData = MutableLiveData<Boolean>() // Estado de carga

    private val sharedPreferences = application.getSharedPreferences("AlkeWallet", Context.MODE_PRIVATE)

    fun login(email: String, password: String) {
        loadingLiveData.postValue(true) // Indicar inicio de carga

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = ApiClient.apiService.login(LoginRequest(email, password))
                if (response.accessToken != null) {
                    // Log para ver el accessToken
                    Log.d("Prueba", "accessToken: ${response.accessToken}")

                    // Guardar accessToken en SharedPreferences
                    val editor = sharedPreferences.edit()
                    editor.putString("accessToken", response.accessToken)
                    editor.apply()

                    loginResultLiveData.postValue(true)
                } else {
                    loginResultLiveData.postValue(false)
                }
            } catch (e: IOException) {
                // Manejar error de red
                Log.e("Prueba", "Error de red: ${e.message}")
                loginResultLiveData.postValue(false)
            } catch (e: Exception) {
                // Manejar otros errores
                Log.e("Prueba", "Error desconocido: ${e.message}")
                loginResultLiveData.postValue(false)
            } finally {
                loadingLiveData.postValue(false) // Indicar fin de carga
            }
        }
    }
}