package com.misgastitos.app.ui.home

import javax.inject.Inject
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import com.misgastitos.app.domain.repositories.Expense
import com.misgastitos.app.domain.usecases.GetExpenses

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getExpenses: GetExpenses
) : ViewModel() {

    private val _items = MutableStateFlow<List<Expense>>(emptyList())
    val items = _items.asStateFlow()

    init { refresh() }
    fun refresh() = viewModelScope.launch { _items.value = getExpenses() }
}