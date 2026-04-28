package com.geekementvotre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.geekementvotre.R
import com.geekementvotre.ui.components.ProductCard
import com.geekementvotre.ui.theme.GeekBlack
import com.geekementvotre.ui.theme.GeekGold
import com.geekementvotre.ui.theme.GeekWhite
import com.geekementvotre.ui.theme.PlayfairDisplayFontFamily

// Modèle de données temporaire pour l'UI
private data class Product(
    val id: Int,
    val category: String,
    val name: String,
    val description: String,
    val price: String
)

@Composable
fun Shop(modifier: Modifier = Modifier) {
    // Liste de produits d'exemple basée sur la maquette
    val products = listOf(
        Product(1, "Accessoire", "Potion", "Potion de soin, à utiliser avec parcimonie... À ne pas mettre entre toutes les mains !", "5.99€"),
        Product(2, "Accessoire", "Potion", "Potion de soin, à utiliser avec parcimonie... À ne pas mettre entre toutes les mains !", "5.99€"),
        Product(3, "Accessoire", "Potion", "Potion de soin, à utiliser avec parcimonie... À ne pas mettre entre toutes les mains !", "5.99€"),
        Product(4, "Accessoire", "Potion", "Potion de soin, à utiliser avec parcimonie... À ne pas mettre entre toutes les mains !", "5.99€"),
        Product(5, "Accessoire", "Potion", "Potion de soin, à utiliser avec parcimonie... À ne pas mettre entre toutes les mains !", "5.99€"),
        Product(6, "Accessoire", "Potion", "Potion de soin, à utiliser avec parcimonie... À ne pas mettre entre toutes les mains !", "5.99€"),
        Product(7, "Accessoire", "Potion", "Potion de soin, à utiliser avec parcimonie... À ne pas mettre entre toutes les mains !", "5.99€"),
        Product(8, "Accessoire", "Potion", "Potion de soin, à utiliser avec parcimonie... À ne pas mettre entre toutes les mains !", "5.99€")
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1C1C1E), GeekBlack)
                )
            )
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // --- LOGO (Fixe en haut) ---
        Image(
            painter = painterResource(id = R.drawable.logo_geekement_votre),
            contentDescription = "Logo Geekement Vôtre",
            modifier = Modifier
                .size(80.dp)
                .padding(top = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- LA GRILLE DE PRODUITS (Scrollable) ---
        // On utilise LazyVerticalGrid pour la performance et le côté responsive
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 150.dp), // 2 colonnes sur mobile standard
            contentPadding = PaddingValues(bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // Header de la boutique à l'intérieur de la grille pour qu'il scrolle avec
            item(span = { androidx.compose.foundation.lazy.grid.GridItemSpan(maxLineSpan) }) {
                ShopHeader()
            }

            // Affichage des produits
            items(products) { product ->
                ProductCard(
                    category = product.category,
                    name = product.name,
                    description = product.description,
                    price = product.price,
                    onAddToCart = { /* Prochaine étape : gestion du panier */ }
                )
            }
        }
    }
}

@Composable
private fun ShopHeader() {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "BOUTIQUE",
                    color = GeekWhite,
                    fontSize = 32.sp,
                    fontFamily = PlayfairDisplayFontFamily,
                    fontWeight = FontWeight.ExtraBold
                )
                
                Text(
                    text = "Parce que les souvenirs ne sont pas que dans nos têtes...",
                    color = GeekWhite.copy(alpha = 0.7f),
                    fontSize = 14.sp,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // Icône Panier encadrée
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .border(
                        width = 1.dp,
                        color = GeekGold.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingBag,
                    contentDescription = "Panier",
                    tint = GeekWhite,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
