package uz.gita.memorygame.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import uz.gita.memorygame.data.room.entity.GameEntity
import uz.gita.memorygame.domain.repository.AppRepository
import uz.gita.memorygame.domain.usecase.MiniLevelScreenUseCase
import javax.inject.Inject

class MiniLevelScreenUseCaseImpl @Inject constructor(
    private val repository: AppRepository
) : MiniLevelScreenUseCase {

    override fun getAllEasyLevel(): Flow<List<GameEntity>> = repository.getAllEasyLevel()

    override fun getAllMediumLevel(): Flow<List<GameEntity>> = repository.getAllMediumLevel()

    override fun getAllHardLevel(): Flow<List<GameEntity>> = repository.getAllHardLevel()
}