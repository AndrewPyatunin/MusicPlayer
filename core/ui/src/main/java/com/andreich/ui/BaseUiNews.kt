package com.andreich.ui

sealed interface BaseUiNews {

    object Initial : BaseUiNews

    class ShowToast(val message: String) : BaseUiNews
}