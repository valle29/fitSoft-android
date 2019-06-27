package com.fitsoft.Activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import com.fitsoft.DataClass.BadRequest
import com.fitsoft.DataClass.LoginRequest
import com.fitsoft.DataClass.LoginResponse
import com.fitsoft.R
import com.fitsoft.Service.AsyncTaskListener
import com.fitsoft.Service.ServicioAsyncService
import com.fitsoft.Service.WebService
import com.fitsoft.Utils.AppStatus
import com.fitsoft.Utils.Funciones
import com.fitsoft.Utils.Preferencias
import com.google.gson.Gson
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.view_login.*
import java.util.HashMap

class Login : AppCompatActivity(){

    private var gson: Gson? = null
    private var loginResponse: LoginResponse? = null
    private var badRequest: BadRequest? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_login)
        this.gson = Gson()

        // Set up the view_login form.
        populateAutoComplete()
        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })

        email_sign_in_button.setOnClickListener {attemptLogin()}
        btn_registrar.setOnClickListener {
            //finish()
            //startActivity(Intent(this, SignUp::class.java))
        }
    }

    fun Activity.hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0)
    }

    private fun populateAutoComplete() {
        if (!mayRequestContacts()) {
            return
        }
    }

    private fun mayRequestContacts(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS)) {
            Snackbar.make(email, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok,
                    { requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
                        REQUEST_READ_CONTACTS
                    ) })
        } else {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_READ_CONTACTS
            )
        }
        return false
    }
    /**
     * Callback received when a permissions request has been completed.
     */
    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete()
            }
        }
    }


    @SuppressLint("ResourceAsColor")
    private fun attemptLogin() {

        // Reset errors.
        email.error = null
        password.error = null

        // Store values at the time of the view_login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password, if the user entered one.
        if(TextUtils.isEmpty(passwordStr)){
            password.error = getString(R.string.error_empty_password)
            focusView = password
            cancel = true
        }else if(!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)){
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt view_login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            //Check if internet is available
            if(!AppStatus.getInstance(this).isOnline){
                val dialog: android.app.AlertDialog? = Funciones.makeAlertPositive(this,"Error de conexión","No estás conectado a internet").create()
                dialog!!.setOnShowListener{
                    dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.colorPrimaryDark)
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.colorPrimaryDark)
                }
                dialog.show()
            }else{
                hideKeyboard()
                var oData = LoginRequest(emailStr,passwordStr)
                var servicioAsyncService = ServicioAsyncService(this, WebService().Login, gson!!.toJson(oData))

                servicioAsyncService.setOnCompleteListener(object : AsyncTaskListener {
                    override fun onTaskStart() {
                        login_progress.visibility = View.VISIBLE
                        login_form.visibility = View.GONE
                    }

                    override fun onTaskDownloadedFinished(result: HashMap<String, Any>) {
                        try {
                            //loginResponse = gson!!.fromJson<Any>(result["Resultado"].toString(), LoginResponse::class.java) as LoginResponse?
                            val statusCode = Integer.parseInt(result["StatusCode"].toString())
                            if (statusCode == 0) {
                                loginResponse = gson!!.fromJson<Any>(result["Resultado"].toString(), LoginResponse::class.java) as LoginResponse?
                            }
                            else
                                badRequest = gson!!.fromJson<Any>(result["Resultado"].toString(), BadRequest::class.java) as BadRequest?
                        } catch (error: Exception) {
                            login_progress.visibility = View.GONE
                            login_form.visibility = View.VISIBLE
                            val messageError = "Ocurrio un error inesperado"
                            val dialog: android.app.AlertDialog? = Funciones.makeAlertPositive(this@Login,"Error",messageError).create()
                            dialog!!.setOnShowListener{
                                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(R.color.colorPrimaryDark)
                                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(R.color.colorPrimaryDark)
                            }
                            dialog.show()
                        }

                    }

                    override fun onTaskUpdate(result: String?) {

                    }

                    override fun onTaskComplete(result: HashMap<String, Any>) {

                        if(Integer.parseInt(result["StatusCode"].toString()) == 0){
                            var preferencias = Preferencias(this@Login)
                            //To finish Main Activity
                            preferencias.sesion = false
                            startActivity(Intent(this@Login, MainActivity::class.java))
                            finish()
                        } else if(Integer.parseInt(result["StatusCode"].toString()) == 401){
                            if (badRequest!!.message.contains("Contraseña incorrectas")){
                                focusView = password
                                login_progress.visibility = View.GONE
                                login_form.visibility = View.VISIBLE
                                password.error = "Contraseña incorrecta"
                                cancel = true
                            }
                            else if(badRequest!!.message.contains("El correo no existe")) {
                                login_progress.visibility = View.GONE
                                login_form.visibility = View.VISIBLE
                                email.error = badRequest!!.message
                                focusView = email
                                cancel = true
                            }
                        }

                    }

                    override  fun onTaskCancelled(result: HashMap<String, Any>) {
                        toolbar.visibility = View.VISIBLE
                        login_progress.visibility = View.GONE
                        login_form.visibility = View.VISIBLE
                    }
                })
                servicioAsyncService.execute()
            }
        }
    }

    private fun isEmailValid(email: String): Boolean {
        //TODO: Replace this with your own logic
        return (email.contains("@") && email.contains("."))
    }

    private fun isPasswordValid(password: String): Boolean {
        //TODO: Replace this with your own logic
        return password.length > 5
    }

    companion object {

        /**
         * Id to identity READ_CONTACTS permission request.
         */
        private val REQUEST_READ_CONTACTS = 0

        /**
         * A dummy authentication store containing known user names and passwords.
         * TODO: remove after connecting to a real authentication system.
         */
        private val DUMMY_CREDENTIALS = arrayOf("foo@example.com:hello", "bar@example.com:world")
    }
}