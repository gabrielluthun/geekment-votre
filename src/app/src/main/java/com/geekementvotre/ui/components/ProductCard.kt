package com.geekementvotre.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekementvotre.R
import com.geekementvotre.ui.theme.DMSansFontFamily
import com.geekementvotre.ui.theme.GeekBlack
import com.geekementvotre.ui.theme.GeekDarkGray
import com.geekementvotre.ui.theme.GeekGold
import com.geekementvotre.ui.theme.GeekWhite
import com.geekementvotre.ui.theme.MontserratFontFamily
import com.geekementvotre.ui.theme.PlayfairDisplayFontFamily

@Composable
fun ProductCard(
    category: String,
    name: String,
    description: String,
    price: String,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier,
    imageRes: Int = R.drawable.logo_geekement_votre // Par défaut
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(GeekWhite.copy(alpha = 0.05f)) // Fond sombre et subtil
            .padding(12.dp)
    ) {
        // --- IMAGE DU PRODUIT ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(GeekBlack.copy(alpha = 0.3f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = name,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Fit
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- CATÉGORIE ---
        Text(
            text = category.uppercase(),
            color = GeekGold,
            fontSize = 10.sp,
            fontFamily = MontserratFontFamily,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )

        // --- NOM ---
        Text(
            text = name.uppercase(),
            color = GeekWhite,
            fontSize = 18.sp,
            fontFamily = PlayfairDisplayFontFamily,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        // --- DESCRIPTION ---
        Text(
            text = description,
            color = GeekWhite.copy(alpha = 0.6f),
            fontSize = 11.sp,
            fontFamily = DMSansFontFamily,
            lineHeight = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.height(28.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // --- PRIX ET BOUTON ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = price,
                color = GeekGold,
                fontSize = 16.sp,
                fontFamily = DMSansFontFamily,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = onAddToCart,
                colors = ButtonDefaults.buttonColors(containerColor = GeekGold),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                modifier = Modifier.height(30.dp)
            ) {
                Text(
                    text = "Ajouter au panier",
                    color = GeekBlack,
                    fontSize = 10.sp,
                    fontFamily = MontserratFontFamily,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
