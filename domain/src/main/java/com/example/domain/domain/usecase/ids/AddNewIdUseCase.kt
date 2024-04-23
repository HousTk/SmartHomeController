package com.example.domain.domain.usecase.ids

import com.example.domain.domain.repository.IdRepository

class AddNewIdUseCase(private val idRepository: IdRepository) {

    fun execute(id:Int):Boolean{
        return idRepository.addToList(id)
    }

}