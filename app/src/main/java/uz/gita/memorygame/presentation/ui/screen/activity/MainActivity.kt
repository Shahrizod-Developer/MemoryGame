package uz.gita.memorygame.presentation.ui.screen.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.memorygame.R
import uz.gita.memorygame.data.model.BackMusic
import uz.gita.memorygame.data.shp.MySharedPreference
import uz.gita.memorygame.presentation.navigator.NavigationHandler
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigationHandler: NavigationHandler
    private val shp = MySharedPreference.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navigationHandler.navigationStack
            .onEach {
                it.invoke(fragment.findNavController())
            }
            .launchIn(lifecycleScope)
    }

    override fun onStart() {
        super.onStart()
        if (shp.music) {
            BackMusic.create(this)
            BackMusic.mediaPlayer.isLooping = true
        } else {
            BackMusic.mediaPlayer.pause()
        }
    }

    override fun onPause() {
        super.onPause()
        BackMusic.mediaPlayer.pause()
    }
}