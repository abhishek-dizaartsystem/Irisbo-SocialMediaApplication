package com.example.sociamediaapplication.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.sociamediaapplication.model.MediaType
import com.example.sociamediaapplication.model.UploadMedia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ChatViewModel: ViewModel() {

    private val _mediaList = MutableStateFlow<List<UploadMedia>>(emptyList())
    val mediaList: StateFlow<List<UploadMedia>> = _mediaList

    fun addImage(uri: Uri){
        _mediaList.update { current->
            current + UploadMedia(uri, MediaType.IMAGE)
        }
    }

    fun addVideo(uri: Uri){
        _mediaList.update { current->
            current + UploadMedia(uri, MediaType.VIDEO)
        }
    }


}