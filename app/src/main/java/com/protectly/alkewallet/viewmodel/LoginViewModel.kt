package com.protectly.alkewallet.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * View model encargado de la validacion del login
 */
class LoginViewModel : ViewModel() {

    //variable que almacena el resultado del login
    val loginResultLiveData = MutableLiveData<Boolean>()

    /**
     * funcion que implementa una corrutina para
     */
    fun login(email: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                if (email == "admin" && password == "admin") {
                    loginResultLiveData.postValue(true)
                } else {
                    loginResultLiveData.postValue(false)
                }

            } catch (e: Exception) {
                //codigo de erorr
                loginResultLiveData.postValue(false)

            }

        }
    }


}