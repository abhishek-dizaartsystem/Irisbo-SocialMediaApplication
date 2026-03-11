package com.example.sociamediaapplication.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.R
import com.example.sociamediaapplication.data.repository.EventRepository
import com.example.sociamediaapplication.model.response.EventDetails
import com.example.sociamediaapplication.model.response.EventDetailsResponse
import com.example.sociamediaapplication.model.response.EventsResponse
import com.example.sociamediaapplication.model.response.MyEventsResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.RequestBody.Companion.toRequestBody

class EventViewModel(
    private val repository: EventRepository
): ViewModel() {


    private val _coverPhoto = MutableStateFlow<Any>(R.drawable.coverphoto)
    val coverPhoto: StateFlow<Any> = _coverPhoto

    private val _eventProfile = MutableStateFlow<Any>(R.drawable.photoinitial)
    val eventProfile: StateFlow<Any> = _eventProfile
    private val _events = MutableStateFlow<EventsResponse?>(null)
    val events: StateFlow<EventsResponse?> = _events

    private val _myEvents = MutableStateFlow<MyEventsResponse?>(null)
    val myEvents: StateFlow<MyEventsResponse?> = _myEvents

    private val _eventDetails = MutableStateFlow<EventDetailsResponse?>(null)
    val eventDetails: StateFlow<EventDetailsResponse?> = _eventDetails

    fun loadEvents(){
        viewModelScope.launch {
            try {

                _events.value = repository.fetchEvents()
            } catch (e: Exception){
                Log.e("Event_Debug", e.toString())
            }
        }
    }

    fun loadMyEvents(){
        viewModelScope.launch {
            try {
                _myEvents.value = repository.fetchMyEvents()
            } catch (e: Exception){
                Log.e("Event_Debug", e.toString())
            }
        }
    }

    fun loadEventDetails(eventId: Int){
        viewModelScope.launch {
            try {
                _eventDetails.value = repository.fetchEventDetails(eventId)
            } catch (e: Exception){
                Log.e("Event_Debug", e.toString())
            }
        }
    }

    fun createEvent(
        context: Context,
        title: String,
        location_name: String,
        description: String,
        start_time: String,
        end_time: String,
        cover_image_uri: Uri
    ){
        viewModelScope.launch {

            try {
                repository.createEvent(
                    context,
                    title.toRequestBody(),
                    location_name.toRequestBody(),
                    description.toRequestBody(),
                    start_time.toRequestBody(),
                    end_time.toRequestBody(),
                    cover_image_uri
                )
            } catch (e: Exception){
                Log.e("Event_Debug", e.toString())
            }
        }
    }

    fun deleteEvent(eventId: Int){
        viewModelScope.launch{
            try{
                repository.deleteEvent(eventId)
            } catch (e: Exception){
                Log.e("Event_Debug", e.toString())
            }
        }
    }

    fun updateCoverImage(uri: Uri){
        _coverPhoto.value = uri
    }

    fun updateEventProfile(uri: Uri){
        _eventProfile.value = uri
    }

}