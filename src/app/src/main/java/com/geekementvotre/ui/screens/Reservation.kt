package com.geekementvotre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekementvotre.R
import com.geekementvotre.ui.theme.GeekBlack
import com.geekementvotre.ui.theme.GeekGold
import com.geekementvotre.ui.theme.GeekWhite
import com.geekementvotre.ui.theme.PlayfairDisplayFontFamily

@Composable
fun Reservation(
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var name by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GeekBlack)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- HEADER AVEC BOUTON RETOUR ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Retour",
                        tint = GeekGold
                    )
                }
            }

            // --- LOGO ---
            Image(
                painter = painterResource(id = R.drawable.logo_geekement_votre),
                contentDescription = "Logo Geekement Vôtre",
                modifier = Modifier
                    .size(100.dp)
                    .padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- TITRE ---
            Text(
                text = "RÉSERVATION",
                color = GeekWhite,
                fontSize = 28.sp,
                fontFamily = PlayfairDisplayFontFamily,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Text(
                text = "VOTRE SESSION SUR-MESURE",
                color = GeekGold,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Petit trait doré décoratif
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(2.dp)
                    .background(GeekGold)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- SECTION 1 : VOS INFORMATIONS ---
            Text(
                text = "VOS INFORMATIONS",
                color = GeekGold,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nom complet", color = GeekWhite.copy(alpha = 0.6f)) },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Icon(Icons.Default.Person, contentDescription = null, tint = GeekGold)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = GeekWhite,
                    unfocusedTextColor = GeekWhite,
                    focusedBorderColor = GeekGold,
                    unfocusedBorderColor = GeekWhite.copy(alpha = 0.2f),
                    cursorColor = GeekGold
                ),
                singleLine = true
            )
        }
    }
}
