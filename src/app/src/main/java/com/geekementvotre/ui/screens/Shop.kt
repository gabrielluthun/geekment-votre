package com.geekementvotre.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
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

private data class Product(
    val id: Int,
    val category: String,
    val name: String,
    val description: String,
    val price: String
)

@Composable
fun Shop(modifier: Modifier = Modifier) {
    val products = listOf(
        Product(1, "Accessoire", "Potion", "Potion de soin, à utiliser avec parcimonie... À ne pas mettre entre toutes les mains !", "5.99€"),
        Product(2, "Accessoire", "Potion", "Potion de soin, à utiliser avec parcimonie... À ne pas mettre entre toutes les mains !", "5.99€"),
        Product(3, "Accessoire", "Potion", "Potion de soin, à utiliser avec parcimonie... À ne pas mettre entre toutes les mains !", "5.99€"),
        Product(4, "Accessoire", "Potion", "Potion de soin, à utiliser avec parcimonie... À ne pas mettre entre toutes les mains !", "5.99€"),
        Product(5, "Accessoire", "Potion", "Potion de soin, à utiliser avec parcimonie... À ne pas mettre entre toutes les mains !", "5.99€"),
        Product(6, "Accessoire", "Potion", "Potion de soin, à utiliser avec parcimonie... À ne pas mettre entre toutes les mains !", "5.99€")
    )
 
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1C1C1E), GeekBlack)
                )
            )
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 160.dp),
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 32.dp, top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            // --- LOGO ---
            item(span = { GridItemSpan(maxLineSpan) }) {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.logo_geekement_votre),
                        contentDescription = "Logo",
                        modifier = Modifier.size(80.dp)
                    )
                }
            }

            // --- HEADER ---
            item(span = { GridItemSpan(maxLineSpan) }) {
                Spacer(modifier = Modifier.height(8.dp))
                ShopHeader()
                Spacer(modifier = Modifier.height(8.dp))
            }

            // --- PRODUITS ---
            items(products) { product ->
                ProductCard(
                    category = product.category,
                    name = product.name,
                    description = product.description,
                    price = product.price,
                    onAddToCart = { /* Prochaine étape */ }
                )
            }
        }
    }
}

@Composable
private fun ShopHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "BOUTIQUE",
                color = GeekWhite,
                fontSize = 32.sp,
                fontFamily = PlayfairDisplayFontFamily,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 1.sp
            )
            
            Text(
                text = "Parce que les souvenirs ne sont pas que dans nos têtes...",
                color = GeekWhite.copy(alpha = 0.6f),
                fontSize = 14.sp,
                fontStyle = FontStyle.Italic,
                lineHeight = 18.sp,
                modifier = Modifier.padding(top = 4.dp, end = 16.dp)
            )
        }

        // Icône Panier
        Box(
            modifier = Modifier
                .padding(top = 4.dp)
                .size(52.dp)
                .border(
                    width = 1.dp,
                    color = GeekGold.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(14.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.ShoppingBag,
                contentDescription = "Panier",
                tint = GeekWhite,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}
