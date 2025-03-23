package com.giovannigatti.hackaton


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.giovannigatti.hackaton.ui.navigation.app.MainNavigation
import com.giovannigatti.hackaton.ui.navigation.screen.Navigation
import com.giovannigatti.hackaton.ui.theme.AppTheme

@Composable
fun App() {
//    KoinApplication(
//        application = {
//            modules(appModule)
//        }
//    ) {
        AppTheme(
            seedColor = Color(0xFFb8e0d4),
        ) {
            MainNavigation()
        }
//    }
}