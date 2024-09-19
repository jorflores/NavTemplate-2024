package com.example.navtemplate.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.navtemplate.viewmodel.UserViewModel

@Composable
fun NavigationDrawer(navController: NavController, appViewModel: UserViewModel, onNavigate: (String) -> Unit) {
    val isUserLogged by appViewModel.isUserLogged.collectAsState(initial = false)

    //val isUserLogged = appViewModel.isUserLogged
// Obtener el estado del back stack del NavController

    val currentBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry.value?.destination?.route

    ModalDrawerSheet(modifier = Modifier) {
        if (isUserLogged) {
            NavigationDrawerItem(
                label = { Text("Ruta 1") },
                selected  = currentDestination == "Ruta 1",
                onClick = { onNavigate("route1") }
            )
            NavigationDrawerItem(
                label = { Text("Ruta 2") },
                selected = currentDestination == "Ruta 2",
                onClick = { onNavigate("route2") }
            )
        }
        NavigationDrawerItem(
            label = { Text("Ruta 3") },
            selected = currentDestination == "Ruta 3",
            onClick = { onNavigate("route3") }
        )
        NavigationDrawerItem(
            label = { Text("Biblioteca") },
            selected = currentDestination == "biblioteca",
            onClick = { onNavigate("biblioteca") }
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { appViewModel.setUserLogged(false) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Log Out")
        }
    }
}