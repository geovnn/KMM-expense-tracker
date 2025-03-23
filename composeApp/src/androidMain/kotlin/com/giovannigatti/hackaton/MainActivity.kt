package com.giovannigatti.hackaton

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.compose.KoinApplication

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current.applicationContext

            KoinApplication(
                application = {
                    androidContext(context)
                    androidLogger()
                    modules(
                        platformModule(),          // ora può usare androidContext()
                        provideDatabaseModule,
                        provideRepositoryModule,
                        appModule,
                    )
                }
            ) {
                App() // ← richiama App dal modulo common
            }
        }
    }
}