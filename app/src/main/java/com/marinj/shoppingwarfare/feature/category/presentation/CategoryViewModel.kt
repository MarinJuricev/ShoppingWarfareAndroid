package com.marinj.shoppingwarfare.feature.category.presentation

import androidx.lifecycle.viewModelScope
import com.marinj.shoppingwarfare.core.base.BaseViewModel
import com.marinj.shoppingwarfare.feature.category.presentation.CategoryEvent.CreateCategory
import com.marinj.shoppingwarfare.feature.category.presentation.CategoryEvent.GetCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor() : BaseViewModel<CategoryEvent>() {

    private val _groceryViewState = MutableStateFlow(CategoryViewState())
    val categoryViewState: StateFlow<CategoryViewState> = _groceryViewState

    override fun onEvent(event: CategoryEvent) {
        when (event) {
            GetCategories -> handleGetGroceries()
            CreateCategory -> handleCreateCategory()
        }
    }

    private fun handleGetGroceries() = viewModelScope.launch {
        _groceryViewState.value = _groceryViewState.value.copy(isLoading = true)
    }

    private fun handleCreateCategory() = viewModelScope.launch {
        _groceryViewState.value = _groceryViewState.value.copy(
            isAddCategoryFabExpanded = !_groceryViewState.value.isAddCategoryFabExpanded
        )
    }
}
