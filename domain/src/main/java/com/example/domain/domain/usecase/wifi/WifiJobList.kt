package com.example.domain.domain.usecase.wifi

import kotlinx.coroutines.Job

class WifiJobList() {

    private var jobList = ArrayList<Job>()

    fun addToList(job:Job){
        jobList.add(job)
    }

    fun clear(){

        if(jobList.isNotEmpty()){
            jobList.forEach{
                it.cancel()
            }
            jobList.clear()
        }

    }

}