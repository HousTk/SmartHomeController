package com.example.domain.domain.usecase.ids

import com.example.domain.domain.repository.IdRepository

class GetNewId(private val idRepository: IdRepository) {

    fun execute():Int{
        val idsList = idRepository.getIdsList()
        if(idsList.isNotEmpty()){
            return idsList.last() + 1
        }else{
            return 1
        }
    }

}