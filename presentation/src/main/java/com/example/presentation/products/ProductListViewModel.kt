package com.example.presentation.products

import androidx.lifecycle.viewModelScope
import com.example.domain.common.Result
import com.example.domain.usecase.GetProductListUseCaseImpl
import com.example.presentation.base.BaseViewModel
import com.example.presentation.mapper.ProductsMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for the products list screen. This class is responsible for managing the view state, handling view intents,
 * and emitting side effects related to the products list screen.
 */
@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductListUseCaseImpl: GetProductListUseCaseImpl,
    private val productsMapper: ProductsMapper,
) : BaseViewModel<ProductListViewState, ProductListViewIntent, ProductListSideEffect>(
) {

    /**
     * Function responsible for fetching the product list from the data source and updating the view state accordingly.
     */
    private fun fetchProducts() {
        viewModelScope.launch {
            getProductListUseCaseImpl.invoke().collect {
                when (it) {
                    is Result.Success -> emitStateUpdate(
                        ProductListViewState.Success(
                            productsMapper.map(
                                it.data,
                            ),
                        ),
                    )

                    is Result.Error -> emitStateUpdate(ProductListViewState.Error(it.error))
                }
            }
        }
    }

    /**
     * Function responsible for navigating to the product details screen for the specified product ID.
     *
     * @param id: The ID of the product to navigate to.
     */
    private fun navigateToProductDetails(id: Int) {
        viewModelScope.launch {
            emitEffect(ProductListSideEffect.NavigateToProductDetails(id))
        }
    }

    /**
     * Override of the sendIntent method from the BaseViewModel class. This method handles incoming view intents
     * and triggers the corresponding actions.
     *
     * @param intent: The view intent to be processed.
     */
    override fun sendIntent(intent: ProductListViewIntent) {
        when (intent) {
            is ProductListViewIntent.FetchProductListList -> fetchProducts()
            is ProductListViewIntent.OnProductItemClick -> navigateToProductDetails(intent.id)
        }
    }

    override fun initialValue() = ProductListViewState.Loading
}
