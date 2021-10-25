package com.marinj.shoppingwarfare.feature.category.di

import com.marinj.shoppingwarfare.feature.category.data.repository.CategoryRepositoryImpl
import com.marinj.shoppingwarfare.feature.category.domain.repository.CategoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@InstallIn(ViewModelComponent::class)
@Module
abstract class CategoryModule {

    @Binds
    @ViewModelScoped
    abstract fun provideCategoryRepository(
        repository: CategoryRepositoryImpl
    ): CategoryRepository
}
