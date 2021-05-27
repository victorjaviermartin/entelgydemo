package com.victormartin.ownpractice.di

import com.victormartin.ownpractice.ui.details.DetailsViewModel
import com.victormartin.ownpractice.ui.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
}
