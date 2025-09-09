package com.misgastitos.app.domain.repositories

data class Category(
    val id: Long = 0,
    val name: String,
    val color: String,
    val icon: String
)

interface CategoryRepository {
    suspend fun getAllCategories(): List<Category>
    suspend fun getCategoryById(id: Long): Category?
    suspend fun insertCategory(category: Category): Long
    suspend fun updateCategory(category: Category)
    suspend fun deleteCategory(id: Long)
}