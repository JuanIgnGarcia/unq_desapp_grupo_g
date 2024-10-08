package com.example.demo.service.Proxys

import com.example.demo.model.UsdPrice
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


class ProxyUsdPrice {


    private val apiUrl = "https://api.estadisticasbcra.com/usd_of"
    private val token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NTg1ODQ4OTEsInR5cGUiOiJleHRlcm5hbCIsInVzZXIiOiJiZXJuYWxqdWxpYW5lQGdtYWlsLmNvbSJ9.KuIW-kN3GKsWDbytyjf6VyJ55eJJ4X3V_D-RiNhKIdtkZOwgAE3-n0TpUyRsm42e9p2jsX6JOVREXjRUGpQrBw" // Reemplaza con tu token

    fun lastUsdPrice(): UsdPrice {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(apiUrl)
            .addHeader("Authorization", "Bearer $token")
            .build()
        val response: Response = client.newCall(request).execute()
        val responseData = response.body?.string()
        val gson = Gson()
        val listType = object : TypeToken<List<UsdPrice>>() {}.type
        val listaUsdPrices: List<UsdPrice> = gson.fromJson(responseData, listType)
        return (listaUsdPrices.last())
    }

    fun convertUSDPrice(cryptoPrice: Double) : Double{
        return this.lastUsdPrice().v * cryptoPrice
    }
}