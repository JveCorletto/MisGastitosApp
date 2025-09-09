package com.misgastitos.app.data.local

import androidx.room.*

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name")
    suspend fun all(): List<CategoryEntity>

    @Query("SELECT * FROM categories WHERE id = :id")
    suspend fun getById(id: Long): CategoryEntity?

    @Insert
    suspend fun insert(cat: CategoryEntity): Long

    @Update
    suspend fun update(cat: CategoryEntity)

    @Query("DELETE FROM categories WHERE id = :id")
    suspend fun delete(id: Long)
}

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    suspend fun all(): List<ExpenseEntity>

    @Insert
    suspend fun insert(exp: ExpenseEntity): Long

    @Update
    suspend fun update(exp: ExpenseEntity)

    @Query("DELETE FROM expenses WHERE id = :id")
    suspend fun delete(id: Long)
}

@Database(
    entities = [CategoryEntity::class, ExpenseEntity::class],
    version = 2 // Incrementa la versión
)
abstract class AppDb : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun expenseDao(): ExpenseDao
}