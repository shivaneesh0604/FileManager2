package com.example.filemanager2

import androidx.lifecycle.ViewModel

class NewFIleCreationViewModel:ViewModel() {
    private lateinit var fileListener :AddFileListener

    fun addListener(fileListener: AddFileListener){
        this.fileListener = fileListener
    }

    fun getListener():AddFileListener{
        return fileListener
    }
}