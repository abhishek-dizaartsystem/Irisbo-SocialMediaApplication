package com.example.sociamediaapplication.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.example.sociamediaapplication.model.MediaType
import com.example.sociamediaapplication.model.UploadMedia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UploadViewModel: ViewModel() {
    private val _caption = MutableStateFlow("")
    val caption: StateFlow<String> = _caption

    private val _mediaList = MutableStateFlow<List<UploadMedia>>(emptyList())
    val mediaList: StateFlow<List<UploadMedia>> = _mediaList

    fun updateCaption(newCaption:String){
        _caption.value = newCaption
    }

    fun addImage(uri:Uri){
        _mediaList.update {current->
            current+ UploadMedia(uri, MediaType.IMAGE)
        }
    }

    fun addVideo(uri: Uri){
        _mediaList.update{current->
            current+ UploadMedia(uri, MediaType.VIDEO)
        }
    }

    fun removeMedia(item: UploadMedia){
        _mediaList.update{current->
            current- item
        }
    }

}