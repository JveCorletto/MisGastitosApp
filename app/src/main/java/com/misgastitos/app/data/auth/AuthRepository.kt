package com.misgastitos.app.data.auth

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.tasks.await
import com.google.firebase.auth.FirebaseAuth

@Singleton
class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth
) {
    val isLoggedIn: Boolean get() = auth.currentUser != null

    suspend fun register(email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass).await()
    }

    suspend fun login(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass).await()
    }

    fun logout() { auth.signOut() }
}