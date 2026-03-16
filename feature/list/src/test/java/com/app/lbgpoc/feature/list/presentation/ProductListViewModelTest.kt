package com.app.lbgpoc.feature.list.presentation

import com.app.lbgpoc.core.common.Result
import com.app.lbgpoc.feature.list.domain.model.ListProduct
import com.app.lbgpoc.feature.list.domain.usecase.GetProductsUseCase
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
class ProductListViewModelTest {

    private lateinit var getProductsUseCase: GetProductsUseCase
    private lateinit var viewModel: ProductListViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getProductsUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadProducts updates state to Success`() = runTest {
        // Arrange
        val mockProducts = listOf(
            ListProduct(id = 1, title = "Product 1", price = 10.0, imageUrl = "")
        )
        // Simulate a flow emitting loading then success
        coEvery { getProductsUseCase() } returns flowOf(
            Result.Loading,
            Result.Success(mockProducts)
        )

        // Instantiate ViewModel
        viewModel = ProductListViewModel(getProductsUseCase)
        
        // Assert initial state
        assertFalse(viewModel.listState.value.isLoading)
        assertTrue(viewModel.listState.value.products.isEmpty())

        // Act
        viewModel.loadProducts()

        // Assert final state: UnconfinedTestDispatcher executes coroutine eagerly,
        // so state is already updated to Success.
        val finalState = viewModel.listState.value
        assertFalse(finalState.isLoading)
        assertEquals(mockProducts, finalState.products)
        assertNull(finalState.error)
    }

    @Test
    fun `loadProducts updates state to Error`() = runTest {
        // Arrange
        coEvery { getProductsUseCase() } returns flowOf(
            Result.Loading,
            Result.Error("Network Failure")
        )

        // Instantiate ViewModel
        viewModel = ProductListViewModel(getProductsUseCase)

        // Act
        viewModel.loadProducts()

        // Assert final state
        val finalState = viewModel.listState.value
        assertFalse(finalState.isLoading)
        assertEquals("Network Failure", finalState.error)
        assertTrue(finalState.products.isEmpty())
    }
}
