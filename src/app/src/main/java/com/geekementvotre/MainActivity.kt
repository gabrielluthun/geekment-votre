package com.geekementvotre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.geekementvotre.ui.components.Footer
import com.geekementvotre.ui.screens.ContactScreen
import com.geekementvotre.ui.screens.Home
import com.geekementvotre.ui.screens.ReservationScreen
import com.geekementvotre.ui.screens.ReservationSuccess
import com.geekementvotre.ui.screens.Shop
import com.geekementvotre.ui.screens.TerresAmbre
import com.geekementvotre.ui.theme.GeekementvotreTheme
import com.stripe.android.PaymentConfiguration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisation de Stripe
        PaymentConfiguration.init(
            applicationContext,
            BuildConfig.STRIPE_PUBLISHABLE_KEY
        )

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
                            "accueil" -> Home(onNavigate = { currentRoute = it })
                            "boutique" -> Shop()
                            "terres_ambre" -> TerresAmbre()
                            "reservation" -> ReservationScreen(
                                onBack = { currentRoute = "accueil" },
                                onSuccess = { currentRoute = "reservation_success" }
                            )
                            "reservation_success" -> ReservationSuccess(
                                onReturnHome = { currentRoute = "accueil" }
                            )
                            "contact" -> ContactScreen()
                            else -> Home(onNavigate = { currentRoute = it })
                        }
                    }
                }
            }
        }
    }
}
