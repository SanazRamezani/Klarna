package com.klarna.challenge.model

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.klarna.challenge.model.data.Weather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataRepository {

    private val HTTPS_API_GITHUB_URL = "https://api.darksky.net/forecast/2bb07c3bece89caf533ac9a5d23d8417/"
    private var webService: WebService? = null
    val data = MutableLiveData<Weather>()

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(HTTPS_API_GITHUB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        webService = retrofit.create(WebService::class.java)
    }

    companion object {
        private var dataRepository: DataRepository? = null
        @Synchronized
        @JvmStatic
        fun getInstance(): DataRepository {
            if (dataRepository == null) {
                dataRepository = DataRepository()
            }
            return dataRepository!!
        }
    }

    fun getWeatherInfo(): MutableLiveData<Weather> {
        return data
    }


    fun fetchWeatherInfo(latitude: Double, longitude: Double) {
        webService?.getWeatherInfo(latitude, longitude)?.enqueue(object: Callback<Weather> {
            override fun onResponse(call: Call<Weather>, response: Response<Weather>) {
                data.value=response.body()
            }

            override fun onFailure(call: Call<Weather>, t: Throwable) {
                data.value=null
            }

        })

    }

}