package com.misgastitos.app.data.repo

import com.misgastitos.app.data.local.AppDb
import com.misgastitos.app.data.local.ExpenseEntity
import com.misgastitos.app.domain.repositories.Expense
import com.misgastitos.app.domain.repositories.ExpenseRepository
import javax.inject.Inject

private fun ExpenseEntity.toDomain() =
    Expense(id = id, amount = amount, date = date, categoryId = categoryId, note = note)

private fun Expense.toEntity() =
    ExpenseEntity(id = id, amount = amount, date = date, note = note, categoryId = categoryId)

class ExpenseRepositoryImpl @Inject constructor(
    private val db: AppDb
) : ExpenseRepository {

    override suspend fun list(): List<Expense> =
        db.expenseDao().all().map { it.toDomain() }

    override suspend fun add(expense: Expense): Long =
        db.expenseDao().insert(expense.toEntity())
}