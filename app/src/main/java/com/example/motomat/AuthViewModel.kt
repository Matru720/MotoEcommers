
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore


// AuthViewModel.kt - Add this new file
class AuthViewModel : ViewModel() {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    private val _signupState = mutableStateOf<SignupState>(SignupState.Initial)
    val signupState: State<SignupState> = _signupState

    fun signUp(email: String, password: String, name: String, onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank() || name.isBlank()) {
            _signupState.value = SignupState.Error("Please fill all fields")
            return
        }

        _signupState.value = SignupState.Loading

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Get the user ID
                    val userId = auth.currentUser?.uid
                    if (userId != null) {
                        // Create user profile in Firestore
                        val user = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "createdAt" to FieldValue.serverTimestamp()
                        )

                        firestore.collection("users")
                            .document(userId)
                            .set(user)
                            .addOnSuccessListener {
                                _signupState.value = SignupState.Success
                                onSuccess()
                            }
                            .addOnFailureListener { e ->
                                _signupState.value = SignupState.Error(e.message ?: "Failed to create profile")
                            }
                    }
                } else {
                    _signupState.value = SignupState.Error(task.exception?.message ?: "Signup failed")
                }
            }
    }
}