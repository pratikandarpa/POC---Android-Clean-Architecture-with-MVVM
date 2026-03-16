package com.app.lbgpoc.feature.detail.data.di

import com.app.lbgpoc.feature.detail.data.remote.FakeStoreDetailApi
import com.app.lbgpoc.feature.detail.data.repository.DetailProductRepositoryImpl
import com.app.lbgpoc.feature.detail.domain.repository.DetailProductRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DetailDataModule {

    @Provides
    @Singleton
    fun provideFakeStoreDetailApi(retrofit: Retrofit): FakeStoreDetailApi {
        return retrofit.create(FakeStoreDetailApi::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class DetailRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDetailProductRepository(
        detailProductRepositoryImpl: DetailProductRepositoryImpl
    ): DetailProductRepository
}
