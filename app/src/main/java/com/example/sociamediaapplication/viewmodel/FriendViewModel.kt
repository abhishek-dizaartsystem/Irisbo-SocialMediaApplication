package com.example.sociamediaapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.FriendRepository
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
}