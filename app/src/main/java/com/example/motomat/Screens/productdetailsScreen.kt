package com.example.motomat.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(navController: NavController, productId: String) {
    val viewModel = viewModel<ProductViewModel>()
    val products by viewModel.products

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Product Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowCircleLeft, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        val product = products.find { it.id == productId }
        if (product != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Price: ₹${String.format("%.2f", product.price)}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Product not found.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
