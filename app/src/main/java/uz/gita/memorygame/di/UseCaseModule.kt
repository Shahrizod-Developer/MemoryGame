package uz.gita.memorygame.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import uz.gita.memorygame.domain.usecase.GameUseCase
import uz.gita.memorygame.domain.usecase.MenuScreenUseCase
import uz.gita.memorygame.domain.usecase.MiniLevelScreenUseCase
import uz.gita.memorygame.domain.usecase.impl.GameUseCaseImpl
import uz.gita.memorygame.domain.usecase.impl.MenuScreenUseCaseImpl
import uz.gita.memorygame.domain.usecase.impl.MiniLevelScreenUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {
    @Binds
    fun bindGameUseCase(impl: GameUseCaseImpl): GameUseCase

    @Binds
    fun bindMenuUseCase(impl: MenuScreenUseCaseImpl): MenuScreenUseCase

    @Binds
    fun bindMiniLevelUseCase(impl: MiniLevelScreenUseCaseImpl): MiniLevelScreenUseCase
}