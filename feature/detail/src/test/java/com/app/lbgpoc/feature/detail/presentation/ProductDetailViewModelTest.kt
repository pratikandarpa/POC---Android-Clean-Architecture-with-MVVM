package com.app.lbgpoc.feature.detail.presentation

import com.app.lbgpoc.core.common.Result
import com.app.lbgpoc.feature.detail.domain.model.DetailProduct
import com.app.lbgpoc.feature.detail.domain.model.Rating
import com.app.lbgpoc.feature.detail.domain.usecase.GetProductDetailsUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProductDetailViewModelTest {

    private lateinit var getProductDetailsUseCase: GetProductDetailsUseCase
    private lateinit var viewModel: ProductDetailViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getProductDetailsUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProductDetail updates state to Success`() = runTest {
        // Arrange
        val productId = 1
        val mockProduct = DetailProduct(
            id = productId,
            title = "Product 1",
            price = 10.0,
            category = "cat",
            imageUrl = "",
            description = "desc",
            rating = Rating(0.0, 0)
        )

        coEvery { getProductDetailsUseCase(productId) } returns flowOf(
            Result.Loading,
            Result.Success(mockProduct)
        )

        // Instantiate ViewModel
        viewModel = ProductDetailViewModel(getProductDetailsUseCase)

        // Assert initial state
        assertFalse(viewModel.detailState.value.isLoading)
        assertNull(viewModel.detailState.value.product)

        // Act 
        viewModel.loadProductDetail(productId)

        // Assert final state
        val finalState = viewModel.detailState.value
        assertFalse(finalState.isLoading)
        assertEquals(mockProduct, finalState.product)
        assertNull(finalState.error)
    }

    @Test
    fun `loadProductDetail updates state to Error`() = runTest {
        // Arrange
        val productId = 1
        coEvery { getProductDetailsUseCase(productId) } returns flowOf(
            Result.Loading,
            Result.Error("Network Failure")
        )

        // Instantiate ViewModel
        viewModel = ProductDetailViewModel(getProductDetailsUseCase)

        // Act
        viewModel.loadProductDetail(productId)

        // Assert final state
        val finalState = viewModel.detailState.value
        assertFalse(finalState.isLoading)
        assertEquals("Network Failure", finalState.error)
        assertNull(finalState.product)
    }
}
