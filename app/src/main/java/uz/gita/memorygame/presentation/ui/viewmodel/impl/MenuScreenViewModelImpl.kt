package uz.gita.memorygame.presentation.ui.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.memorygame.data.model.Level
import uz.gita.memorygame.domain.usecase.MenuScreenUseCase
import uz.gita.memorygame.presentation.navigator.Navigator
import uz.gita.memorygame.presentation.ui.screen.fragment.main.MenuScreenDirections
import uz.gita.memorygame.presentation.ui.viewmodel.MenuScreenViewModel
import uz.gita.memorygame.utils.eventFlow
import javax.inject.Inject


@HiltViewModel
class MenuScreenViewModelImpl @Inject constructor(
    private val useCase: MenuScreenUseCase,
    private val navigator: Navigator
) : MenuScreenViewModel, ViewModel() {

    override val levelFlow = eventFlow<String>()

    init {
        viewModelScope.launch {
            useCase.getLevel().collectLatest {
                levelFlow.emit(it)
            }
        }
    }

    override fun onClickPlay(level: String) {
        viewModelScope.launch {
            navigator.navigateTo(MenuScreenDirections.actionMenuScreenToMiniLevelScreen(level))
        }
    }

    override suspend fun setLevel(level: String) = useCase.setLevel(level)

    override fun onClickNext(level: String) {
        viewModelScope.launch {
            when (level) {
                "4x4\neasy" -> {
                    levelFlow.emit(
                        "6x6\n" +
                                "medium"
                    )
                }
                "6x6\nmedium" -> {
                    levelFlow.emit(
                        "6x8\n" +
                                "hard"
                    )
                }
                "6x8\nhard" -> {
                    levelFlow.emit(
                        "4x4\n" +
                                "easy"
                    )
                }
            }
        }
    }

    override fun onClickPrev(level: String) {
        viewModelScope.launch {
            viewModelScope.launch {
                when (level) {
                    "4x4\neasy" -> {
                        levelFlow.emit(
                            "6x8\n" +
                                    "hard"
                        )
                    }
                    "6x6\nmedium" -> {
                        levelFlow.emit(
                            "4x4\n" +
                                    "easy"
                        )
                    }
                    "6x8\nhard" -> {
                        levelFlow.emit(
                            "6x6\n" +
                                    "medium"
                        )
                    }
                }
            }
        }
    }

    override fun onClickSetting() {

    }

    override fun onClickInfo() {

    }


}