package uz.gita.memorygame.presentation.navigator

import androidx.navigation.NavController
import androidx.navigation.NavDirections
import kotlinx.coroutines.flow.MutableSharedFlow
import uz.gita.memorygame.presentation.navigator.Direction
import uz.gita.memorygame.presentation.navigator.NavigationArgs
import uz.gita.memorygame.presentation.navigator.NavigationHandler
import uz.gita.memorygame.presentation.navigator.Navigator
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NavigationDispatcher @Inject constructor() : Navigator, NavigationHandler {
    override val navigationStack = MutableSharedFlow<(NavController) -> Unit>()

    private suspend fun navigator(args: NavController.() -> Unit) {
        navigationStack.emit(args)
    }

    override suspend fun navigateTo(direction: NavDirections) = navigator { navigate(direction) }

    override suspend fun back() = navigator { popBackStack() }

}