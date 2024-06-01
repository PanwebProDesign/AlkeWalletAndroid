package com.protectly.alkewallet.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.protectly.alkewallet.GlobalClassApp
import com.protectly.alkewallet.model.AccountRequest
import com.protectly.alkewallet.model.AccountResponse
import com.protectly.alkewallet.model.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AssignAccountViewModel(application: Application) : AndroidViewModel(application) {

    val accountCreatedLiveData = MutableLiveData<Boolean>()
    val errorMessageLiveData = MutableLiveData<String>()

    fun assignAccountToUser(userId: Int) {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val currentDate = sdf.format(Date())

        val accountRequest = AccountRequest(
            creationDate = currentDate,
            money = 150000.0,
            isBlocked = false,
            userId = userId
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val token = GlobalClassApp.tokenAccess
                if (token.isNullOrEmpty()) {
                    errorMessageLiveData.postValue("Token no encontrado")
                    return@launch
                }

                val response = ApiClient.apiService.createAccount("Bearer $token", accountRequest)
                if (response.isSuccessful && response.body() != null) {
                    accountCreatedLiveData.postValue(true)
                } else {
                    accountCreatedLiveData.postValue(false)
                }
            } catch (e: IOException) {
                Log.e("AssignAccountVM", "Error de red: ${e.message}")
                errorMessageLiveData.postValue("Error de red: ${e.message}")
            } catch (e: Exception) {
                Log.e("AssignAccountVM", "Error desconocido: ${e.message}")
                errorMessageLiveData.postValue("Error desconocido: ${e.message}")
            }
        }
    }
}
