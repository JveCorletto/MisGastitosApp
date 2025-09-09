package com.misgastitos.app.di

import androidx.room.Room
import android.content.Context
import com.misgastitos.app.data.local.AppDb
import com.misgastitos.app.data.repo.ExpenseRepositoryImpl
import com.misgastitos.app.data.repo.CategoryRepositoryImpl
import com.misgastitos.app.domain.repositories.ExpenseRepository
import com.misgastitos.app.domain.repositories.CategoryRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.Executors

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides @Singleton
    fun provideDb(@ApplicationContext ctx: Context): AppDb =
        Room.databaseBuilder(ctx, AppDb::class.java, "misgastitos.db")
            .fallbackToDestructiveMigration()
            .setQueryExecutor(Executors.newSingleThreadExecutor()) // ← Agrega esto
            .setTransactionExecutor(Executors.newSingleThreadExecutor()) // ← Y esto
            .build()
    @Provides @Singleton
    fun provideExpenseRepo(db: AppDb): ExpenseRepository =
        ExpenseRepositoryImpl(db)

    @Provides @Singleton
    fun provideCategoryRepo(db: AppDb): CategoryRepository =
        CategoryRepositoryImpl(db)
}