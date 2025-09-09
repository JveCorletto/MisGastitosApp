package com.misgastitos.app.data.repo

import com.misgastitos.app.data.local.AppDb
import com.misgastitos.app.data.local.CategoryEntity
import com.misgastitos.app.domain.repositories.Category
import com.misgastitos.app.domain.repositories.CategoryRepository
import javax.inject.Inject

private fun CategoryEntity.toDomain() = Category(
    id = id,
    name = name,
    color = color,
    icon = icon
)

private fun Category.toEntity() = CategoryEntity(
    id = id,
    name = name,
    color = color,
    icon = icon
)

class CategoryRepositoryImpl @Inject constructor(
    private val db: AppDb
) : CategoryRepository {

    override suspend fun getAllCategories(): List<Category> =
        db.categoryDao().all().map { it.toDomain() }

    override suspend fun getCategoryById(id: Long): Category? =
        db.categoryDao().getById(id)?.toDomain()

    override suspend fun insertCategory(category: Category): Long {
        val result = db.categoryDao().insert(category.toEntity())
        return result
    }

    override suspend fun updateCategory(category: Category) {
        db.categoryDao().update(category.toEntity())
    }

    override suspend fun deleteCategory(id: Long) {
        db.categoryDao().delete(id)
    }
}