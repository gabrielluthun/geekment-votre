package com.geekementvotre.ui.screens

import android.util.Log
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.geekementvotre.BuildConfig
import com.geekementvotre.R
import java.util.Locale
import com.geekementvotre.ui.components.CartBottomSheet
import com.geekementvotre.ui.components.ProductCard
import com.geekementvotre.ui.screens.CheckoutFormScreen
import com.geekementvotre.ui.theme.GeekBlack
import com.geekementvotre.ui.theme.GeekGold
import com.geekementvotre.ui.theme.GeekWhite
import com.geekementvotre.ui.theme.PlayfairDisplayFontFamily
import com.geekementvotre.viewmodels.CartViewModel
import com.geekementvotre.viewmodels.CheckoutUiState
import com.geekementvotre.viewmodels.CheckoutViewModel
import com.geekementvotre.viewmodels.ShopUiState
import com.geekementvotre.viewmodels.ShopViewModel
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet
import kotlinx.coroutines.launch

@Composable
fun Shop(
    modifier: Modifier = Modifier,
    viewModel: ShopViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel(),
    checkoutViewModel: CheckoutViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val cartCount by cartViewModel.cartCount.collectAsState()
    val cartTotal by cartViewModel.cartTotal.collectAsState()
    val checkoutState by checkoutViewModel.uiState.collectAsState()
    var showCart by remember { mutableStateOf(false) }
    
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Pour garder trace de la commande en cours de paiement
    var currentOrderId by remember { mutableStateOf<String?>(null) }

    val paymentSheet = rememberPaymentSheet { paymentResult ->
        when (paymentResult) {
            is PaymentSheetResult.Completed -> {
                currentOrderId?.let { id ->
                    checkoutViewModel.markOrderAsPaid(id)
                }
                cartViewModel.clearCart()
                scope.launch {
                    snackbarHostState.showSnackbar("Paiement réussi ! Merci de votre confiance.")
                }
            }
            is PaymentSheetResult.Canceled -> {
                // Annulé
            }
            is PaymentSheetResult.Failed -> {
                Log.e("Shop", "Stripe Payment Failed", paymentResult.error)
                scope.launch {
                    snackbarHostState.showSnackbar("Échec du paiement : ${paymentResult.error.localizedMessage}")
                }
            }
        }
    }

    LaunchedEffect(checkoutState) {
        when (checkoutState) {
            is CheckoutUiState.Success -> {
                val successState = checkoutState as CheckoutUiState.Success
                val config = successState.config
                currentOrderId = successState.orderId
                
                // On utilise la clé du serveur seulement si elle est valide (commence par pk_)
                val stripeKey = if (config.publishableKey.startsWith("pk_")) {
                    config.publishableKey
                } else {
                    Log.w("Shop", "Clé serveur invalide (${config.publishableKey}), fallback sur BuildConfig")
                    BuildConfig.STRIPE_PUBLISHABLE_KEY
                }
                
                Log.d("Shop", "Initialisation Stripe avec la clé : ${stripeKey.take(8)}...")
                PaymentConfiguration.init(context, stripeKey)
                
                paymentSheet.presentWithPaymentIntent(
                    config.paymentIntent,
                    PaymentSheet.Configuration(
                        merchantDisplayName = "Geekement Votre",
                        customer = config.customer?.let {
                            PaymentSheet.CustomerConfiguration(
                                id = it,
                                ephemeralKeySecret = config.ephemeralKey ?: ""
                            )
                        }
                    )
                )
                checkoutViewModel.resetState()
            }
            is CheckoutUiState.Error -> {
                scope.launch {
                    snackbarHostState.showSnackbar((checkoutState as CheckoutUiState.Error).message)
                }
                checkoutViewModel.resetState()
            }
            else -> {}
        }
    }

    if (showCart) {
        CartBottomSheet(
            cartViewModel = cartViewModel,
            onCheckout = {
                checkoutViewModel.startCheckout()
                showCart = false
            },
            onDismiss = { showCart = false }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF1C1C1E), GeekBlack)
                )
            )
    ) {
        if (checkoutState is CheckoutUiState.Form) {
            val cartItems by cartViewModel.cartItems.collectAsState()
            CheckoutFormScreen(
                viewModel = checkoutViewModel,
                totalAmount = cartTotal,
                cartItems = cartItems,
                onBack = { checkoutViewModel.resetState() }
            )
        } else {
            when (val state = uiState) {
                is ShopUiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = GeekGold)
                    }
                }
                is ShopUiState.Error -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.logo_geekement_votre),
                            contentDescription = null,
                            tint = GeekGold.copy(alpha = 0.3f),
                            modifier = Modifier.size(120.dp)
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Text(
                            text = "Boutique indisponible",
                            color = GeekGold,
                            fontSize = 20.sp,
                            fontFamily = PlayfairDisplayFontFamily,
                            fontWeight = FontWeight.Bold,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = state.message.ifBlank { "Une erreur est survenue lors du chargement de la boutique." },
                            color = GeekWhite.copy(alpha = 0.7f),
                            fontSize = 14.sp,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        androidx.compose.material3.Button(
                            onClick = { viewModel.fetchProducts() },
                            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                                containerColor = GeekGold,
                                contentColor = GeekBlack
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Réessayer", fontWeight = FontWeight.Bold)
                        }
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
                            ShopHeader(cartCount = cartCount, onCartClick = { showCart = true })
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        // --- PRODUITS DYNAMIQUES ---
                        items(state.products) { product ->
                            ProductCard(
                                category = product.category ?: "Produit",
                                name = product.name ?: "Sans nom",
                                description = product.description ?: "",
                                price = String.format(Locale.FRANCE, "%.2f€", product.price ?: 0.0),
                                imageUrl = product.imageUrl,
                                onAddToCart = { cartViewModel.addToCart(product) }
                            )
                        }
                    }
                }
            }
        }

        // --- FEEDBACKS ---
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 80.dp)
        )

        if (checkoutState is CheckoutUiState.Loading) {
            Box(
                modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = GeekGold)
            }
        }
    }
}

@Composable
private fun ShopHeader(
    cartCount: Int,
    onCartClick: () -> Unit
) {
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
        androidx.compose.material3.IconButton(
            onClick = onCartClick,
            modifier = Modifier
                .padding(top = 4.dp)
                .size(52.dp)
                .border(
                    width = 1.dp,
                    color = GeekGold.copy(alpha = 0.6f),
                    shape = RoundedCornerShape(14.dp)
                )
        ) {
            Box {
                Icon(
                    imageVector = Icons.Outlined.ShoppingBag,
                    contentDescription = "Panier",
                    tint = GeekWhite,
                    modifier = Modifier.size(26.dp)
                )
                if (cartCount > 0) {
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .background(GeekGold, RoundedCornerShape(9.dp))
                            .align(Alignment.BottomEnd),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = cartCount.toString(),
                            color = GeekBlack,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
