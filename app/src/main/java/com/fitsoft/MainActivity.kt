package com.fitsoft

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import com.fitsoft.DataClass.LoginRequest
import com.fitsoft.DataClass.LoginResponse
import com.fitsoft.Service.AsyncTaskListener
import com.fitsoft.Service.ServicioAsyncService
import com.fitsoft.Service.WebService
import com.google.gson.Gson
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        boton.setOnClickListener {
            var correo = "client@gmail.com"
            var pass = "daniel"

        }
    }
}
