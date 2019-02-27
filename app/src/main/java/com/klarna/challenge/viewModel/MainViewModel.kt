package com.klarna.challenge.viewModel


import android.arch.lifecycle.*
import com.klarna.challenge.model.DataRepository
import com.klarna.challenge.model.data.Weather

class MainViewModel : ViewModel() {

    fun fetchWeatherInfo(latitude: Double, longitude: Double){
        DataRepository.getInstance().fetchWeatherInfo(latitude, longitude)
    }

    fun getWeatherInfo(): MutableLiveData<Weather> {
        return DataRepository.getInstance().getWeatherInfo()
    }


    class Factory : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel() as T
        }
    }
}