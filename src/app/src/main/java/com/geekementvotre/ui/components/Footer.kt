package com.geekementvotre.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekementvotre.ui.theme.GeekBlack
import com.geekementvotre.ui.theme.GeekFooter
import com.geekementvotre.ui.theme.GeekGold
import com.geekementvotre.ui.theme.GeekWhite
import com.geekementvotre.ui.theme.MontserratFontFamily

private data class FooterNavigationItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun Footer(
    currentRoute: String = "accueil",
    onNavigate: (String) -> Unit = {}
) {
    val items = listOf(
        FooterNavigationItem("Accueil", Icons.Default.Home, "accueil"),
        FooterNavigationItem("Boutique", Icons.Default.ShoppingBag, "boutique"),
        FooterNavigationItem("Terres d'Ambre", Icons.Default.Shield, "terres_ambre"),
        FooterNavigationItem("Réserver", Icons.Default.CalendarMonth, "reserver"),
        FooterNavigationItem("Contact", Icons.Default.Email, "contact")
    )

    Surface(
        color = GeekFooter,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding() 
    ) {
        Column {
            // Ligne de séparation très fine en haut du footer (optionnelle, selon la maquette)
            Box(modifier = Modifier.fillMaxWidth().height(0.5.dp).background(GeekWhite.copy(alpha = 0.1f)))
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp), // Hauteur plus compacte comme sur la maquette
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items.forEach { item ->
                    val selected = currentRoute == item.route
                    
                    // Animations de couleur
                    val contentColor by animateColorAsState(
                        targetValue = if (selected) GeekGold else GeekWhite.copy(alpha = 0.4f),
                        animationSpec = tween(durationMillis = 300),
                        label = "ContentColor"
                    )

                    // Animation de la barre de sélection (largeur)
                    val indicatorWidth by animateDpAsState(
                        targetValue = if (selected) 32.dp else 0.dp,
                        animationSpec = tween(durationMillis = 300),
                        label = "IndicatorWidth"
                    )

                    // Animation d'échelle pour l'icône
                    val iconScale by animateFloatAsState(
                        targetValue = if (selected) 1.15f else 1.0f,
                        animationSpec = tween(durationMillis = 300),
                        label = "IconScale"
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null, // On retire l'effet ripple gris par défaut pour plus de propreté
                                onClick = { onNavigate(item.route) }
                            ),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // La barre dorée de sélection animée
                        Box(
                            modifier = Modifier
                                .width(indicatorWidth)
                                .height(3.dp)
                                .background(GeekGold)
                        )
                        
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .scale(iconScale),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = contentColor,
                                modifier = Modifier.size(22.dp)
                            )
                            
                            Spacer(modifier = Modifier.height(2.dp))
                            
                            Text(
                                text = item.label,
                                fontSize = 9.sp,
                                fontFamily = MontserratFontFamily,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                color = contentColor
                            )
                        }
                        
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}
