import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.motomat.Screens.BikeSlideshow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    // ViewModel to fetch and observe products
    val viewModel = viewModel<ProductViewModel>()
    val products by viewModel.products
    val isLoading by viewModel.isLoading

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MotoMat") },
                actions = {
                    IconButton(onClick = { navController.navigate("profile") }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Slideshow at the top
                BikeSlideshow()

                Spacer(modifier = Modifier.height(16.dp))

                when {
                    isLoading -> {
                        // Loading indicator
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    products.isEmpty() -> {
                        // No products available message with retry button
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "No products available",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.fetchProducts() }) {
                                Text("Retry")
                            }
                        }
                    }

                    else -> {
                        // List of products
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(products) { product ->
                                ProductCard(
                                    product = product,
                                    onClick = { navController.navigate("productDetail/${product.id}") }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
