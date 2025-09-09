package com.misgastitos.app.domain.usecases

import com.misgastitos.app.domain.repositories.Expense
import com.misgastitos.app.domain.repositories.ExpenseRepository
import javax.inject.Inject

class GetExpenses @Inject constructor(
    private val repo: ExpenseRepository
) { suspend operator fun invoke() = repo.list() }

class AddExpense @Inject constructor(
    private val repo: ExpenseRepository
) { suspend operator fun invoke(exp: Expense) = repo.add(exp) }