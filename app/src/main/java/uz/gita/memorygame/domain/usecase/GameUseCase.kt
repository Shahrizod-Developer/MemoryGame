package uz.gita.memorygame.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.memorygame.data.model.GameModel
import uz.gita.memorygame.data.model.Level
import uz.gita.memorygame.data.room.entity.GameEntity


interface GameUseCase {

    fun getByNumber(level: Int, number: Int): Flow<List<GameModel>>

    suspend fun saveResult(entity: GameEntity)

    suspend fun openNextLevel(level: Int, number: Int)
}