package uz.gita.memorygame.presentation.ui.screen.fragment.main

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.memorygame.R
import uz.gita.memorygame.data.model.BackMusic
import uz.gita.memorygame.data.model.Level
import uz.gita.memorygame.data.shp.MySharedPreference
import uz.gita.memorygame.databinding.DialogExitBinding
import uz.gita.memorygame.databinding.DialogInfoBinding
import uz.gita.memorygame.databinding.DialogSettingBinding
import uz.gita.memorygame.databinding.ScreenMenuBinding
import uz.gita.memorygame.presentation.ui.viewmodel.MenuScreenViewModel
import uz.gita.memorygame.presentation.ui.viewmodel.impl.MenuScreenViewModelImpl


@AndroidEntryPoint
class MenuScreen : Fragment(R.layout.screen_menu) {

    private val binding: ScreenMenuBinding by viewBinding(ScreenMenuBinding::bind)

    private val viewModel: MenuScreenViewModel by viewModels<MenuScreenViewModelImpl>()
    private lateinit var level: String
    private val shp = MySharedPreference.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.levelFlow.collectLatest {
                level = it
                binding.level.text = it
            }
        }

        binding.next.setOnClickListener {
            viewModel.onClickNext(level)
        }

        binding.prev.setOnClickListener {
            viewModel.onClickPrev(level)
        }

        binding.play.setOnClickListener {
            viewModel.onClickPlay(level)
        }
        binding.settings.setOnClickListener {
            settingDialog()
        }
        binding.info.setOnClickListener {
            showInfoDialog()
        }

    }

    override fun onPause() {
        super.onPause()
        lifecycleScope.launch {
            viewModel.setLevel(level)
        }
    }

    private fun settingDialog() {

        val dialog = AlertDialog.Builder(requireContext()).create()
        val dialogBinding =
            DialogSettingBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.musicSwitch.isChecked = BackMusic.mediaPlayer.isPlaying
        dialogBinding.musicSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                BackMusic.create(requireContext())
                BackMusic.mediaPlayer.isLooping = true
                shp.music = true
            } else {
                shp.music = false
                BackMusic.mediaPlayer.pause()
            }
        }
        dialogBinding.musicSwitch.isChecked = shp.music

        dialogBinding.soundSwitch.isChecked = shp.vibration
        dialogBinding.soundSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            shp.vibration = isChecked
        }
        dialogBinding.soundSwitch.isChecked = shp.vibration

        dialogBinding.lang.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setView(dialogBinding.root)
        dialog.show()
    }

    private fun showInfoDialog() {
        val dialog = AlertDialog.Builder(requireContext()).create()
        val dialogBinding =
            DialogInfoBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        dialog.setCancelable(false)

        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )

        dialogBinding.yes.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setView(dialogBinding.root)
        dialog.show()
    }

}