package com.klekner.polymerfinder.ui.viewmodel

import androidx.lifecycle.*
import com.klekner.polymerfinder.data.Polymer
import com.klekner.polymerfinder.model.PolymerRepository

class PolymerInfoViewModel(val repository: PolymerRepository): ViewModel() {

    var blends: LiveData<List<Polymer>?> = MutableLiveData()
    var polymer: LiveData<Polymer> = MutableLiveData()

    fun getPolymer(id: Long) {
        polymer = liveData {
            val data = repository.getPolymer(id)// loadUser is a suspend function.
            emitSource(data)
        }
    }

    fun getBlends(id: Long) {
        blends = liveData {
            val data = repository.getBlends(id)// loadUser is a suspend function.
            emitSource(data)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class InfoFactory(val repository: PolymerRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PolymerInfoViewModel(
            repository
        ) as T
    }

}