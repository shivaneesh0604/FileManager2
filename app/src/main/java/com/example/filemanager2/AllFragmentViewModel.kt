package com.example.filemanager2

import android.util.Log
import androidx.lifecycle.ViewModel

class AllFragmentViewModel:ViewModel() {
    internal var file: File? = null

    init {
        Log.i("orientation","called allfragViewModel "+file)
    }
}