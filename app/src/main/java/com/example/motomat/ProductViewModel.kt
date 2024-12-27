
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


class ProductViewModel : ViewModel() {
    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        viewModelScope.launch {
            try {
                FirebaseFirestore.getInstance()
                    .collection("products")
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) {
                            // Handle error
                            return@addSnapshotListener
                        }

                        if (snapshot != null) {
                            val productList = snapshot.documents.mapNotNull { document ->
                                try {
                                    Product(
                                        id = document.id,
                                        name = document.getString("name") ?: "",
                                        price = document.getDouble("price") ?: 0.0,
                                        description = document.getString("description") ?: ""
                                    )
                                } catch (e: Exception) {
                                    null
                                }
                            }
                            _products.value = productList
                        }
                        _isLoading.value = false
                    }
            } catch (e: Exception) {
                _isLoading.value = false
            }
        }
    }
}