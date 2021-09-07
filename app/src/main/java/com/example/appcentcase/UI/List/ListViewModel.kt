package com.example.appcentcase.UI.List

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.appcentcase.Model.weatherLocation
import com.example.appcentcase.Model.weatherLocationItem
import com.example.appcentcase.util.ServiceManager
import com.example.appcentcase.util.SingleLiveEvent
import kotlinx.coroutines.launch
import java.lang.Exception

class ListViewModel(application: Application) : AndroidViewModel(application) {
    private val showListMutableLiveData = MutableLiveData<List<weatherLocationItem>>()
    val showListLiveData: LiveData<List<weatherLocationItem>> = showListMutableLiveData

    val errorStateLiveData = SingleLiveEvent<String>()

    fun getCitiesByLocation(location:String?) {

        viewModelScope.launch {
            try {
                val result = ServiceManager.service.getCitiesByLocation(location)
                val showList = arrayListOf<weatherLocationItem>()
                for (showResult in result) {
                    Log.v("Veriler","abcd")

                    showList.add(showResult)
                }
                showListMutableLiveData.postValue(showList)
            } catch (e: Exception) {
                errorStateLiveData.postValue("Bir hata oluştu")
                Log.v("hata", "service call error", e)
            }
        }
    }

    fun getCitiesBySearch(cityName:String){
        viewModelScope.launch {
            try {
                val result = ServiceManager.service.getCitiesBySearch(cityName)
                val showList = arrayListOf<weatherLocationItem>()
                for (showResult in result) {
                    Log.v("Veriler","abcd")
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