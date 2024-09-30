package com.example.navtemplate.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.navtemplate.data.LoginUserRequest
import com.example.navtemplate.data.LoginUserResponse
import com.example.navtemplate.data.RegisterUserRequest
import com.example.navtemplate.data.RegisterUserResponse
import com.example.navtemplate.dataStore.PreferenceKeys.LOGGED_ID
import com.example.navtemplate.dataStore.dataStore
import com.example.navtemplate.dataStore.setLoggedIn
import com.example.navtemplate.service.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.lang.Exception

class UserViewModel(private val userService: UserService, application: Application) : AndroidViewModel(application) {

    var email by mutableStateOf("jorge.flores@tec.mx")
    var password by mutableStateOf("1234")

    //var isUserLogged by mutableStateOf(false)

    val isUserLogged: Flow<Boolean> = getApplication<Application>().dataStore.data.map {
        preferences ->
        preferences[LOGGED_ID] == true
    }

    fun setUserLogged(value: Boolean){
        viewModelScope.launch {
            getApplication<Application>().setLoggedIn(value)

        }
    }

    private val _login = MutableStateFlow<LoginUserState>(LoginUserState.Initial)
    val login: StateFlow<LoginUserState> = _login

    private val _register = MutableStateFlow<RegisterUserState>(RegisterUserState.Initial)
    val register: StateFlow<RegisterUserState> = _register


    fun registerUser(user: RegisterUserRequest) {

        viewModelScope.launch {

            try {
                _register.value = RegisterUserState.Loading
                val response = userService.addUser(user)
                _register.value = RegisterUserState.Success(response)
            } catch (e: Exception) {
                _register.value = RegisterUserState.Error(e.message.toString())
            }
        }
    }


    fun loginUser(user: LoginUserRequest) {

        _login.value = LoginUserState.Initial
        viewModelScope.launch {

            try {
                _login.value = LoginUserState.Loading
                val response = userService.loginUser(user)
                //isUserLogged = true
                _login.value = LoginUserState.Success(response)
            } catch (e: Exception) {
                _login.value = LoginUserState.Error("Error en el login: " + e.message.toString())
            }
        }
    }





}


sealed class LoginUserState {
    object Initial : LoginUserState()
    object Loading : LoginUserState()
    data class Success(val loginResponse: LoginUserResponse) : LoginUserState()
    data class Error(val errorMessage: String) : LoginUserState()
}

sealed class RegisterUserState {
    object Initial : RegisterUserState()
    object Loading : RegisterUserState()
    data class Success(val registerResponse: RegisterUserResponse) : RegisterUserState()
    data class Error(val errorMessage: String) : RegisterUserState()
}