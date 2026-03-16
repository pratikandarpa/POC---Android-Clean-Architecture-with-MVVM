package com.app.lbgpoc.feature.list.data.di

import com.app.lbgpoc.feature.list.data.remote.FakeStoreListApi
import com.app.lbgpoc.feature.list.data.repository.ListProductRepositoryImpl
import com.app.lbgpoc.feature.list.domain.repository.ListProductRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ListDataModule {

    @Provides
    @Singleton
    fun provideFakeStoreListApi(retrofit: Retrofit): FakeStoreListApi {
        return retrofit.create(FakeStoreListApi::class.java)
    }
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ListRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindListProductRepository(
        listProductRepositoryImpl: ListProductRepositoryImpl
    ): ListProductRepository
}
