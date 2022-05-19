package com.aseemwangoo.handsonkotlin.shared.core

import com.aseemwangoo.handsonkotlin.home.view.HomeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindHomeRepository(

    ): HomeRepository
}