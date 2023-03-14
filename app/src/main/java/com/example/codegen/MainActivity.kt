package com.example.codegen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.function.ToDoubleBiFunction

class MainActivity : AppCompatActivity() {
//    val  a = TODO()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val txtCode:TextView = findViewById(R.id.code);

        val btnSend:TextView = findViewById(R.id.send);
        val inpCode:EditText = findViewById(R.id.input);

        btnSend.setOnClickListener(View.OnClickListener {
            val requestBody = mapOf("application_code" to inpCode.text.toString())
            consumeService(requestBody, txtCode)
        })
    }
    private fun consumeService(request: Map<String, String>, textView: TextView): MutableLiveData<CodeResponse> {
        val liveData = MutableLiveData<CodeResponse>()
        val response = ServiceBuilder.buildService(APIService::class.java)
        response.validateCode(request).enqueue(
            object : Callback<CodeResponse> {
                override fun onResponse(
                    call: Call<CodeResponse>,
                    response: Response<CodeResponse>
                ) {
                    if (response.body()?.getLogincode() != null){
                        textView.text = response.body()?.getLogincode()
                        liveData.value = response.body()
                    }else{
                        val builder = AlertDialog.Builder(this@MainActivity)
                        builder.setTitle("Codigo Invalido")
                        builder.setMessage("\nFavor de ingresar un código valido\n")
                        builder.setPositiveButton(android.R.string.yes) { dialog, which -> }
                        builder.show()
                    }
                }
                override fun onFailure(call: Call<CodeResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity,"ARREGLA TUS ERRORES",Toast.LENGTH_LONG).show()
                }

            }
        )
        return liveData
    }

}
object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://miguelacv.online/")
//        .baseUrl("http://192.168.1.10:8000")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}
