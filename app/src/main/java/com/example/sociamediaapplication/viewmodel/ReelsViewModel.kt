package com.example.sociamediaapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.ViewModelInitializer
import com.example.sociamediaapplication.model.Reel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReelsViewModel: ViewModel() {
    private val _reelsList = MutableStateFlow(
        listOf(
            Reel(
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                false,
                120
            ),
            Reel(
                "https://www.w3schools.com/html/mov_bbb.mp4",
                false,
                89
            ),
            Reel(
                "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
                false,
                230
            )
        )
    )
    val reelsList: StateFlow<List<Reel>> = _reelsList

    private val _isMuted = MutableStateFlow<Boolean>(false)
    val isMuted: StateFlow<Boolean> = _isMuted

    fun toggleMuteVideo(){
        _isMuted.value = !_isMuted.value
    }

    fun toggleLike(index: Int) {
        _reelsList.value = _reelsList.value.mapIndexed { i, reel ->
            if (i == index) {
                if (reel.isLiked) {
                    reel.copy(
                        isLiked = false,
                        likeCount = reel.likeCount - 1
                    )
                } else {
                    reel.copy(
                        isLiked = true,
                        likeCount = reel.likeCount + 1
                    )
                }
            } else reel
        }
    }

    fun toggleSave(index:Int){
        _reelsList.value = _reelsList.value.mapIndexed { i, reel ->
            if(i == index){
                if(reel.isSaved){
                    reel.copy(
                        isSaved = false
                    )
                }else{
                    reel.copy(
                        isSaved = true
                    )
                }
            }else reel
        }
    }
}