package com.app.lbgpoc.feature.detail.domain.usecase

import com.app.lbgpoc.core.common.Result
import com.app.lbgpoc.feature.detail.domain.model.DetailProduct
import com.app.lbgpoc.feature.detail.domain.model.Rating
import com.app.lbgpoc.feature.detail.domain.repository.DetailProductRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class GetProductDetailsUseCaseTest {

    private lateinit var repository: DetailProductRepository
    private lateinit var getProductDetailsUseCase: GetProductDetailsUseCase

    @Before
    fun setup() {
        repository = mockk()
        getProductDetailsUseCase = GetProductDetailsUseCase(repository)
    }

    @Test
    fun `invoke should return Flow of Result from repository given productId`() = runTest {
        // Arrange
        val productId = 1
        val mockProduct = DetailProduct(id = productId, title = "Product 1", price = 10.0, category = "cat1", imageUrl = "", description = "desc", rating = Rating(0.0, 0))
        val expectedFlow = flowOf(Result.Success(mockProduct))
        coEvery { repository.getProductDetails(productId) } returns expectedFlow

        // Act
        val resultList = getProductDetailsUseCase(productId).toList()

        // Assert
        assertEquals(1, resultList.size)
        assertTrue(resultList[0] is Result.Success)
        assertEquals(mockProduct, (resultList[0] as Result.Success).data)
    }

    @Test
    fun `invoke should return error Result when repository fails`() = runTest {
        // Arrange
        val productId = 1
        val expectedFlow = flowOf(Result.Error("Network error"))
        coEvery { repository.getProductDetails(productId) } returns expectedFlow

        // Act
        val resultList = getProductDetailsUseCase(productId).toList()

        // Assert
        assertEquals(1, resultList.size)
        assertTrue(resultList[0] is Result.Error)
        assertEquals("Network error", (resultList[0] as Result.Error).message)
    }
}
