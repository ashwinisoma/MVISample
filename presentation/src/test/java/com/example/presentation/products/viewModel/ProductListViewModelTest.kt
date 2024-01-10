package com.example.presentation.products.viewModel

import app.cash.turbine.test
import com.example.domain.common.Result
import com.example.domain.usecase.GetProductListUseCaseImpl
import com.example.presentation.CoroutinesTestRule
import com.example.presentation.mapper.ProductsMapper
import com.example.presentation.products.ProductListSideEffect
import com.example.presentation.products.ProductListViewIntent
import com.example.presentation.products.ProductListViewModel
import com.example.presentation.products.ProductListViewState
import com.example.presentation.utils.FakeDataProvider
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductListViewModelTest {

    private lateinit var mockGetProductsUseCaseImpl: GetProductListUseCaseImpl
    private lateinit var mockProductsMapper: ProductsMapper
    private lateinit var viewModel: ProductListViewModel

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private val expectedMapProducts = FakeDataProvider.fakeMapProducts

    @Before
    fun setUp() {
        mockGetProductsUseCaseImpl = mockk()
        mockProductsMapper = mockk()
        viewModel = ProductListViewModel(mockGetProductsUseCaseImpl, mockProductsMapper)
    }

    @Test
    fun `Given product list is available, when send Intent with FetchProductListList is called, then emit a Success state with list of products`() {
        runTest {
            coEvery { mockProductsMapper.map(FakeDataProvider.fakeProductResponseList) }.returns(
                expectedMapProducts
            )
            val expectedResultProducts = FakeDataProvider.fakeProductResponseList
            val resultFlow = flowOf(Result.Success(expectedResultProducts))
            coEvery { mockGetProductsUseCaseImpl.invoke() } returns resultFlow

            // When
            viewModel.sendIntent(ProductListViewIntent.FetchProductListList)

            viewModel.state.test {
                val initialEmitState = awaitItem()
                assert(initialEmitState is ProductListViewState.Loading)
                val emittedState = awaitItem()
                assert(emittedState is ProductListViewState.Success)
                val emittedProducts = (emittedState as ProductListViewState.Success).data
                assertEquals(expectedMapProducts, emittedProducts)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `Given an error from the product list fetch, when fetchProducts is called, then emit an Error state`() {
        runTest {
            val expected = Throwable(FakeDataProvider.error_msg)
            val resultFlow = flowOf(Result.Error<Nothing>(expected, null))
            coEvery { mockGetProductsUseCaseImpl.invoke() } returns resultFlow

            // When
            viewModel.sendIntent(ProductListViewIntent.FetchProductListList)

            viewModel.state.test {
                awaitItem() // Wait for the initial state to be emitted
                val emittedState = awaitItem()
                assert(emittedState is ProductListViewState.Error)
                val emittedError = (emittedState as ProductListViewState.Error).throwable
                assertEquals(expected, emittedError)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `Given list of products displayed when OnProductItemClick then emit NavigateToProductDetails side effect`() =
        runTest {
            // Given
            coEvery { mockProductsMapper.map(FakeDataProvider.fakeProductResponseList) }.returns(
                expectedMapProducts
            )
            val expectedResultProducts = FakeDataProvider.fakeProductResponseList
            val resultFlow = flowOf(Result.Success(expectedResultProducts))
            coEvery { mockGetProductsUseCaseImpl.invoke() } returns resultFlow

            // When
            viewModel.sendIntent(ProductListViewIntent.OnProductItemClick(FakeDataProvider.productId_1))

            viewModel.sideEffect.test {
                val emittedState = awaitItem()
                assert(emittedState is ProductListSideEffect.NavigateToProductDetails)
            }
        }
}
