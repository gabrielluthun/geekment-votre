package com.geekementvotre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
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
import com.geekementvotre.viewmodels.ReservationUiState

@Composable
fun ReservationScreen(
    onBack: () -> Unit,
    onSuccess: () -> Unit,
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

        // Section 1 : Vos Informations
        PersonalInfoSection(
            uiState = uiState,
            onNomChange = viewModel::updateNom,
            onPrenomChange = viewModel::updatePrenom,
            onEmailChange = viewModel::updateEmail,
            onDateNaissanceChange = viewModel::updateDateNaissance,
            onGenreChange = viewModel::updateGenre
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Section 2 : Détails de la session
        SessionDetailsSection(
            uiState = uiState,
            onDateChange = viewModel::updateSelectedDate,
            onNbJoueursChange = viewModel::updateNbJoueurs,
            onTimeChange = viewModel::updateSelectedTime,
            onGameChange = viewModel::updateGame
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Section 3 : Lieu et message
        LocationSection(
            uiState = uiState,
            onAdresseChange = viewModel::updateAdresseSession,
            onMessageChange = viewModel::updateMessageSpecifique
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Affichage de l'erreur si nécessaire
        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage?: "Une erreur inconnue est survenue",
                color = GeekError,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }

        // Bouton de validation
        GeekButton(
            text = if (uiState.isSubmitting) "ENVOI EN COURS..." else "Réserver une session",
            onClick = { viewModel.submitReservation() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            enabled = uiState.canSubmit && !uiState.isSubmitting
        )

        // Gestion de la navigation vers l'écran de succès
        LaunchedEffect(uiState.submissionSuccess) {
            if (uiState.submissionSuccess == true) {
                onSuccess()
                viewModel.resetSubmissionStatus()
            }
        }
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
private fun PersonalInfoSection(
    uiState: ReservationUiState,
    onNomChange: (String) -> Unit,
    onPrenomChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onDateNaissanceChange: (String) -> Unit,
    onGenreChange: (String) -> Unit
) {
    ReservationSection(
        icon = Icons.Outlined.Person,
        title = "VOS INFORMATIONS"
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            CustomTextField(
                label = "*Nom",
                value = uiState.nom,
                onValueChange = onNomChange,
                modifier = Modifier.weight(1f),
                icon = Icons.Outlined.Person
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
            label = "*E-mail",
            value = uiState.email,
            onValueChange = onEmailChange,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            icon = Icons.Outlined.Email
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CustomTextField(
                label = "Date de naissance",
                value = uiState.dateNaissance,
                onValueChange = onDateNaissanceChange,
                modifier = Modifier.weight(1f),
                placeholder = "JJ/MM/AAAA",
                icon = Icons.Outlined.CalendarMonth
            )
            Spacer(modifier = Modifier.width(16.dp))
            CustomDropdownField(
                label = "Genre",
                selectedValue = uiState.genre,
                options = listOf("Homme", "Femme", "Autre", "Non spécifié"),
                onValueChange = onGenreChange,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun SessionDetailsSection(
    uiState: ReservationUiState,
    onDateChange: (String) -> Unit,
    onNbJoueursChange: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    onGameChange: (String) -> Unit
) {
    ReservationSection(
        icon = Icons.Outlined.Casino,
        title = "DÉTAILS DE LA SESSION"
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            CustomTextField(
                label = "*Date",
                value = uiState.selectedDate,
                onValueChange = onDateChange,
                modifier = Modifier.weight(1f),
                placeholder = "  /  /  ",
                icon = Icons.Outlined.CalendarMonth
            )
            Spacer(modifier = Modifier.width(16.dp))
            CustomTextField(
                label = "*Nombre de joueurs",
                value = uiState.nbJoueurs,
                onValueChange = onNbJoueursChange,
                modifier = Modifier.weight(1f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                icon = Icons.Outlined.Group
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            label = "*Créneau horaire souhaité",
            value = uiState.selectedTime,
            onValueChange = onTimeChange,
            modifier = Modifier.fillMaxWidth(),
            icon = Icons.Outlined.Schedule
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            label = "*Type de jeu souhaité",
            value = uiState.selectedGame,
            onValueChange = onGameChange,
            modifier = Modifier.fillMaxWidth(),
            icon = Icons.Outlined.SportsEsports
        )
    }
}

@Composable
private fun LocationSection(
    uiState: ReservationUiState,
    onAdresseChange: (String) -> Unit,
    onMessageChange: (String) -> Unit
) {
    ReservationSection(
        icon = Icons.Outlined.LocationOn,
        title = "LIEU ET MESSAGE"
    ) {
        CustomTextField(
            label = "Adresse de la session (si domicile)",
            value = uiState.adresseSession,
            onValueChange = onAdresseChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = "Laissez vide si c'est chez moi",
            icon = Icons.Outlined.Home
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            label = "Un message spécifique ?",
            value = uiState.messageSpecifique,
            onValueChange = onMessageChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            minLines = 3,
            placeholder = "Indiquez ici vos préférences ou questions...",
            icon = Icons.Outlined.ChatBubbleOutline
        )
    }
}
