package com.example.demo.service.Proxys

import org.springframework.aot.generate.Generated
import com.example.demo.model.UsdPrice
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

@Generated
class ProxyUsdPrice {


    private val apiUrl = "https://api.estadisticasbcra.com/usd_of"
    private val token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3NjA0MTMzMDMsInR5cGUiOiJleHRlcm5hbCIsInVzZXIiOiJwaXlveG9wNDgwQHJvd3BsYW50LmNvbSJ9.8PshhrGdV4ftQ0nctBk6zTgNIRDZcNmEJuEqQqs0mtRofFNHekrOCpiUA2tstV1A2n64l1kYZfp711_bGxPVIA" // Reemplaza con tu token

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