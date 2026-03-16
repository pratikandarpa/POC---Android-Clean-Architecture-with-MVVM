package com.app.lbgpoc.feature.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.lbgpoc.core.common.Result
import com.app.lbgpoc.feature.detail.domain.model.DetailProduct
import com.app.lbgpoc.feature.detail.domain.usecase.GetProductDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ProductDetailState(
    val isLoading: Boolean = false,
    val product: DetailProduct? = null,
    val error: String? = null
)

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailsUseCase: GetProductDetailsUseCase
) : ViewModel() {

    private val _detailState = MutableStateFlow(ProductDetailState())
    val detailState: StateFlow<ProductDetailState> = _detailState.asStateFlow()

    fun loadProductDetail(productId: Int) {
        viewModelScope.launch {
            getProductDetailsUseCase(productId).collect { result ->
                when (result) {
                    is Result.Loading -> _detailState.value =
                        _detailState.value.copy(isLoading = true, error = null)

                    is Result.Success -> _detailState.value =
                        _detailState.value.copy(isLoading = false, product = result.data)

                    is Result.Error -> _detailState.value =
                        _detailState.value.copy(isLoading = false, error = result.message)
                }
            }
        }
    }
}
