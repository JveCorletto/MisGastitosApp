package com.misgastitos.app.di

import androidx.room.Room
import android.content.Context
import com.misgastitos.app.data.local.AppDb
import com.misgastitos.app.data.repo.ExpenseRepositoryImpl
import com.misgastitos.app.domain.repositories.ExpenseRepository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides @Singleton
    fun provideDb(@ApplicationContext ctx: Context): AppDb =
        Room.databaseBuilder(ctx, AppDb::class.java, "misgastitos.db").build()

    @Provides @Singleton
    fun provideExpenseRepo(db: AppDb): ExpenseRepository =
        ExpenseRepositoryImpl(db)
}