package com.project.middleman.core.common.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.project.middleman.core.source.data.model.Challenge

class SharedViewModel : ViewModel(){
    var challenge by mutableStateOf<Challenge?>(null)

}