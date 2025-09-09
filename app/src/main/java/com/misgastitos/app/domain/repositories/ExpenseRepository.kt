package com.misgastitos.app.domain.repositories

data class Expense(
    val id: Long = 0,
    val amount: Double,
    val date: Long,
    val categoryId: Long? = null,
    val note: String? = null
)

interface ExpenseRepository {
    suspend fun list(): List<Expense>
    suspend fun add(expense: Expense): Long
}