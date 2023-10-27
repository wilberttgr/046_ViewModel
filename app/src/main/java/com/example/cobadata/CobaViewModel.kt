package com.example.cobadata

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cobadata.Data.DataForm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class CobaViewModel: ViewModel(){
    var namaUSR : String by mutableStateOf("")
        private set
    var noTlp : String by mutableStateOf("")
        private set
    var emailUSR : String by mutableStateOf("")
        private set
    var jenisKl : String by mutableStateOf("")
        private set
    var alamat : String by mutableStateOf("")
        private set
    private val _uiState = MutableStateFlow(DataForm())
    val uiState :  StateFlow<DataForm> = _uiState.asStateFlow()

    fun BacaData(nm:String,tlp:String,eml:String, jk:String, alt:String){
        namaUSR=nm;
        noTlp=tlp;
        emailUSR=eml;
        jenisKl=jk;
        alamat=alt;
    }

    fun setJenisK(pilihJK:String){
        _uiState.update { currentState -> currentState.copy(sex = pilihJK) }
    }
}
