package com.example.domain.domain.usecase.ids

import com.example.domain.domain.repository.IdRepository

class GetIdsList(private val idRepository: IdRepository) {
    fun execute():ArrayList<Int>{

        return idRepository.getIdsList()
    }
}