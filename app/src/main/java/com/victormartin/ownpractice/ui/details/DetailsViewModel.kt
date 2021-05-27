package com.victormartin.ownpractice.ui.details

import androidx.lifecycle.asLiveData
import com.victormartin.ownpractice.data.repository.DataRepository
import com.victormartin.ownpractice.ui.base.BaseViewModel

/**
 * ViewModel for [DetailsActivity]
 */
class DetailsViewModel constructor(private val dataRepository: DataRepository) :
    BaseViewModel() {

    fun getHero(name: String) = dataRepository.getHeroByName(name).asLiveData()
}