package com.protectly.alkewallet.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.protectly.alkewallet.GlobalClassApp
import com.protectly.alkewallet.model.Transaction
import com.protectly.alkewallet.model.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    val transactionsLiveData = MutableLiveData<List<Transaction>>()
    val errorMessageLiveData = MutableLiveData<String>()

    fun fetchTransactions() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val token = GlobalClassApp.tokenAccess
                if (token.isNullOrEmpty()) {
                    errorMessageLiveData.postValue("Token no encontrado")
                    return@launch
                }

                val response = ApiClient.apiService.getTransactions("Bearer $token")
                if (response.isSuccessful) {
                    val transactionsResponse = response.body()
                    val transactions = transactionsResponse?.data
                    Log.d("TransactionViewModel", "Transacciones obtenidas: $transactions")
                    transactionsLiveData.postValue(transactions ?: emptyList())
                } else {
                    Log.e("TransactionViewModel", "Error al obtener las transacciones")
                    errorMessageLiveData.postValue("Error al obtener las transacciones")
                }
            } catch (e: IOException) {
                Log.e("TransactionViewModel", "Error de red: ${e.message}")
                errorMessageLiveData.postValue("Error de red: ${e.message}")
            } catch (e: Exception) {
                Log.e("TransactionViewModel", "Error desconocido: ${e.message}")
                errorMessageLiveData.postValue("Error desconocido: ${e.message}")
            }
        }
    }
}
