package com.geekementvotre.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable { onNavigate(item.route) },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // La barre dorée de sélection tout en haut
                        Box(
                            modifier = Modifier
                                .width(32.dp) // Barre plus courte, centrée
                                .height(3.dp)
                                .background(if (selected) GeekGold else Color.Transparent)
                        )
                        
                        Column(
                            modifier = Modifier.weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = if (selected) GeekGold else GeekWhite.copy(alpha = 0.5f),
                                modifier = Modifier.size(22.dp)
                            )
                            
                            Spacer(modifier = Modifier.height(2.dp))
                            
                            Text(
                                text = item.label,
                                fontSize = 9.sp,
                                fontFamily = MontserratFontFamily,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                color = if (selected) GeekGold else GeekWhite.copy(alpha = 0.5f)
                            )
                        }
                        
                        // Petit spacer en bas pour l'équilibre
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}
