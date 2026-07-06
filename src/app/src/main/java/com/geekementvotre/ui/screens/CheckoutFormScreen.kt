package com.geekementvotre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekementvotre.R
import com.geekementvotre.ui.components.CustomTextField
import com.geekementvotre.ui.components.GeekButton
import com.geekementvotre.ui.components.ReservationSection
import com.geekementvotre.ui.theme.*
import com.geekementvotre.viewmodels.CheckoutViewModel
import java.util.Locale

@Composable
fun CheckoutFormScreen(
    viewModel: CheckoutViewModel,
    totalAmount: Double,
    onBack: () -> Unit
) {
    val formState by viewModel.formState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(GeekBlack)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- LOGO ---
        Spacer(modifier = Modifier.height(20.dp))
        Image(
            painter = painterResource(id = R.drawable.logo_geekement_votre),
            contentDescription = "Logo Geekement Votre",
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- TITRE ---
        Text(
            text = "COORDONNÉES",
            style = MaterialTheme.typography.displaySmall.copy(
                fontFamily = PlayfairDisplayFontFamily,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                letterSpacing = 2.sp,
                fontSize = 22.sp
            ),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Dernière étape avant l'expédition !",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = PlayfairDisplayFontFamily,
                color = GeekSubtitle,
                fontStyle = FontStyle.Italic
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .width(80.dp)
                .height(2.dp)
                .background(GeekGold)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Section : Vos Informations
        ReservationSection(
            icon = Icons.Outlined.Person,
            title = "VOS INFORMATIONS"
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = "*Nom",
                    value = formState.nom,
                    onValueChange = viewModel::updateNom,
                    modifier = Modifier.weight(1f),
                    icon = Icons.Outlined.Person,
                    isError = formState.nom.isNotEmpty() && !formState.isNomValid,
                    placeholder = "Ex: Dupont"
                )
                Spacer(modifier = Modifier.width(16.dp))
                CustomTextField(
                    label = "*Prénom",
                    value = formState.prenom,
                    onValueChange = viewModel::updatePrenom,
                    modifier = Modifier.weight(1f),
                    isError = formState.prenom.isNotEmpty() && !formState.isPrenomValid,
                    placeholder = "Ex: Jean"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                label = "*E-mail",
                value = formState.email,
                onValueChange = viewModel::updateEmail,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                icon = Icons.Outlined.Email,
                isError = formState.email.isNotEmpty() && !formState.isEmailValid
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                label = "*Téléphone",
                value = formState.telephone,
                onValueChange = viewModel::updateTelephone,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                icon = Icons.Outlined.Phone,
                isError = formState.telephone.isNotEmpty() && !formState.isTelephoneValid,
                placeholder = "0612345678"
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section : Adresse
        ReservationSection(
            icon = Icons.Outlined.Receipt,
            title = "ADRESSE DE FACTURATION"
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = "N°",
                    value = formState.numeroRue,
                    onValueChange = viewModel::updateNumeroRue,
                    modifier = Modifier.weight(0.3f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Spacer(modifier = Modifier.width(8.dp))
                CustomTextField(
                    label = "*Rue",
                    value = formState.nomRue,
                    onValueChange = viewModel::updateNomRue,
                    modifier = Modifier.weight(1f),
                    isError = formState.nomRue.isNotEmpty() && !formState.isNomRueValid,
                    placeholder = "Rue, Avenue..."
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    label = "*Code Postal",
                    value = formState.codePostal,
                    onValueChange = viewModel::updateCodePostal,
                    modifier = Modifier.weight(0.5f),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = formState.codePostal.isNotEmpty() && !formState.isCodePostalValid
                )
                Spacer(modifier = Modifier.width(16.dp))
                CustomTextField(
                    label = "*Ville",
                    value = formState.nomVille,
                    onValueChange = viewModel::updateNomVille,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Résumé du total
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "Total à régler :",
                color = GeekWhite,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                String.format(Locale.FRANCE, "%.2f €", totalAmount),
                color = GeekGold,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        GeekButton(
            text = "Procéder au paiement",
            onClick = { viewModel.prepareCheckout((totalAmount * 100).toLong()) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            enabled = formState.canSubmit
        )

        TextButton(
            onClick = onBack,
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text("Retour au panier", color = GeekSubtitle)
        }
    }
}
