package com.geekementvotre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekementvotre.R
import com.geekementvotre.ui.theme.GeekBlack
import com.geekementvotre.ui.theme.GeekGold
import com.geekementvotre.ui.theme.GeekWhite
import com.geekementvotre.ui.theme.PlayfairDisplayFontFamily

@Composable
fun Shop(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1C1C1E), GeekBlack)
                )
            )
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- LOGO ---
        Image(
            painter = painterResource(id = R.drawable.logo_geekement_votre),
            contentDescription = "Logo Geekement Vôtre",
            modifier = Modifier
                .size(80.dp)
                .padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- HEADER BOUTIQUE ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "BOUTIQUE",
                    color = GeekWhite,
                    fontSize = 32.sp,
                    fontFamily = PlayfairDisplayFontFamily,
                    fontWeight = FontWeight.ExtraBold
                )
                
                Text(
                    text = "Parce que les souvenirs ne sont pas que dans nos têtes...",
                    color = GeekWhite.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Icône Panier encadrée
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .border(
                        width = 1.dp,
                        color = GeekGold.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingBag,
                    contentDescription = "Panier",
                    tint = GeekWhite,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // --- ZONE DES PRODUITS (À venir à l'étape 5) ---
        Text(
            text = "Les articles arrivent bientôt...",
            color = GeekWhite.copy(alpha = 0.3f),
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 48.dp)
        )
    }
}
