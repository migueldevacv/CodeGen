package com.example.codegen

import com.google.gson.annotations.SerializedName

data class CodeResponse (
    @SerializedName("login_code") var login_code: String
){
    fun getLogincode(): String {
        return login_code;
    }
}
