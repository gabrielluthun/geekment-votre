package com.geekementvotre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekementvotre.ui.components.GeekButton
import com.geekementvotre.ui.theme.GeekBlack
import com.geekementvotre.ui.theme.GeekGold
import com.geekementvotre.ui.theme.GeekWhite

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    // État du scroll pour permettre de faire défiler toute la page
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GeekBlack)
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- LOGO ---
        Image(
            painter = painterResource(id = com.geekementvotre.R.drawable.logo_geekement_votre),
            contentDescription = "Logo Geekement Vôtre",
            modifier = Modifier
                .size(200.dp)
                .padding(top = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- TITRE PRINCIPAL ---
        Text(
            text = "GEEKEMENT VÔTRE",
            color = GeekWhite,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        Text(
            text = "VOTRE ANIMATEUR POP-CULTURE",
            color = GeekGold,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- DESCRIPTION ---
        Text(
            text = "Maître du jeu à Domicile, et animateur événementiel dans la région des Hauts-de-France",
            color = GeekWhite.copy(alpha = 0.8f),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- BOUTONS D'ACTION ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GeekButton(
                text = "Réserver",
                onClick = { /* Action réservation */ },
                modifier = Modifier.weight(1f)
            )
            
            // Pour le deuxième bouton, on pourrait créer une variante Outlined
            // Pour l'instant on utilise le même ou on personnalise
            GeekButton(
                text = "Me contacter",
                onClick = { /* Action contact */ },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        // --- SECTION PRESTATIONS ---
        Text(
            text = "MES PRESTATIONS",
            color = GeekWhite,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        
        Text(
            text = "Des animations sur-mesure, pour tous vos événements",
            color = GeekWhite.copy(alpha = 0.6f),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        
        // Petite ligne de séparation dorée
        Box(
            modifier = Modifier
                .width(40.dp)
                .height(2.dp)
                .background(GeekGold)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // TODO: Créer des cartes de prestations ici
        Text("Plus de contenu arrive...", color = GeekWhite.copy(alpha = 0.4f))
    }
}
