package com.example.sociamediaapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.AnalyticsRepository
import com.example.sociamediaapplication.model.response.AnalyticsDashboardResponse
import com.example.sociamediaapplication.model.response.ChannelAnalyticsResponse
import com.example.sociamediaapplication.model.response.TopVideosResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnalyticsViewModel(
    private val repository: AnalyticsRepository
): ViewModel() {

    private val _channelAnalytics = MutableStateFlow<ChannelAnalyticsResponse?>(null)
    val channelAnalytics: StateFlow<ChannelAnalyticsResponse?> = _channelAnalytics

    private val _videoDashboard = MutableStateFlow<AnalyticsDashboardResponse?>(null)
    val videoDashboard: StateFlow<AnalyticsDashboardResponse?> = _videoDashboard

    private val _topVideos = MutableStateFlow<TopVideosResponse?>(null)
    val topVideos: StateFlow<TopVideosResponse?> = _topVideos

    fun getChannelAnalytics(){
        viewModelScope.launch {
            try {
                _channelAnalytics.value = repository.getChannelAnalytics()
            }catch (e: Exception){
                Log.e("Analytics_DEBUG", e.message.toString())
            }
        }
    }

    fun getAnalyticsDashboard(){
        viewModelScope.launch {
            try {
                _videoDashboard.value = repository.getVideoDashboard()
            }catch (e: Exception){
                Log.e("Analytics_DEBUG", e.message.toString())
            }
        }
    }

    fun getTopVideos(){
        viewModelScope.launch {
            try {
                _topVideos.value = repository.getTopVideos()
            }catch (e: Exception){
                Log.e("Analytics_DEBUG", e.message.toString())
            }
        }
    }
}