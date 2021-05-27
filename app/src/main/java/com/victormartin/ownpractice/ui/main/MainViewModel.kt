package com.victormartin.ownpractice.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.victormartin.ownpractice.data.repository.DataRepository
import com.victormartin.ownpractice.model.dao.MarvelHeroEntityModel
import com.victormartin.ownpractice.ui.base.BaseViewModel
import com.victormartin.ownpractice.utils.State
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel for [MainActivity]
 */

class MainViewModel constructor(private val dataRepository: DataRepository) :
    BaseViewModel() {

    private val _heroesLiveData = MutableLiveData<State<List<MarvelHeroEntityModel>>>()

    val heroesLiveData: LiveData<State<List<MarvelHeroEntityModel>>> get() = _heroesLiveData

    fun getHeroes() {
        viewModelScope.launch {
            dataRepository.getAllHeroes().collect {
                _heroesLiveData.value = it
            }
        }
    }

}
