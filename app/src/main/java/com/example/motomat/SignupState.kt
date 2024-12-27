// SignupState.kt - Add this new file
sealed class SignupState {
    object Initial : SignupState()
    object Loading : SignupState()
    object Success : SignupState()
    data class Error(val message: String) : SignupState()
}