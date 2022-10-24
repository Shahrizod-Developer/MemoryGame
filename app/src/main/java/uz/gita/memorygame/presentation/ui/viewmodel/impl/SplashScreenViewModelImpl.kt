package uz.gita.memorygame.presentation.ui.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.memorygame.presentation.navigator.Navigator
import uz.gita.memorygame.presentation.ui.screen.fragment.splash.SplashScreenDirections
import uz.gita.memorygame.presentation.ui.viewmodel.SplashScreenViewModel
import javax.inject.Inject


@HiltViewModel
class SplashScreenViewModelImpl @Inject constructor(
    private val navigator: Navigator
) : SplashScreenViewModel, ViewModel() {
    override fun openMenuScreen() {
        viewModelScope.launch {
            delay(2500)
            navigator.navigateTo(SplashScreenDirections.actionSplashScreenToMenuScreen())
        }
    }


}