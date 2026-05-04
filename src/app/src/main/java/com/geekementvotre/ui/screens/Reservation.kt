package com.geekementvotre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.geekementvotre.R
import com.geekementvotre.ui.components.*
import com.geekementvotre.ui.theme.*
import com.geekementvotre.viewmodels.ReservationViewModel

@Composable
fun ReservationScreen(
    onBack: () -> Unit,
    viewModel: ReservationViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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

        // Section : Informations Personnelles
        PersonalInformationSection(
            uiState = uiState,
            onNomChange = viewModel::updateNom,
            onPrenomChange = viewModel::updatePrenom,
            onEmailChange = viewModel::updateEmail,
            onDateDeNaissanceChange = viewModel::updateDateDeNaissance,
            onGenreChange = viewModel::updateGenre
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Bouton de validation (activé seulement si les informations client sont complètes et valides)
        GeekButton(
            text = "CONTINUER",
            onClick = { /* Prochaine étape ou action */ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            enabled = uiState.canSubmit
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
            text = "VOTRE RÉSERVATION",
            style = MaterialTheme.typography.displaySmall.copy(
                fontFamily = PlayfairDisplayFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 2.sp
            ),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Pour réserver un JDR, c'est ici !",
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
    uiState: com.geekementvotre.viewmodels.ReservationUiState,
    onNomChange: (String) -> Unit,
    onPrenomChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onDateDeNaissanceChange: (String) -> Unit,
    onGenreChange: (String) -> Unit
) {
    val genres = listOf("Masculin", "Féminin", "Non-binaire", "Autre")

    ReservationSection(
        icon = Icons.Outlined.Person,
        title = "VOS INFORMATIONS"
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            CustomTextField(
                label = "*Nom",
                value = uiState.nom,
                onValueChange = onNomChange,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            CustomTextField(
                label = "*Prénom",
                value = uiState.prenom,
                onValueChange = onPrenomChange,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            label = "*Email",
            value = uiState.email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = !uiState.isEmailValid
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            DateOfBirthField(
                value = uiState.dateDeNaissance,
                onDateSelected = onDateDeNaissanceChange,
                modifier = Modifier.weight(1.2f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            CustomDropdownField(
                label = "Genre",
                selectedValue = uiState.genre,
                options = genres,
                onValueChange = onGenreChange,
                modifier = Modifier.weight(1f)
            )
        }
    }
}
