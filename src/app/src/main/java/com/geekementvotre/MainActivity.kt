package com.geekementvotre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.geekementvotre.ui.components.Footer
import com.geekementvotre.ui.screens.HomeScreen
import com.geekementvotre.ui.theme.GeekementvotreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeekementvotreTheme {
                var currentRoute by remember { mutableStateOf("accueil") }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { 
                        Footer(
                            currentRoute = currentRoute,
                            onNavigate = { currentRoute = it }
                        ) 
                    }
                ) { innerPadding ->
                    Crossfade(
                        targetState = currentRoute,
                        modifier = Modifier.padding(innerPadding),
                        label = "MainNavigation"
                    ) { route ->
                        when (route) {
                            "accueil" -> HomeScreen()
                            "boutique" -> {
                                // Placeholder pour l'instant
                                Text("Page Boutique en cours de création...")
                            }
                            else -> HomeScreen()
                        }
                    }
                }
            }
        }
    }
}
