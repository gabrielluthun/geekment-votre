package com.geekementvotre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Topic
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
import com.geekementvotre.ui.components.CustomTextField
import com.geekementvotre.ui.components.GeekButton
import com.geekementvotre.ui.components.ReservationSection
import com.geekementvotre.ui.theme.*
import com.geekementvotre.viewmodels.ContactViewModel

@Composable
fun ContactScreen(
    viewModel: ContactViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GeekBlack)
            .verticalScroll(scrollState)
            .padding(bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactHeader()

        Spacer(modifier = Modifier.height(32.dp))

        ReservationSection(
            icon = Icons.Outlined.Email,
            title = "FORMULAIRE DE CONTACT"
        ) {
            CustomTextField(
                label = "*Votre Nom",
                value = uiState.nom,
                onValueChange = viewModel::updateNom,
                modifier = Modifier.fillMaxWidth(),
                icon = Icons.Outlined.Person
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                label = "*Votre E-mail",
                value = uiState.email,
                onValueChange = viewModel::updateEmail,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                icon = Icons.Outlined.Email,
                isError = uiState.email.isNotEmpty() && !uiState.isEmailValid
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                label = "*Sujet",
                value = uiState.sujet,
                onValueChange = viewModel::updateSujet,
                modifier = Modifier.fillMaxWidth(),
                icon = Icons.Outlined.Topic
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                label = "*Votre Message",
                value = uiState.message,
                onValueChange = viewModel::updateMessage,
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                minLines = 5,
                placeholder = "Comment puis-je vous aider ?",
                icon = Icons.Outlined.Message,
                isError = uiState.message.isNotEmpty() && !uiState.isMessageClean
            )
            if (uiState.message.isNotEmpty() && !uiState.isMessageClean) {
                Text(
                    "Le message contient des propos inappropriés.",
                    color = GeekError,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (uiState.errorMessage != null) {
            Text(
                text = uiState.errorMessage ?: "",
                color = GeekError,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }

        if (uiState.submissionSuccess == true) {
            Text(
                text = "Votre message a bien été envoyé ! Je vous répondrai dès que possible.",
                color = GeekGold,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            GeekButton(
                text = "ENVOYER UN AUTRE MESSAGE",
                onClick = { viewModel.resetSubmissionStatus() },
                modifier = Modifier.padding(horizontal = 16.dp),
                isOutlined = true
            )
        } else {
            GeekButton(
                text = if (uiState.isSubmitting) "ENVOI EN COURS..." else "ENVOYER LE MESSAGE",
                onClick = { viewModel.submitContact() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                enabled = uiState.canSubmit && !uiState.isSubmitting
            )
        }
    }
}

@Composable
private fun ContactHeader() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(20.dp))

        Image(
            painter = painterResource(id = R.drawable.logo_geekement_votre),
            contentDescription = "Logo Geekement Votre",
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "CONTACTEZ-NOUS",
            style = MaterialTheme.typography.displaySmall.copy(
                fontFamily = PlayfairDisplayFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 2.sp
            ),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Une question ? Un projet spécifique ?",
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
