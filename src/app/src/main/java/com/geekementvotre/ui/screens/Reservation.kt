package com.geekementvotre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
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
            onTelephoneChange = viewModel::updateTelephone,
            onNumeroRueChange = viewModel::updateNumeroRue,
            onNomRueChange = viewModel::updateNomRue,
            onCodePostalChange = viewModel::updateCodePostal,
            onNomVilleChange = viewModel::updateNomVille,
            onDateNaissanceChange = viewModel::updateDateNaissance,
            onGenreChange = viewModel::updateGenre
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Section 2 : Détails de la session
        SessionDetailsSection(
            uiState = uiState,
            onDateChange = viewModel::updateDateSession,
            onNbJoueursChange = viewModel::updateNbJoueurs,
            onTimeChange = viewModel::updateCreneauHoraire,
            onGameChange = viewModel::updateTypeJeu
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Section 3 : Lieu et message
        LocationSection(
            uiState = uiState,
            onADomicileChange = viewModel::updateADomicileClient,
            onNumeroRueChange = viewModel::updateNumeroRueSession,
            onNomRueChange = viewModel::updateNomRueSession,
            onCodePostalChange = viewModel::updateCodePostalSession,
            onNomVilleChange = viewModel::updateNomVilleSession,
            onMessageChange = viewModel::updateMessageDemande
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Affichage de l'erreur si nécessaire
        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage ?: "Une erreur inconnue est survenue",
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
            text = "Uniquement dédié au JDR à domicile !",
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
    onTelephoneChange: (String) -> Unit,
    onNumeroRueChange: (String) -> Unit,
    onNomRueChange: (String) -> Unit,
    onCodePostalChange: (String) -> Unit,
    onNomVilleChange: (String) -> Unit,
    onDateNaissanceChange: (LocalDate?) -> Unit,
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
            icon = Icons.Outlined.Email,
            isError = uiState.email.isNotEmpty() && !uiState.isEmailValid
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            label = "Téléphone",
            value = uiState.telephone,
            onValueChange = onTelephoneChange,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            icon = Icons.Outlined.Phone
        )
        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider(
            color = GeekWhite.copy(alpha = 0.1f),
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Adresse de facturation",
            style = MaterialTheme.typography.labelSmall,
            color = GeekGold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            CustomTextField(
                label = "N°",
                value = uiState.numeroRue,
                onValueChange = onNumeroRueChange,
                modifier = Modifier.weight(0.3f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomTextField(
                label = "Rue",
                value = uiState.nomRue,
                onValueChange = onNomRueChange,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CustomTextField(
                label = "Code Postal",
                value = uiState.codePostal,
                onValueChange = onCodePostalChange,
                modifier = Modifier.weight(0.5f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.width(16.dp))
            CustomTextField(
                label = "Ville",
                value = uiState.nomVille,
                onValueChange = onNomVilleChange,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            DatePickerField(
                label = "Date de naissance",
                value = uiState.dateNaissance,
                onValueChange = onDateNaissanceChange,
                modifier = Modifier.weight(1f),
                yearRange = 1920..LocalDate.now().year,
                selectableDates = object : SelectableDates {
                    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                        return utcTimeMillis <= System.currentTimeMillis()
                    }
                }
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
    onDateChange: (LocalDate?) -> Unit,
    onNbJoueursChange: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    onGameChange: (String) -> Unit
) {
    ReservationSection(
        icon = Icons.Outlined.Casino,
        title = "DÉTAILS DE LA SESSION"
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DatePickerField(
                label = "*Date",
                value = uiState.dateSession,
                onValueChange = onDateChange,
                modifier = Modifier.weight(1f),
                yearRange = LocalDate.now().year..LocalDate.now().year + 3,
                selectableDates = object : SelectableDates {
                    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                        val todayUtc = LocalDate.now()
                            .atStartOfDay(java.time.ZoneOffset.UTC)
                            .toInstant()
                            .toEpochMilli()
                        return utcTimeMillis >= todayUtc
                    }
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            CustomTextField(
                label = "*Joueurs",
                value = uiState.nbJoueurs,
                onValueChange = onNbJoueursChange,
                modifier = Modifier.weight(0.6f),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                icon = Icons.Outlined.Group,
                placeholder = "1-6"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomDropdownField(
            label = "*Créneau horaire souhaité",
            selectedValue = uiState.creneauHoraire,
            options = listOf(
                "Matinée (2h)",
                "Matinée (3h)",
                "Après-midi (3h)",
                "Après-midi (4h)",
                "Soirée (3h)",
                "Soirée (4h)",
                "Campagne (Journée entière)"
            ),
            onValueChange = onTimeChange,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        CustomDropdownField(
            label = "*Type de jeu souhaité",
            selectedValue = uiState.typeJeu,
            options = listOf("Fantasy", "Horreur", "Enquête", "Sci-Fi", "Aventure", "Terres d'Ambre"),
            onValueChange = onGameChange,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun LocationSection(
    uiState: ReservationUiState,
    onADomicileChange: (Boolean) -> Unit,
    onNumeroRueChange: (String) -> Unit,
    onNomRueChange: (String) -> Unit,
    onCodePostalChange: (String) -> Unit,
    onNomVilleChange: (String) -> Unit,
    onMessageChange: (String) -> Unit
) {
    ReservationSection(
        icon = Icons.Outlined.LocationOn,
        title = "LIEU ET MESSAGE"
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = uiState.aDomicileClient,
                onCheckedChange = onADomicileChange,
                colors = CheckboxDefaults.colors(
                    checkedColor = GeekGold,
                    uncheckedColor = GeekSubtitle,
                    checkmarkColor = GeekBlack
                )
            )
            Text(
                text = "Utiliser l'adresse de facturation comme adresse de jeu",
                color = GeekWhite,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 13.sp
            )
        }

        if (!uiState.aDomicileClient) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = "N°",
                    value = uiState.numeroRueSession,
                    onValueChange = onNumeroRueChange,
                    modifier = Modifier.weight(0.3f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.width(8.dp))
                CustomTextField(
                    label = "*Rue de la session",
                    value = uiState.nomRueSession,
                    onValueChange = onNomRueChange,
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Home
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = "*Code Postal",
                    value = uiState.codePostalSession,
                    onValueChange = onCodePostalChange,
                    modifier = Modifier.weight(0.5f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.width(16.dp))
                CustomTextField(
                    label = "*Ville",
                    value = uiState.nomVilleSession,
                    onValueChange = onNomVilleChange,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        CustomTextField(
            label = "Un message spécifique ? Des précisions ?",
            value = uiState.messageDemande,
            onValueChange = onMessageChange,
            modifier = Modifier.fillMaxWidth(),
            singleLine = false,
            minLines = 3,
            placeholder = "Indiquez ici des détails, questions...",
            icon = Icons.Outlined.ChatBubbleOutline
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(
    label: String,
    value: LocalDate?,
    onValueChange: (LocalDate?) -> Unit,
    modifier: Modifier = Modifier,
    selectableDates: SelectableDates = DatePickerDefaults.AllDates,
    yearRange: IntRange = DatePickerDefaults.YearRange
) {
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = value?.atStartOfDay(java.time.ZoneOffset.UTC)?.toInstant()?.toEpochMilli(),
            selectableDates = selectableDates,
            yearRange = yearRange
        )

        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        onValueChange(java.time.Instant.ofEpochMilli(millis).atZone(java.time.ZoneOffset.UTC).toLocalDate())
                    }
                    showDatePicker = false
                }) {
                    Text("OK", color = GeekGold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Annuler", color = GeekSubtitle)
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = GeekCardBackground
            )
        ) {
            DatePicker(
                state = datePickerState,
                colors = DatePickerDefaults.colors(
                    titleContentColor = Color.White,
                    headlineContentColor = Color.White,
                    weekdayContentColor = GeekSubtitle,
                    subheadContentColor = GeekSubtitle,
                    yearContentColor = Color.White,
                    selectedYearContentColor = GeekBlack,
                    selectedYearContainerColor = GeekGold,
                    dayContentColor = Color.White,
                    selectedDayContentColor = GeekBlack,
                    selectedDayContainerColor = GeekGold,
                    todayContentColor = GeekGold,
                    todayDateBorderColor = GeekGold
                )
            )
        }
    }

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
                .background(GeekInputBackground)
                .clickable { showDatePicker = true }
                .padding(horizontal = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Outlined.CalendarMonth,
                    contentDescription = null,
                    tint = GeekGold.copy(alpha = 0.6f),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = value?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: "JJ/MM/AAAA",
                    color = if (value == null) GeekSubtitle.copy(alpha = 0.5f) else Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

