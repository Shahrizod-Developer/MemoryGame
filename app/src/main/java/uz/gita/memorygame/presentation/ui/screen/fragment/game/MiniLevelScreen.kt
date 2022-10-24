package uz.gita.memorygame.presentation.ui.screen.fragment.game

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.gita.memorygame.R
import uz.gita.memorygame.databinding.DialogInfoBinding
import uz.gita.memorygame.databinding.ScreenMiniGameBinding
import uz.gita.memorygame.presentation.adapter.LevelAdapter
import uz.gita.memorygame.presentation.ui.viewmodel.MiniScreenViewModel
import uz.gita.memorygame.presentation.ui.viewmodel.impl.MiniScreenViewModelImpl

@AndroidEntryPoint
class MiniLevelScreen : Fragment(R.layout.screen_mini_game) {

    private val binding: ScreenMiniGameBinding by viewBinding(ScreenMiniGameBinding::bind)
    private val viewModel: MiniScreenViewModel by viewModels<MiniScreenViewModelImpl>()
    private val adapter: LevelAdapter by lazy { LevelAdapter() }
    private val arg: MiniLevelScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        when (arg.level) {
            "4x4\neasy" -> {
                binding.title.text = "Easy"
                viewModel.generateEasy()
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.easyLevelsList.collectLatest {
                        if (it != null) {
                            binding.progress.visibility = View.GONE
                        } else {
                            binding.progress.visibility = View.VISIBLE
                        }
                        adapter.submitList(it)
                    }
                }

            }
            "6x6\nmedium" -> {
                viewModel.generateMedium()
                binding.title.text = "Medium"
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.mediumLevelsList.collectLatest {
                        if (it != null) {
                            binding.progress.visibility = View.GONE
                        } else {
                            binding.progress.visibility = View.VISIBLE
                        }
                        adapter.submitList(it)
                    }
                }
            }
            "6x8\nhard" -> {
                viewModel.generateHard()
                binding.title.text = "Hard"
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.hardLevelsList.collectLatest {
                        if (it != null) {
                            binding.progress.visibility = View.GONE
                        } else {
                            binding.progress.visibility = View.VISIBLE
                        }
                        adapter.submitList(it)
                    }
                }
            }
        }
        binding.list.adapter = adapter

        adapter.setSwitchChangedListener {
            viewModel.openGameScreen(it)
        }
        binding.back.setOnClickListener {
            viewModel.back()
        }
        binding.info.setOnClickListener {
            showInfoDialog(arg.level)
        }
    }

    private fun showInfoDialog(text: String) {
        val dialog = AlertDialog.Builder(requireContext()).create()
        val dialogBinding =
            DialogInfoBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        dialog.setCancelable(false)

        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )

        Log.d("TTT", arg.level)
        when (text) {
            "4x4\neasy" -> {
                dialogBinding.text.text =
                    "The terms of the game are as follows. In this level you will find a match between 16 pictures. If you finish the whole game in 1 minute, you get 3 stars, if you finish in 2 minutes, you get 2 stars, if you finish in 3 minutes, you get 1 star and you can go to the next level. If you can't find the matches within 3 minutes, the game is not over, but the next level is not unlocked and stars are not awarded."
            }
            "6x6\nmedium" -> {

                dialogBinding.text.text =
                    "The terms of the game are as follows. In this level you will find a match between 36 pictures. If you finish the whole game in 3 minute, you get 3 stars, if you finish in 4 minutes, you get 2 stars, if you finish in 5 minutes, you get 1 star and you can go to the next level. If you can't find the matches within 3 minutes, the game is not over, but the next level is not unlocked and stars are not awarded."
            }
            "6x8\nhard" -> {
                dialogBinding.text.text =
                    "The terms of the game are as follows. In this level you will find a match between 48 pictures. If you finish the whole game in 4 minute, you get 3 stars, if you finish in 5 minutes, you get 2 stars, if you finish in 6 minutes, you get 1 star and you can go to the next level. If you can't find the matches within 3 minutes, the game is not over, but the next level is not unlocked and stars are not awarded."

            }
        }
        dialogBinding.yes.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setView(dialogBinding.root)
        dialog.show()
    }

}