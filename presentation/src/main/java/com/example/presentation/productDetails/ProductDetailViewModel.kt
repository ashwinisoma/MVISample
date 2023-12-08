package com.example.presentation.productDetails

import androidx.lifecycle.viewModelScope
import com.example.domain.common.Result
import com.example.domain.usecase.GetProductItemUseCase
import com.example.presentation.base.BaseViewModel
import com.example.presentation.mapper.ProductItemMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/***
 * Represents ViewModel for Product detail screen
 */
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductItemUseCase: GetProductItemUseCase,
    private val productItemMapper: ProductItemMapper,
) : BaseViewModel<ProductDetailViewState, ProductDetailViewIntent, ProductDetailSideEffect>(
    ProductDetailViewState.Loading,
) {

    private fun fetchProductDetails(productId: Int) {
        viewModelScope.launch {
            getProductItemUseCase.invoke(productId).collect {
                when (it) {
                    is Result.Success -> emitStateUpdate(
                        ProductDetailViewState.Success(
                            productItemMapper.map(it.data),
                        ),
                    )

                    is Result.Error -> emitStateUpdate(ProductDetailViewState.Error(it.error))
                }
            }
        }
    }

    override fun sendIntent(intent: ProductDetailViewIntent) {
        when (intent) {
            is ProductDetailViewIntent.FetchProductDetail -> {
                val productId = intent.id
                fetchProductDetails(productId)
            }
        }
    }
}
