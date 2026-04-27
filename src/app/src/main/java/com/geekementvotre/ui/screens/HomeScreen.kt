package com.geekementvotre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.TheaterComedy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekementvotre.ui.components.GeekButton
import com.geekementvotre.ui.components.PrestationCard
import com.geekementvotre.ui.theme.GeekBlack
import com.geekementvotre.ui.theme.GeekGold
import com.geekementvotre.ui.theme.GeekWhite
import com.geekementvotre.ui.theme.PlayfairDisplayFontFamily

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1C1C1E), GeekBlack)
                )
            )
    ) {
        val screenWidth = maxWidth
        val isSmallScreen = screenWidth < 360.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = if (isSmallScreen) 16.dp else 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- LOGO ---
            Image(
                painter = painterResource(id = com.geekementvotre.R.drawable.logo_geekement_votre),
                contentDescription = "Logo Geekement Vôtre",
                modifier = Modifier
                    .size(if (isSmallScreen) 150.dp else 200.dp)
                    .padding(top = if (isSmallScreen) 12.dp else 24.dp)
            )

            Spacer(modifier = Modifier.height(if (isSmallScreen) 16.dp else 24.dp))

            // --- TITRE PRINCIPAL ---
            Text(
                text = "GEEKEMENT VÔTRE",
                color = GeekWhite,
                fontSize = if (isSmallScreen) 24.sp else 32.sp,
                fontFamily = PlayfairDisplayFontFamily,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                lineHeight = if (isSmallScreen) 28.sp else 36.sp
            )

            Text(
                text = "VOTRE ANIMATEUR POP-CULTURE",
                color = GeekGold,
                fontSize = if (isSmallScreen) 12.sp else 14.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            // --- DESCRIPTION ---
            Text(
                text = "Maître du jeu à Domicile, et animateur événementiel dans la région des Hauts-de-France",
                color = GeekWhite.copy(alpha = 0.8f),
                fontSize = if (isSmallScreen) 14.sp else 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = if (isSmallScreen) 8.dp else 24.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- BOUTONS D'ACTION ---
            if (screenWidth < 340.dp) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GeekButton(text = "Réserver", onClick = { }, modifier = Modifier.fillMaxWidth())
                    GeekButton(text = "Me contacter", onClick = { }, modifier = Modifier.fillMaxWidth(), isOutlined = true)
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    GeekButton(text = "Réserver", onClick = { }, modifier = Modifier.weight(1f))
                    GeekButton(text = "Me contacter", onClick = { }, modifier = Modifier.weight(1f), isOutlined = true)
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // --- SECTION PRESTATIONS ---
            Text(
                text = "MES PRESTATIONS",
                style = MaterialTheme.typography.displayLarge,
                color = GeekWhite,
                fontSize = 24.sp,
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
            
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(2.dp)
                    .background(GeekGold)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- LISTE DES PRESTATIONS ---
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                PrestationCard(
                    title = "ANIMATION ÉVÉNEMENTIEL",
                    description = "Quizz, animation cosplay, et ambiance mémorable pour vos conventions.",
                    icon = Icons.Default.TheaterComedy
                )

                PrestationCard(
                    title = "MJ À DOMICILE",
                    description = "Déplacement chez vous pour des sesssions de JDR sur-mesure, du simple one-shot au longues compagnes...",
                    icon = Icons.Default.Casino
                )

                PrestationCard(
                    title = "POUR QUI ?",
                    description = "• Débutants : pour une découverte\n• Familles : pour une soirée originale\n• Confirmés : pour de la complexité",
                    icon = Icons.Default.Groups
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(2.dp)
                    .background(GeekGold)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- BANNIÈRE NOUVEAU JDR ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF302341), Color(0xFF1A1A1A))
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(if (isSmallScreen) 20.dp else 32.dp)
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "NOUVEAU JDR",
                        style = MaterialTheme.typography.displayLarge,
                        color = GeekGold,
                        fontSize = 20.sp
                    )
                    
                    Spacer(modifier = Modifier.height(if (isSmallScreen) 16.dp else 32.dp))
                    
                    Text(
                        text = "(Insérer description, inconnue pour l'instant)",
                        color = GeekWhite.copy(alpha = 0.4f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Button(
                        onClick = { /* Action */ },
                        colors = ButtonDefaults.buttonColors(containerColor = GeekGold),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        modifier = if (isSmallScreen) Modifier.fillMaxWidth() else Modifier
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            Text("Découvrir le projet", color = GeekBlack, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = GeekBlack, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(2.dp)
                    .background(GeekGold)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- SECTION JEUX ---
            Text(
                text = "MES JEUX",
                style = MaterialTheme.typography.displayLarge,
                color = GeekWhite,
                fontSize = 24.sp,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "Des univers variés, pour tous les goûts",
                color = GeekWhite.copy(alpha = 0.6f),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- GRILLE DE JEUX ---
            val games = listOf(
                "Fantasy" to Icons.Default.Casino,
                "Horreur" to Icons.Default.AutoAwesome,
                "Enquête" to Icons.Default.Casino,
                "Sci-Fi" to Icons.Default.Casino,
                "Aventure" to Icons.Default.Casino,
                "Terres d'Ambre" to Icons.Default.Casino
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                for (i in games.indices step 2) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        GameSmallCard(games[i].first, games[i].second, Modifier.weight(1f))
                        if (i + 1 < games.size) {
                            GameSmallCard(games[i+1].first, games[i+1].second, Modifier.weight(1f))
                        } else {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}

@Composable
fun GameSmallCard(name: String, icon: ImageVector, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(GeekWhite.copy(alpha = 0.05f), RoundedCornerShape(12.dp))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(icon, contentDescription = null, tint = GeekGold, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text(name, color = GeekWhite, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
    }
}
