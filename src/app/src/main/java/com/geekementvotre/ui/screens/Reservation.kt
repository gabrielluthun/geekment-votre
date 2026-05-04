package com.geekementvotre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekementvotre.R
import com.geekementvotre.ui.components.DateOfBirthField
import com.geekementvotre.ui.theme.*

@Composable
fun ReservationScreen(onBack: () -> Unit) {
    // États - Vos Informations
    var nom by remember { mutableStateOf("") }
    var prenom by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var dateDeNaissance by remember { mutableStateOf("") }
    var genre by remember { mutableStateOf("") }

    // États - Détails de la Session (Préparation pour la suite)
    var dateSession by remember { mutableStateOf("") }
    var nombreJoueurs by remember { mutableStateOf("") }
    var creneauHoraire by remember { mutableStateOf("") }
    var typeJeu by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GeekBlack)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ReservationHeader()

        Spacer(modifier = Modifier.height(32.dp))

        PersonalInformationSection(
            nom = nom,
            onNomChange = { nom = it },
            prenom = prenom,
            onPrenomChange = { prenom = it },
            email = email,
            onEmailChange = { email = it },
            dateDeNaissance = dateDeNaissance,
            onDateDeNaissanceChange = { dateDeNaissance = it },
            genre = genre,
            onGenreChange = { genre = it }
        )
    }
}

@Composable
private fun ReservationHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_geekement_votre),
            contentDescription = "Logo Geekement Votre",
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

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

        Box(
            modifier = Modifier
                .width(80.dp)
                .height(2.dp)
                .background(GeekGold)
        )
    }
}

@Composable
private fun PersonalInformationSection(
    nom: String,
    onNomChange: (String) -> Unit,
    prenom: String,
    onPrenomChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    dateDeNaissance: String,
    onDateDeNaissanceChange: (String) -> Unit,
    genre: String,
    onGenreChange: (String) -> Unit
) {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$".toRegex()
    val isEmailError by remember(email) {
        derivedStateOf {
            email.isNotEmpty() && !email.matches(emailRegex)
        }
    }

    ReservationSection(
        icon = Icons.Outlined.Person,
        title = "VOS INFORMATIONS"
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            CustomTextField(
                label = "*Nom",
                value = nom,
                onValueChange = onNomChange,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            CustomTextField(
                label = "*Prénom",
                value = prenom,
                onValueChange = onPrenomChange,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            label = "*Email",
            value = email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = isEmailError
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            DateOfBirthField(
                value = dateDeNaissance,
                onDateSelected = onDateDeNaissanceChange,
                modifier = Modifier.weight(1.2f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            CustomTextField(
                label = "Genre",
                value = genre,
                onValueChange = onGenreChange,
                modifier = Modifier.weight(1f)
            )
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
    placeholder: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    isError: Boolean = false
) {
    Column(modifier = modifier) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium.copy(
                color = if (isError) Color(0xFFE57373) else GeekSubtitle,
                fontWeight = FontWeight.Medium
            ),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = Color.White),
            cursorBrush = SolidColor(GeekGold),
            keyboardOptions = keyboardOptions,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color(0xFF2C2C2E)) // Couleur des champs
                        .then(
                            if (isError) Modifier.border(1.dp, Color(0xFFE57373), RoundedCornerShape(8.dp))
                            else Modifier
                        )
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
                    innerTextField()
                }
            }
        )
    }
}
