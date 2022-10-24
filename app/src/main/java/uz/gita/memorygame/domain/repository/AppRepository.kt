package uz.gita.memorygame.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.memorygame.data.model.GameModel
import uz.gita.memorygame.data.model.Level
import uz.gita.memorygame.data.room.entity.GameEntity

interface AppRepository {

    fun getAllEasyLevel(): Flow<List<GameEntity>>

    fun getAllMediumLevel(): Flow<List<GameEntity>>

    fun getAllHardLevel(): Flow<List<GameEntity>>

    fun update(entity: GameEntity)

    fun getLevel(): Flow<String>

    suspend fun openNextLevel(level: Int, number: Int)

    suspend fun setLevel(level: String)

    fun getByNumber(level: Int, number: Int): Flow<List<GameModel>>
}