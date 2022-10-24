package uz.gita.memorygame.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.memorygame.data.room.entity.GameEntity
import uz.gita.memorygame.domain.repository.AppRepository
import uz.gita.memorygame.presentation.ui.viewmodel.MiniScreenViewModel
import javax.inject.Inject

interface MiniLevelScreenUseCase {

    fun getAllEasyLevel(): Flow<List<GameEntity>>

    fun getAllMediumLevel(): Flow<List<GameEntity>>

    fun getAllHardLevel(): Flow<List<GameEntity>>
}