package com.example.sociamediaapplication.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.PostRepository
import com.example.sociamediaapplication.data.repository.ReelRepository
import com.example.sociamediaapplication.model.MediaType
import com.example.sociamediaapplication.model.UploadMedia
import com.example.sociamediaapplication.model.UploadType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UploadViewModel(
    private val repository: PostRepository,
    private val reelRepository: ReelRepository
): ViewModel() {
    private val _caption = MutableStateFlow("")
    val caption: StateFlow<String> = _caption

    private val _mediaList = MutableStateFlow<List<UploadMedia>>(emptyList())
    val mediaList: StateFlow<List<UploadMedia>> = _mediaList

    private val _uploadType = MutableStateFlow(UploadType.POST)
    val uploadType: StateFlow<UploadType> = _uploadType

    private val _isUploading = MutableStateFlow(false)
    val isUploading: StateFlow<Boolean> = _isUploading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun updateCaption(newCaption:String){
        _caption.value = newCaption
    }

    fun uploadPost(context: Context) {
        viewModelScope.launch {
            try {

                val uris = mediaList.value.map { it.uri }

                repository.createPost(
                    caption.value,
                    uris,
                    context
                )

                // 🔥 Clear UI after upload
                _caption.value = ""
                _mediaList.value = emptyList()
                

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun uploadReel(
        caption: String?,
        videoUri: Uri?,
        context: Context
    ) {
        viewModelScope.launch {
            _isUploading.value = true
            try {

                reelRepository.uploadReel(
                    captionText = caption,
                    uri = videoUri,
                    context = context
                )

            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isUploading.value = false
            }
        }
    }


    fun setUploadType(type: UploadType) {
        _uploadType.value = type

        // 🔥 If switching to reel → keep only one media
        if (type == UploadType.REEL) {
            _mediaList.value = _mediaList.value.take(1)
        }
    }

    fun addImage(uri: Uri) {
        val type = _uploadType.value

        _mediaList.update { list ->
            if (type == UploadType.REEL) {
                listOf(UploadMedia(uri, MediaType.IMAGE))
            } else {
                list + UploadMedia(uri, MediaType.IMAGE)
            }
        }
    }

    fun addVideo(uri: Uri) {
        val type = _uploadType.value

        _mediaList.update { list ->
            if (type == UploadType.REEL) {
                listOf(UploadMedia(uri, MediaType.VIDEO))
            } else {
                list + UploadMedia(uri, MediaType.VIDEO)
            }
        }
    }


    fun removeMedia(item: UploadMedia){
        _mediaList.update{current->
            current- item
        }
    }

}