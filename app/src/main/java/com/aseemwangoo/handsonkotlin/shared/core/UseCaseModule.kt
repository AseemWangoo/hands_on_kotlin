package com.aseemwangoo.handsonkotlin.shared.core

import com.aseemwangoo.handsonkotlin.home.view.GetHomeTasksUseCase
import com.aseemwangoo.handsonkotlin.home.view.HomeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun bindHomeUseCase(
        homeUseCase: HomeUseCase,
    ): GetHomeTasksUseCase
}