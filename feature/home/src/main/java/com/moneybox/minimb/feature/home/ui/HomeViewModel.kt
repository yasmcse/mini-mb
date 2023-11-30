package com.moneybox.minimb.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneybox.minimb.common.di.CommonModule
import com.moneybox.minimb.common.NetworkResult
import com.moneybox.minimb.common.model.products.AllProducts
import com.moneybox.minimb.common.utils.DispatcherProvider
import com.moneybox.minimb.feature.home.usecase.GetInvestorProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductsUseCase: GetInvestorProductsUseCase,
    @CommonModule.Feature private val dispatcher: DispatcherProvider
) : ViewModel() {

    private val _products: MutableStateFlow<NetworkResult<AllProducts>> = MutableStateFlow<NetworkResult<AllProducts>>(NetworkResult.Loading)
    val products: StateFlow<NetworkResult<AllProducts>> =
        _products.asStateFlow()

    fun getInvestorProducts() =
        viewModelScope.launch(dispatcher.default) {
            getProductsUseCase().collect {
                _products.value = it
            }
        }
}