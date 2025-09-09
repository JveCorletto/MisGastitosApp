package com.misgastitos.app.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misgastitos.app.data.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AuthState(
    val loading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    fun isLoggedIn() = repo.isLoggedIn

    fun register(email: String, pass: String, onSuccess: () -> Unit) = viewModelScope.launch {
        if (email.isBlank() || pass.isBlank()) {
            _state.value = AuthState(error = "Email y contraseña son requeridos")
            return@launch
        }

        if (pass.length < 6) {
            _state.value = AuthState(error = "La contraseña debe tener al menos 6 caracteres")
            return@launch
        }

        runCatching {
            _state.value = AuthState(loading = true, error = null)
            repo.register(email.trim(), pass)
        }.onSuccess {
            _state.value = AuthState()
            onSuccess()
        }.onFailure {
            _state.value = AuthState(error = it.message ?: "Error desconocido")
        }
    }

    fun login(email: String, pass: String, onSuccess: () -> Unit) = viewModelScope.launch {
        if (email.isBlank() || pass.isBlank()) {
            _state.value = AuthState(error = "Email y contraseña son requeridos")
            return@launch
        }

        runCatching {
            _state.value = AuthState(loading = true, error = null)
            repo.login(email.trim(), pass)
        }.onSuccess {
            _state.value = AuthState()
            onSuccess()
        }.onFailure {
            _state.value = AuthState(error = it.message ?: "Error desconocido")
        }
    }

    fun logout() = repo.logout()

    fun clearError() {
        _state.value = AuthState(error = null)
    }
}