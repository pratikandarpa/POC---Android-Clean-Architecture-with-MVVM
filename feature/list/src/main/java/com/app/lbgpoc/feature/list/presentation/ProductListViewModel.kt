package com.app.lbgpoc.feature.list.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lbgpoc.core.common.Result
import com.app.lbgpoc.feature.list.domain.model.ListProduct
import com.app.lbgpoc.feature.list.domain.usecase.GetProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductListState(
    val isLoading: Boolean = false,
    val products: List<ListProduct> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val _listState = MutableStateFlow(ProductListState())
    val listState: StateFlow<ProductListState> = _listState.asStateFlow()

    fun loadProducts() {
        viewModelScope.launch {
            getProductsUseCase().collect { result ->
                when (result) {
                    is Result.Loading -> _listState.value =
                        _listState.value.copy(isLoading = true, error = null)

                    is Result.Success -> _listState.value =
                        _listState.value.copy(isLoading = false, products = result.data)

                    is Result.Error -> _listState.value =
                        _listState.value.copy(isLoading = false, error = result.message)
                }
            }
        }
    }
}
