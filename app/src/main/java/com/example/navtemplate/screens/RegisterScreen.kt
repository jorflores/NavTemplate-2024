package com.example.navtemplate.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.navtemplate.data.LoginUserRequest
import com.example.navtemplate.data.RegisterUserRequest
import com.example.navtemplate.viewmodel.LoginUserState
import com.example.navtemplate.viewmodel.RegisterUserState
import com.example.navtemplate.viewmodel.UserViewModel

@Composable
fun RegisterScreen(navController: NavController, userViewModel: UserViewModel) {


    val snackbarHostState = remember { SnackbarHostState() }
    val registerState by userViewModel.register.collectAsState()



    LaunchedEffect(registerState) {
        when (val register = registerState) {

            is RegisterUserState.Loading -> {

            }

            is RegisterUserState.Success -> {
                // Show the snackbar
                snackbarHostState.showSnackbar("Registro exitoso")
                //navController.navigate("home")
                //userViewModel.isUserLogged = true
                userViewModel.setUserLogged(true)
            }

            is RegisterUserState.Error -> {
                snackbarHostState.showSnackbar(register.errorMessage)
            }

            else -> {}
        }
    }


    Scaffold(

        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(16.dp)
            )
        }

    ) {paddingvalues ->

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp).padding(paddingvalues),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = userViewModel.email,
                onValueChange = { userViewModel.email = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = userViewModel.password,
                onValueChange = { userViewModel.password = it },
                label = { Text("Contraseña") },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {

                    userViewModel.registerUser(
                        RegisterUserRequest(
                            userViewModel.email,
                            userViewModel.password
                        )
                    )



                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar")
            }

            Register(registerState)
        }
    }

}

@Composable
private fun Register(registerState: RegisterUserState) {
    when (registerState) {

        is RegisterUserState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        else -> {
        }
    }
}