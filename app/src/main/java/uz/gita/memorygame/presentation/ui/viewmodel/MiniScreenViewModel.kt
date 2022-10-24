package uz.gita.memorygame.presentation.ui.viewmodel

import kotlinx.coroutines.flow.Flow
import uz.gita.memorygame.data.model.Level
import uz.gita.memorygame.data.room.entity.GameEntity

interface MiniScreenViewModel {

    val easyLevelsList: Flow<List<GameEntity>>
    val mediumLevelsList: Flow<List<GameEntity>>
    val hardLevelsList: Flow<List<GameEntity>>

    fun back()

    fun openGameScreen(gameEntity: GameEntity)

     fun generateEasy()
     fun generateMedium()
     fun generateHard()
}