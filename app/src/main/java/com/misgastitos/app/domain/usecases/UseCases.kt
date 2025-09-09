package com.misgastitos.app.domain.usecases

import com.misgastitos.app.domain.repositories.Expense
import com.misgastitos.app.domain.repositories.ExpenseRepository
import com.misgastitos.app.domain.repositories.Category
import com.misgastitos.app.domain.repositories.CategoryRepository
import javax.inject.Inject

// Casos de uso para gastos
class GetExpenses @Inject constructor(
    private val repo: ExpenseRepository
) { suspend operator fun invoke() = repo.list() }

class AddExpense @Inject constructor(
    private val repo: ExpenseRepository
) { suspend operator fun invoke(exp: Expense) = repo.add(exp) }

// Casos de uso para categorías
class GetCategories @Inject constructor(
    private val repo: CategoryRepository
) { suspend operator fun invoke() = repo.getAllCategories() }

class GetCategoryById @Inject constructor(
    private val repo: CategoryRepository
) { suspend operator fun invoke(id: Long) = repo.getCategoryById(id) }

class AddCategory @Inject constructor(
    private val repo: CategoryRepository
) { suspend operator fun invoke(category: Category) = repo.insertCategory(category) }

class UpdateCategory @Inject constructor(
    private val repo: CategoryRepository
) { suspend operator fun invoke(category: Category) = repo.updateCategory(category) }

class DeleteCategory @Inject constructor(
    private val repo: CategoryRepository
) { suspend operator fun invoke(id: Long) = repo.deleteCategory(id) }