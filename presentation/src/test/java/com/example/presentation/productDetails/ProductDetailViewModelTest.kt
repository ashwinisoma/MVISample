package com.example.presentation.productDetails

import app.cash.turbine.test
import com.example.domain.common.Result
import com.example.domain.usecase.GetProductItemUseCase
import com.example.presentation.CoroutinesTestRule
import com.example.presentation.mapper.ProductItemMapper
import com.example.presentation.utils.FakeDataProvider
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductDetailViewModelTest {

    private lateinit var mockGetProductItemUseCase: GetProductItemUseCase
    private lateinit var mockProductItemMapper: ProductItemMapper
    private lateinit var viewModel: ProductDetailViewModel

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    private val expectedMapProducts = FakeDataProvider.fakeProduct

    @Before
    fun setUp() {
        mockGetProductItemUseCase = mockk()
        mockProductItemMapper = mockk()
        viewModel = ProductDetailViewModel(mockGetProductItemUseCase, mockProductItemMapper)
    }

    @Test
    fun `Given product Item is available, when send Intent with FetchProductDetail is called, then emit a Success state with a product item`() {
        runTest {
            coEvery { mockProductItemMapper.map(FakeDataProvider.fakeProductItem1) }.returns(
                expectedMapProducts
            )
            val expectedResultProductItem = FakeDataProvider.fakeProductItem1
            val resultFlow = flowOf(Result.Success(expectedResultProductItem))
            coEvery { mockGetProductItemUseCase.invoke(FakeDataProvider.productId_1) } returns resultFlow

            // When
            viewModel.sendIntent(ProductDetailViewIntent.FetchProductDetail(FakeDataProvider.productId_1))

            viewModel.state.test {
                val initialEmitState = awaitItem()
                assert(initialEmitState is ProductDetailViewState.Loading)
                val emittedState = awaitItem()
                assert(emittedState is ProductDetailViewState.Success)
                val emittedProducts = (emittedState as ProductDetailViewState.Success).data
                TestCase.assertEquals(expectedMapProducts, emittedProducts)
                cancelAndConsumeRemainingEvents()
            }
        }
    }

    @Test
    fun `Given an productId, productItem is not available , when FetchProductDetail is called, then emit an Error state`() {
        runTest {
            val expected = Throwable(FakeDataProvider.error_msg)
            val resultFlow = flowOf(Result.Error<Nothing>(expected, null))
            coEvery { mockGetProductItemUseCase.invoke(FakeDataProvider.productId_2) } returns resultFlow

            viewModel.sendIntent(ProductDetailViewIntent.FetchProductDetail(FakeDataProvider.productId_2))

            viewModel.state.test {
                awaitItem() // Wait for the initial state to be emitted
                val emittedState = awaitItem()
                assert(emittedState is ProductDetailViewState.Error)
                val emittedError = (emittedState as ProductDetailViewState.Error).throwable
                TestCase.assertEquals(expected, emittedError)
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
