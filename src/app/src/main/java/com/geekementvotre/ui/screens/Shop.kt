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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.geekementvotre.R
import com.geekementvotre.ui.components.ProductCard
import com.geekementvotre.ui.theme.GeekBlack
import com.geekementvotre.ui.theme.GeekGold
import com.geekementvotre.ui.theme.GeekWhite
import com.geekementvotre.ui.theme.PlayfairDisplayFontFamily
import com.geekementvotre.viewmodels.ShopUiState
import com.geekementvotre.viewmodels.ShopViewModel

@Composable
fun Shop(
    modifier: Modifier = Modifier,
    viewModel: ShopViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1C1C1E), GeekBlack)
                )
            )
    ) {
        when (val state = uiState) {
            is ShopUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = GeekGold)
                }
            }
            is ShopUiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Erreur : ${state.message}",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            is ShopUiState.Success -> {
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

                    // --- PRODUITS DYNAMIQUES ---
                    items(state.products) { product ->
                        ProductCard(
                            category = product.category,
                            name = product.name,
                            description = product.description,
                            price = "${product.price}€",
                            onAddToCart = { /* À implémenter : Panier */ }
                        )
                    }
                }
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
