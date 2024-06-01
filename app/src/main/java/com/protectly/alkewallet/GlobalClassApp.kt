package com.protectly.alkewallet

import android.app.Application
import com.protectly.alkewallet.model.UserResponse

//Clase global para guardar datos mientras la app esta abierta!
class GlobalClassApp : Application() {


    companion object{
        //vamos a crear un objeto usuario que estara global al proyecto
        var userLogged : UserResponse? = null

        var tokenAccess : String? = null
    }

    override fun onCreate() {
        super.onCreate()
        userLogged = null
        tokenAccess = null
    }

}