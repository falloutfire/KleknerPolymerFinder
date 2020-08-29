package com.klekner.polymerfinder.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.klekner.polymerfinder.model.PolymerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.plus

class ListPolymerViewModel(private val repository: PolymerRepository) : ViewModel() {
    val keeps = repository.polymers

    private val scope = viewModelScope + Dispatchers.IO

    fun addPolymer() {
        //repository.addPolymer(scope)
    }
}

@Suppress("UNCHECKED_CAST")
class Factory(val repository: PolymerRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ListPolymerViewModel(
            repository
        ) as T
    }

}