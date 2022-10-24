package uz.gita.memorygame.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.memorygame.data.model.GameModel
import uz.gita.memorygame.data.model.Level
import uz.gita.memorygame.data.room.entity.GameEntity


interface GameScreenViewModel {

    val gameModelLiveData: Flow<List<GameModel>>

    fun back()

    fun getByNumber(level: Int, number: Int)

    fun saveResult(entity: GameEntity)

    fun btnClicked(state: Boolean, position: Int)

    fun openNextLevel(level: Int, number: Int)
}