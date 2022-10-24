package uz.gita.memorygame.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.memorygame.data.model.Level

interface MenuScreenUseCase {

    fun getLevel(): Flow<String>

    suspend fun setLevel(level: String)
}