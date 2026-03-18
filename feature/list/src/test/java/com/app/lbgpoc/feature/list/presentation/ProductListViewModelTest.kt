package com.app.lbgpoc.feature.list.presentation

import com.app.lbgpoc.core.common.Result
import com.app.lbgpoc.core.common.DataError
import com.app.lbgpoc.feature.list.domain.model.Product
import com.app.lbgpoc.feature.list.domain.model.Rating
import com.app.lbgpoc.feature.list.domain.usecase.GetProductsUseCase
import com.app.lbgpoc.feature.list.presentation.viewmodel.ProductListViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
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
class ProductListViewModelTest {

    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var viewModel: ProductListViewModel
    private val testDispatcher = StandardTestDispatcher()
    private val productsFlow = MutableSharedFlow<Result<List<Product>>>()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getProductsUseCase = mockk()
        coEvery { getProductsUseCase() } returns productsFlow
        viewModel = ProductListViewModel(getProductsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProducts updates state to Success`() = runTest {
        // Arrange
        val mockProducts = listOf(
            Product(
                id = 1,
                title = "Product 1",
                price = 10.0,
                imageUrl = "",
                description = "Description",
                category = "Category",
                rating = Rating(4.5, 100)
            )
        )

        // Act
        productsFlow.emit(Result.Loading)
        advanceUntilIdle()
        assertTrue(viewModel.listState.value.isLoading)
        productsFlow.emit(Result.Success(mockProducts))
        advanceUntilIdle()

        // Assert
        val finalState = viewModel.listState.value
        assertFalse(finalState.isLoading)
        assertEquals(mockProducts, finalState.products)
        assertNull(finalState.error)
    }

    @Test
    fun `loadProducts updates state to Error`() = runTest {
        // Act
        productsFlow.emit(Result.Loading)
        advanceUntilIdle()
        assertTrue(viewModel.listState.value.isLoading)
        productsFlow.emit(Result.Error(DataError.Network.UNKNOWN))
        advanceUntilIdle()

        // Assert
        val finalState = viewModel.listState.value
        assertFalse(finalState.isLoading)
        assertEquals(DataError.Network.UNKNOWN, finalState.error)
        assertTrue(finalState.products.isEmpty())
    }
}
