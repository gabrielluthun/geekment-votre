package com.geekementvotre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekementvotre.R
import com.geekementvotre.ui.theme.*

@Composable
fun ReservationScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GeekBlack)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Étape 1.1 : Le Logo
        Image(
            painter = painterResource(id = R.drawable.logo_geekement_votre),
            contentDescription = "Logo Geekement Votre",
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Étape 1.2 : Titre Principal
        Text(
            text = "RÉSERVER UNE SESSION",
            style = MaterialTheme.typography.displaySmall.copy(
                fontFamily = PlayfairDisplayFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 2.sp
            ),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Uniquement dédié au JDR !",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = PlayfairDisplayFontFamily,
                fontStyle = FontStyle.Italic,
                color = GeekSubtitle,
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(top = 4.dp),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Divider doré
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(2.dp)
                .background(GeekGold)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Étape 2 : Vos Informations
        ReservationSection(
            icon = Icons.Outlined.Person,
            title = "VOS INFORMATIONS"
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = "*Nom",
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                CustomTextField(
                    label = "*Prénom",
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                label = "*Email",
                value = "",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = "*Date de naissance",
                    value = "",
                    onValueChange = {},
                    placeholder = "/     /     /",
                    modifier = Modifier.weight(1.2f)
                )
                Spacer(modifier = Modifier.width(16.dp))
                CustomTextField(
                    label = "Genre",
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun ReservationSection(
    icon: ImageVector,
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF141416)) // Gris très sombre pour la carte
            .padding(24.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = GeekGold,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontFamily = PlayfairDisplayFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
            )
        }

        content()

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "*Champ obligatoire",
            style = MaterialTheme.typography.labelSmall.copy(
                fontStyle = FontStyle.Italic,
                color = GeekSubtitle.copy(alpha = 0.7f)
            )
        )
    }
}

@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                color = GeekSubtitle,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(44.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFF2C2C2E)) // Couleur des champs
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (value.isEmpty() && placeholder != null) {
                Text(
                    text = placeholder,
                    color = GeekSubtitle.copy(alpha = 0.5f),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Text(
                text = value,
                color = Color.White,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
