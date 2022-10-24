package uz.gita.memorygame.presentation.ui.viewmodel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.memorygame.data.model.Level

interface MenuScreenViewModel {

    val levelFlow: Flow<String>

    fun onClickPlay(level: String)

    suspend fun setLevel(level: String)

    fun onClickNext(level: String)

    fun onClickPrev(level: String)

    fun onClickSetting()

    fun onClickInfo()

}