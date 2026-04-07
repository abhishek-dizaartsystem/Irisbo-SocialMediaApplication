package com.example.sociamediaapplication.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sociamediaapplication.data.repository.ReelRepository
import com.example.sociamediaapplication.model.response.Reel
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

    private val _myReels = MutableStateFlow<List<Reel>>(emptyList())
    val myReels: StateFlow<List<Reel>> = _myReels

    init {
        //loadReels()
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

    fun loadMyReels(){
        viewModelScope.launch {
            _loading.value = true
            try {
                _myReels.value = repository.getMyReels()

                Log.d("loadMyReels_DEBUG", repository.getMyReels().toString())
            }catch (e: Exception) {
                _error.value = e.message
                Log.e("ReelsViewModel", e.message.toString())
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
                    is_liked = if(it.is_liked == 1) 0 else 1,
                    likes_count =
                        if (it.is_liked == 0) it.likes_count - 1
                        else it.likes_count + 1
                )
            } else it
        }

        viewModelScope.launch {
            try {
//                val response = repository.toggleLike(reel.id)

                // 🔥 sync with backend truth
                _reels.value = _reels.value.map {
                    if (it.id == reel.id) {
                        it.copy(
//                            is_liked = response.liked,
//                            likes_count = response.likes_count
                        )
                    } else it
                }

            } catch (e: Exception) {
                // 🔥 rollback on failure
                loadReels()
            }
        }
    }

    fun toggleLikeMyReels(reel: Reel) {

        val currentList = _myReels.value

        val index = currentList.indexOfFirst { it.id == reel.id }
        if (index == -1) return

        val currentReel = currentList[index]

        val currentlyLiked = currentReel.is_liked == 1

        // ✅ Optimistic update
        val optimisticReel = currentReel.copy(
            is_liked = if (currentlyLiked) 0 else 1,
            likes_count = if (currentlyLiked)
                currentReel.likes_count - 1
            else
                currentReel.likes_count + 1
        )

        _myReels.value = currentList.toMutableList().apply {
            set(index, optimisticReel)
        }

        viewModelScope.launch {
            try {
                val response = if (currentlyLiked) {
                    repository.unlikeReel(reel.id)
                } else {
                    repository.likeReel(reel.id)
                }

                // ✅ REAL backend state
                val isLikedFromApi =
                    response.user_reaction.reaction == "like"

                val updatedReel = currentReel.copy(
                    is_liked = if (isLikedFromApi) 1 else 0,
                    likes_count = response.counts.likes
                )

                _myReels.value = _myReels.value.toMutableList().apply {
                    set(index, updatedReel)
                }

            } catch (e: Exception) {

                // ❌ rollback
                _myReels.value = _myReels.value.toMutableList().apply {
                    set(index, currentReel)
                }
            }
        }
    }

    fun toggleSave(reel: Reel) {

        val currentList = _reels.value

        val index = currentList.indexOfFirst { it.id == reel.id }
        if (index == -1) return

        val currentReel = currentList[index]

        val currentlySaved = currentReel.is_saved == 1
        val updatedReel = currentReel.copy(
            is_saved = if (currentlySaved) 0 else 1
        )

        // ✅ Optimistic UI update
        _reels.value = currentList.toMutableList().apply {
            set(index, updatedReel)
        }

        viewModelScope.launch {
            try {
                if (currentlySaved) {
                    repository.unsaveReel(reel.id)
                    Log.d("ReelsViewModel", "UNSAVE API CALLED")
                } else {
                    repository.saveReel(reel.id)
                    Log.d("ReelsViewModel", "SAVE API CALLED")
                }

            } catch (e: Exception) {

                Log.e("ReelsViewModel", "Save failed: ${e.message}")

                // ❌ Rollback ONLY this item
                _reels.value = _reels.value.toMutableList().apply {
                    set(index, currentReel)
                }
            }
        }
    }

    fun updateReel(caption: String){
        viewModelScope.launch {
            try {
                repository.updateReel(caption)
            }catch (e: Exception) {
                Log.e("ReelsViewModel", e.message.toString())
            }
        }
    }

    fun toggleSaveMyReels(reel: Reel) {

        Log.d("DEBUG_SAVE", "Profile toggleSave called for id = ${reel.id}")

        val currentList = _myReels.value

        val index = currentList.indexOfFirst { it.id == reel.id }
        if (index == -1) {
            Log.e("DEBUG_SAVE", "Reel not found in myReels")
            return
        }

        val currentReel = currentList[index]

        val currentlySaved = currentReel.is_saved == 1
        val updatedReel = currentReel.copy(
            is_saved = if (currentlySaved) 0 else 1
        )

        // ✅ Optimistic UI update (Profile list)
        _myReels.value = currentList.toMutableList().apply {
            set(index, updatedReel)
        }

        viewModelScope.launch {
            try {
                if (currentlySaved) {
                    Log.d("DEBUG_SAVE", "UNSAVE API (Profile)")
                    repository.unsaveReel(reel.id)
                } else {
                    Log.d("DEBUG_SAVE", "SAVE API (Profile)")
                    repository.saveReel(reel.id)
                }

            } catch (e: Exception) {

                Log.e("DEBUG_SAVE", "API FAILED: ${e.message}")

                // ❌ rollback ONLY this item
                _myReels.value = _myReels.value.toMutableList().apply {
                    set(index, currentReel)
                }
            }
        }
    }



}

