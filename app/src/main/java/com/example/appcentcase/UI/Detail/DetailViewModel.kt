package com.example.appcentcase.UI.Detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appcentcase.Model.ConsolidatedWeather
import com.example.appcentcase.util.ServiceManager
import com.example.appcentcase.util.SingleLiveEvent
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailViewModel(application: Application) : AndroidViewModel(application) {
    private val showListMutableLiveData = MutableLiveData<List<ConsolidatedWeather>>()
    val showListLiveData: LiveData<List<ConsolidatedWeather>> = showListMutableLiveData

    val errorStateLiveData = SingleLiveEvent<String>()


    fun getCityWeather(woeid:Int) {

        viewModelScope.launch {
            try {
                val result = ServiceManager.service.getWeatherCity(woeid)
                val showList = arrayListOf<ConsolidatedWeather>()
                for(showResult in result.consolidatedWeather){
                    showList.add(showResult)
                }
                showListMutableLiveData.postValue(showList)
            } catch (e: Exception) {
                errorStateLiveData.postValue("Bir hata oluştu")
                Log.v("hata", "service call error", e)
            }
        }
    }
}