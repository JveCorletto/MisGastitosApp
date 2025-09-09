package com.misgastitos.app.ui.add

import javax.inject.Inject
import kotlinx.coroutines.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.misgastitos.app.domain.usecases.AddExpense
import com.misgastitos.app.domain.repositories.Expense

@HiltViewModel
class AddExpenseViewModel @Inject constructor(
    private val addExpense: AddExpense
) : ViewModel() {

    var amountText: String = "0.00"
    var note: String? = null

    fun save(onDone: () -> Unit) = viewModelScope.launch {
        val amount = amountText.toDoubleOrNull() ?: 0.0
        addExpense(Expense(amount = amount, date = System.currentTimeMillis(), note = note))
        onDone()
    }
}