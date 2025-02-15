package com.andreich.musicplayer.ui.mapper

interface ModelToUiMapper<I, O> {

    fun map(from: I): O
}