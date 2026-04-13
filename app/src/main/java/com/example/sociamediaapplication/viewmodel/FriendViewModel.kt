package com.example.sociamediaapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.FriendRepository
import com.example.sociamediaapplication.model.response.FriendStatusResponse
import com.example.sociamediaapplication.model.response.MyFriendsResponse
import com.example.sociamediaapplication.model.response.ReceivedRequestResponse
import com.example.sociamediaapplication.model.response.SuggestedUsersResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FriendViewModel(
    private val repository: FriendRepository
): ViewModel() {

    private val _suggestedUsers = MutableStateFlow<SuggestedUsersResponse?>(null)
    val suggestedUsers: StateFlow<SuggestedUsersResponse?> = _suggestedUsers

    fun loadSuggestedUsers(){
        viewModelScope.launch {
            try {
                _suggestedUsers.value = repository.getSuggestedUsers()
            }catch (e: Exception){
                Log.e("FriendVM_DEBUG", e.message.toString())
            }
        }
    }
    
    fun sendFriendRequest(userId: Int){
        viewModelScope.launch { 
            try {
                repository.sendFriendRequest(userId)
                loadSuggestedUsers()
            }catch(e: Exception){
                Log.e("FriendVM_DEBUG", e.message.toString())
            }
        }
    }

    private val _receivedRequests = MutableStateFlow<ReceivedRequestResponse?>(null)
    val receivedRequests: StateFlow<ReceivedRequestResponse?> = _receivedRequests

    fun getReceivedRequests(){
        viewModelScope.launch {
            try {
                _receivedRequests.value = repository.getReceivedRequests()
            }catch(e: Exception){
                Log.e("FriendVM_DEBUG", e.message.toString())
            }
        }
    }

    private val _sentRequests = MutableStateFlow<ReceivedRequestResponse?>(null)
    val sentRequests: StateFlow<ReceivedRequestResponse?> = _receivedRequests

    fun getSentRequests(){
        viewModelScope.launch {
            try {
                _sentRequests.value = repository.getSentRequests()
            }catch(e: Exception){
                Log.e("FriendVM_DEBUG", e.message.toString())
            }
        }
    }

    private val _friendshipStatus = MutableStateFlow<FriendStatusResponse?>(null)
    val friendshipStatus: StateFlow<FriendStatusResponse?> = _friendshipStatus

    fun getFriendshipStatus(userId: Int){
        viewModelScope.launch {
            try {
                _friendshipStatus.value = repository.getFriendshipStatus(userId)
            }catch(e: Exception){
                Log.e("FriendVM_DEBUG", e.message.toString())
            }
        }
    }

    private val _myFriends = MutableStateFlow<MyFriendsResponse?>(null)
    val myFriends: StateFlow<MyFriendsResponse?> = _myFriends

    fun getMyFriends(){
        viewModelScope.launch {
            try {
                _myFriends.value = repository.getMyFriends()
            }catch(e: Exception){
                Log.e("FriendVM_DEBUG", e.message.toString())
            }
        }
    }

}