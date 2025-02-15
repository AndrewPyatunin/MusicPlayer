package com.andreich.ui

import androidx.fragment.app.Fragment

sealed interface BaseUiNews {

    object Initial : BaseUiNews

    class NavigateTo(val fragment: Fragment) : BaseUiNews

    class ShowToast(val message: String) : BaseUiNews
}