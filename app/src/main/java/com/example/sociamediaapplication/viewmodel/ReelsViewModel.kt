package com.example.sociamediaapplication.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.ViewModelInitializer
import com.example.sociamediaapplication.data.repository.ReelRepository
import com.example.sociamediaapplication.model.Reel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReelsViewModel(
    private val repository: ReelRepository
): ViewModel() {
//    private val _reelsList = MutableStateFlow(
//        listOf(
//            Reel(
//                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
//                false,
//                120
//            ),
//            Reel(
//                "https://www.w3schools.com/html/mov_bbb.mp4",
//                false,
//                89
//            ),
//            Reel(
//                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
//                false,
//                230
//            )
//        )
//    )
//    val reelsList: StateFlow<List<Reel>> = _reelsList
//
//    private val _isMuted = MutableStateFlow<Boolean>(false)
//    val isMuted: StateFlow<Boolean> = _isMuted
//
//    fun toggleMuteVideo(){
//        _isMuted.value = !_isMuted.value
//    }
//
//    fun toggleLike(index: Int) {
//        _reelsList.value = _reelsList.value.mapIndexed { i, reel ->
//            if (i == index) {
//                if (reel.isLiked) {
//                    reel.copy(
//                        isLiked = false,
//                        likeCount = reel.likeCount - 1
//                    )
//                } else {
//                    reel.copy(
//                        isLiked = true,
//                        likeCount = reel.likeCount + 1
//                    )
//                }
//            } else reel
//        }
//    }
//
//    fun toggleSave(index:Int){
//        _reelsList.value = _reelsList.value.mapIndexed { i, reel ->
//            if(i == index){
//                if(reel.isSaved){
//                    reel.copy(
//                        isSaved = false
//                    )
//                }else{
//                    reel.copy(
//                        isSaved = true
//                    )
//                }
//            }else reel
//        }
//    }

    private val _reels = MutableStateFlow<List<Reel>>(emptyList())
    val reels: StateFlow<List<Reel>> = _reels

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadReels()
    }

    fun loadReels() {
        viewModelScope.launch {
            _loading.value = true
            try {
                _reels.value = repository.getReels()
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun toggleLike(reel: Reel) {

        // 🔥 optimistic update
        _reels.value = _reels.value.map {
            if (it.id == reel.id) {
                it.copy(
                    is_liked = !it.is_liked,
                    likes_count =
                        if (it.is_liked) it.likes_count - 1
                        else it.likes_count + 1
                )
            } else it
        }

        viewModelScope.launch {
            try {
                val response = repository.toggleLike(reel.id)

                // 🔥 sync with backend truth
                _reels.value = _reels.value.map {
                    if (it.id == reel.id) {
                        it.copy(
                            is_liked = response.liked,
                            likes_count = response.likes_count
                        )
                    } else it
                }

            } catch (e: Exception) {
                // 🔥 rollback on failure
                loadReels()
            }
        }
    }

}

