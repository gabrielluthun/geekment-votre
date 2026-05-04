package com.geekementvotre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
fun TerresAmbre(modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(GeekBlack)
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- LOGO ---
            Image(
                painter = painterResource(id = R.drawable.logo_geekement_votre),
                contentDescription = "Logo Geekement Vôtre",
                modifier = Modifier
                    .size(80.dp)
                    .padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- CARTE NOUVEAU JDR ---
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = GeekGold.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color(0xFF2D1E3D), Color(0xFF1A1A1A))
                        ),
                        shape = RoundedCornerShape(20.dp)
                    )
                    .padding(32.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "NOUVEAU JDR",
                        color = GeekGold,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Start)
                    )
                    Text(
                        text = "TERRES D'AMBRE",
                        color = GeekWhite,
                        fontSize = 24.sp,
                        fontFamily = PlayfairDisplayFontFamily,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = "(insérer description, inconnue\npour l'instant)",
                        color = GeekWhite.copy(alpha = 0.4f),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        onClick = { /* Action */ },
                        colors = ButtonDefaults.buttonColors(containerColor = GeekGold),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 12.dp),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Découvrir le projet",
                                color = GeekBlack,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = GeekBlack,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // --- SECTION POURQUOI SOUTENIR ---
            Text(
                text = "POURQUOI SOUTENIR ?",
                color = GeekWhite,
                fontSize = 28.sp,
                fontFamily = PlayfairDisplayFontFamily,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Un projet porté unanimement",
                color = GeekWhite.copy(alpha = 0.6f),
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                ReasonCard(
                    icon = Icons.AutoMirrored.Filled.MenuBook,
                    title = "UN UNIVERS ORIGINAL",
                    description = "Partez pour un monde fantasy riche, face à ses mythes et dangers"
                )
                ReasonCard(
                    icon = Icons.Default.LocationOn,
                    title = "AVENTURES MODULAIRES",
                    description = "Campagne complexe ou scénario one-shot, vous choisissez votre destin"
                )
                ReasonCard(
                    icon = Icons.Default.AutoAwesome,
                    title = "IMMERSION TOTALE",
                    description = "Livre de règles illustré, cartes, fiche de personnages... L'immersion est garantie"
                )
            }

            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Composable
private fun ReasonCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(GeekWhite.copy(alpha = 0.05f))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(GeekWhite.copy(alpha = 0.05f), RoundedCornerShape(8.dp))
                .border(1.dp, GeekGold.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = GeekGold,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = title,
                color = GeekWhite,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            )
            Text(
                text = description,
                color = GeekWhite.copy(alpha = 0.5f),
                fontSize = 13.sp,
                lineHeight = 18.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
