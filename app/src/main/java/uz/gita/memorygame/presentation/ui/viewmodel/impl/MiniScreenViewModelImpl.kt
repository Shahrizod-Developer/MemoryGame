package uz.gita.memorygame.presentation.ui.viewmodel.impl

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.memorygame.data.model.Level
import uz.gita.memorygame.data.room.entity.GameEntity
import uz.gita.memorygame.domain.usecase.MiniLevelScreenUseCase
import uz.gita.memorygame.presentation.navigator.Navigator
import uz.gita.memorygame.presentation.ui.screen.fragment.game.MiniLevelScreenDirections
import uz.gita.memorygame.presentation.ui.viewmodel.MiniScreenViewModel
import uz.gita.memorygame.utils.eventFlow
import javax.inject.Inject

@HiltViewModel
class MiniScreenViewModelImpl @Inject constructor(
    private val navigator: Navigator,
    private val useCase: MiniLevelScreenUseCase
) : MiniScreenViewModel, ViewModel() {

    override val easyLevelsList = eventFlow<List<GameEntity>>()

    override val mediumLevelsList = eventFlow<List<GameEntity>>()

    override val hardLevelsList = eventFlow<List<GameEntity>>()

    override fun back() {
        viewModelScope.launch {
            navigator.back()
        }
    }

    override fun openGameScreen(gameEntity: GameEntity) {
        viewModelScope.launch {
            navigator.navigateTo(
                MiniLevelScreenDirections.actionMiniLevelScreenToGameScreen(
                    gameEntity
                )
            )
        }
    }

    override fun generateEasy() {
        viewModelScope.launch {
            useCase.getAllEasyLevel().collectLatest {
                easyLevelsList.emit(it)
            }
        }

    }

    override fun generateMedium() {
        viewModelScope.launch {
            useCase.getAllMediumLevel().collectLatest {
                mediumLevelsList.emit(it)
            }
        }
    }

    override fun generateHard() {
        viewModelScope.launch {
            useCase.getAllHardLevel().collectLatest {
                hardLevelsList.emit(it)
            }
        }
    }
}