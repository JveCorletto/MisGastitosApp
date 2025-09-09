package com.misgastitos.app.ui.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misgastitos.app.domain.repositories.Category
import com.misgastitos.app.domain.usecases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class CategoryState(
    val categories: List<Category> = emptyList(),
    val loading: Boolean = false,
    val error: String? = null,
    val selectedCategory: Category? = null
)

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val getCategories: GetCategories,
    private val addCategory: AddCategory,
    private val updateCategory: UpdateCategory,
    private val deleteCategory: DeleteCategory
) : ViewModel() {

    private val _state = MutableStateFlow(CategoryState())
    val state = _state.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                val categories = getCategories()
                _state.value = _state.value.copy(
                    categories = categories,
                    loading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Error al cargar categorías: ${e.message}",
                    loading = false
                )
            }
        }
    }

    fun saveCategory(category: Category, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)
            try {
                if (category.id == 0L) {
                    val newId = addCategory(category)
                } else {
                    updateCategory(category)
                }
                loadCategories()
                onSuccess()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Error al guardar categoría: ${e.message}",
                    loading = false
                )
            }
        }
    }

    fun deleteCategory(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _state.value = _state.value.copy(loading = true, error = null)
                deleteCategory.invoke(id)

                val categories = getCategories()
                _state.value = _state.value.copy(
                    categories = categories,
                    loading = false
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    error = "Error al eliminar categoría: ${e.message}",
                    loading = false
                )
            }
        }
    }
}