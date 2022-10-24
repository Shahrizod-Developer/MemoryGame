package uz.gita.memorygame.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.memorygame.data.model.Level
import uz.gita.memorygame.domain.repository.AppRepository
import uz.gita.memorygame.domain.usecase.MenuScreenUseCase
import javax.inject.Inject

class MenuScreenUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) : MenuScreenUseCase {

    override fun getLevel(): Flow<String> = repository.getLevel()

    override suspend fun setLevel(level: String) = repository.setLevel(level)
}