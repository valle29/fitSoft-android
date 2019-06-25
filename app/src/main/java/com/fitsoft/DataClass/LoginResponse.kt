package com.fitsoft.DataClass

data class LoginResponse(var message: String, var data: Data)

data class Data(var token: String)