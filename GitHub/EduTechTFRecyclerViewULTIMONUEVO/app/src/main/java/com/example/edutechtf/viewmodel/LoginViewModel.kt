package com.example.edutechtf.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.edutechtf.exceptions.CredencialesInvalidasException
import com.example.edutechtf.repositories.UserRepository

class LoginViewModel : ViewModel() {
    var nickname by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    fun onNicknameChanged(nuevoNickname: String) {
        nickname = nuevoNickname
    }

    fun onPasswordChanged(nuevaPassword: String) {
        password = nuevaPassword
    }


    fun intentarLogin(onLoginExitoso: () -> Unit) {
        try {
            val usuario = UserRepository.login(nickname, password)
            onLoginExitoso()

        } catch (e: CredencialesInvalidasException) {
            throw e
        }
    }
}