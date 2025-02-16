package com.andreich.data.mapper

interface DtoMapper<I, O> {

    fun map(fromDto: I): O
}