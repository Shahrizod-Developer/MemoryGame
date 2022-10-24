package uz.gita.memorygame.presentation.ui.viewmodel.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import uz.gita.memorygame.data.model.GameModel
import uz.gita.memorygame.data.model.Level
import uz.gita.memorygame.data.room.entity.GameEntity
import uz.gita.memorygame.domain.usecase.GameUseCase
import uz.gita.memorygame.presentation.navigator.Navigator
import uz.gita.memorygame.presentation.ui.viewmodel.GameScreenViewModel
import uz.gita.memorygame.utils.eventFlow
import javax.inject.Inject


@HiltViewModel
class GameScreenViewModelImpl @Inject constructor(
    private val useCase: GameUseCase,
    private val navigator: Navigator
) :
    GameScreenViewModel, ViewModel() {

    override val gameModelLiveData = eventFlow<List<GameModel>>()
    override fun back() {
        viewModelScope.launch {
            navigator.back()
        }
    }

    override fun getByNumber(level: Int, number: Int) {
        viewModelScope.launch {
            useCase.getByNumber(level, number).collect {
                gameModelLiveData.emit(it)
            }
        }
    }

    override fun saveResult(entity: GameEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.saveResult(entity)
        }
    }

    override fun btnClicked(state: Boolean, position: Int) {

    }

    override fun openNextLevel(level: Int, number: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            useCase.openNextLevel(level, number)
        }
    }

}

