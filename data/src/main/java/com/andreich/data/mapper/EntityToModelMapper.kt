package com.andreich.data.mapper

interface EntityToModelMapper<I, O> {

    fun map(fromEntity: I): O
}