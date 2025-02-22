package com.andreich.musicplayer.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andreich.domain.usecase.ClearDatabaseUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val clearDatabaseUseCase: ClearDatabaseUseCase
) : ViewModel() {

    fun clearDatabase() {
        viewModelScope.launch {
            clearDatabaseUseCase()
        }
    }
}