package com.app.lbgpoc.feature.list.domain.usecase

import com.app.lbgpoc.core.common.Result
import com.app.lbgpoc.core.common.DataError
import com.app.lbgpoc.feature.list.domain.model.Product
import com.app.lbgpoc.feature.list.domain.model.Rating
import com.app.lbgpoc.feature.list.domain.repository.ListProductRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetProductsUseCaseTest {

    private lateinit var repository: ListProductRepository
    private lateinit var getProductsUseCase: GetProductsUseCase

    @Before
    fun setup() {
        repository = mockk()
        getProductsUseCase = GetProductsUseCase(repository)
    }

    @Test
    fun `invoke should return Flow of Result from repository`() = runTest {
        // Arrange
        val mockProducts = listOf(
            Product(
                id = 1,
                title = "Product 1",
                price = 10.0,
                imageUrl = "",
                description = "Desc 1",
                category = "Cat 1",
                rating = Rating(4.0, 10)
            ),
            Product(
                id = 2,
                title = "Product 2",
                price = 20.0,
                imageUrl = "",
                description = "Desc 2",
                category = "Cat 2",
                rating = Rating(3.5, 5)
            )
        )
        val expectedFlow = flowOf(Result.Success(mockProducts))
        coEvery { repository.getProducts() } returns expectedFlow

        // Act
        val resultList = getProductsUseCase().toList()

        // Assert
        assertEquals(1, resultList.size)
        assertTrue(resultList[0] is Result.Success)
        assertEquals(mockProducts, (resultList[0] as Result.Success).data)
    }

    @Test
    fun `invoke should return error Result when repository fails`() = runTest {
        // Arrange
        val expectedFlow = flowOf(Result.Error(DataError.Network.UNKNOWN))
        coEvery { repository.getProducts() } returns expectedFlow

        // Act
        val resultList = getProductsUseCase().toList()

        // Assert
        assertEquals(1, resultList.size)
        assertTrue(resultList[0] is Result.Error)
        assertEquals(DataError.Network.UNKNOWN, (resultList[0] as Result.Error).error)
    }
}
