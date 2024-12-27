
import com.google.firebase.firestore.FirebaseFirestore

class ProductRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val productsCollection = firestore.collection("products")

    fun addSampleProducts() {
        val sampleProducts = listOf(
            mapOf(
                "name" to "Sport Bike",
                "price" to 999.99,
                "description" to "High-performance sport motorcycle"
            ),
            mapOf(
                "name" to "Cruiser",
                "price" to 899.99,
                "description" to "Comfortable cruiser motorcycle"
            ),
            mapOf(
                "name" to "Adventure Bike",
                "price" to 1299.99,
                "description" to "All-terrain adventure motorcycle"
            )
        )

        sampleProducts.forEach { product ->
            productsCollection.add(product)
        }
    }
}