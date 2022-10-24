package uz.gita.memorygame.presentation.ui.screen.fragment.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.memorygame.R
import uz.gita.memorygame.databinding.ScreenSplashBinding
import uz.gita.memorygame.presentation.ui.viewmodel.SplashScreenViewModel
import uz.gita.memorygame.presentation.ui.viewmodel.impl.SplashScreenViewModelImpl

@AndroidEntryPoint
class SplashScreen : Fragment(R.layout.screen_splash) {

    private val binding: ScreenSplashBinding by viewBinding(ScreenSplashBinding::bind)
    private val viewModel: SplashScreenViewModel by viewModels<SplashScreenViewModelImpl>()

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.openMenuScreen()
        memoryAnimation(binding.memory)
        gameAnimation(binding.game)

    }

    private fun memoryAnimation(view: View) {

        val rotation = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f)
        val transitionY =
            ObjectAnimator.ofFloat(
                view,
                "translationY",
                view.y,
                320f
            )

        AnimatorSet().apply {
            play(transitionY).with(rotation)
            duration = 2000
            start()
        }
    }

    private fun gameAnimation(view: View) {

        val rotation = ObjectAnimator.ofFloat(view, "rotation", 0f, -360f)
        val transitionY =
            ObjectAnimator.ofFloat(
                view,
                "translationY",
                view.y,
                -320f
            )

        AnimatorSet().apply {
            play(transitionY).with(rotation)
            duration = 2000
            start()
        }
    }
}