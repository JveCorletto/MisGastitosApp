package com.misgastitos.app.data.local

import androidx.room.*

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name")
    suspend fun all(): List<CategoryEntity>

    @Insert
    suspend fun insert(cat: CategoryEntity): Long
}

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY date DESC")
    suspend fun all(): List<ExpenseEntity>

    @Insert
    suspend fun insert(exp: ExpenseEntity): Long
}

@Database(
    entities = [CategoryEntity::class, ExpenseEntity::class],
    version = 1
)
abstract class AppDb : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun expenseDao(): ExpenseDao
}