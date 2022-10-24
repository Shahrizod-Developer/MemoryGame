package uz.gita.memorygame.presentation.ui.screen.fragment.game

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.widget.Chronometer
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.memorygame.R
import uz.gita.memorygame.data.model.GameModel
import uz.gita.memorygame.data.model.Level
import uz.gita.memorygame.data.room.entity.GameEntity
import uz.gita.memorygame.data.shp.MySharedPreference
import uz.gita.memorygame.databinding.DialogExitBinding
import uz.gita.memorygame.databinding.DialogWinBinding
import uz.gita.memorygame.databinding.ScreenGameBinding
import uz.gita.memorygame.presentation.ui.viewmodel.GameScreenViewModel
import uz.gita.memorygame.presentation.ui.viewmodel.impl.GameScreenViewModelImpl
import uz.gita.memorygame.utils.ExplosionField
import java.text.SimpleDateFormat
import kotlin.math.abs
import kotlin.math.log

@AndroidEntryPoint
class GameScreen : Fragment(R.layout.screen_game) {

    private val binding: ScreenGameBinding by viewBinding(ScreenGameBinding::bind)
    private val viewModel: GameScreenViewModel by viewModels<GameScreenViewModelImpl>()
    private lateinit var dateFormat: SimpleDateFormat

    private var level = Level.EASY
    private var _width = 0
    private var _height = 0
    private var count = 0
    private var cliCount = 0
    private var countWin = 0
    private var star: Int = 0
    private var a: Long = 0
    private var list = ArrayList<ImageView>()
    private lateinit var images: ArrayList<ImageView>
    private var totalTime: Long = 0
    private var startTime: Long = 0
    private val args: GameScreenArgs by navArgs()
    private lateinit var time: Chronometer
    private var number: Int = 0
    private val shp = MySharedPreference.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            showExitDialog()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        number = args.game.number
        binding.level.text = number.toString()
        time = binding.time
        time.base = SystemClock.elapsedRealtime()
        startTime = SystemClock.elapsedRealtime()
        time.start()

        when (args.game.level) {
            1 -> {
                count = 4 * 4
                level = Level.EASY
            }
            2 -> {
                count = 6 * 6
                level = Level.MEDIUM
            }
            3 -> {
                count = 6 * 8
                level = Level.HARD
            }
        }


        load()
        viewModel.getByNumber(args.game.level, number)

        viewLifecycleOwner.lifecycleScope.launch {

            viewModel.gameModelLiveData.onEach {
                onClick(it)
            }.collect()

        }

        binding.back.setOnClickListener {
            showExitDialog()
        }
    }

    private fun onClick(it: List<GameModel>) {

        Log.d("MMM", it.size.toString())

        for (i in it.indices) {
            val imageView = images[i]
            imageView.tag = it[i]
            imageView.setOnClickListener {
                if (it.rotationY == 0f) {
                    if (cliCount == 1) {
                        openView(imageView)
                        cliCount++
                        val imageView1 = list[0]
                        if (imageView1.tag == imageView.tag) {
                            lifecycleScope.launch(Dispatchers.Main) {
                                delay(1000)
                                val mExplosionField = ExplosionField.attach2Window(activity)
                                mExplosionField.explode(imageView)
                                mExplosionField.explode(imageView1)
                                imageView.visibility = View.INVISIBLE
                                imageView1.visibility = View.INVISIBLE

                                countWin += 2
                                if (countWin == count) {
//                                    showResultDialog()
                                    totalTime = SystemClock.elapsedRealtime()
                                    time.stop()

                                    dateFormat = SimpleDateFormat("sss")
                                    a = abs(startTime - totalTime)

                                    when (args.game.level) {

                                        1 -> {
                                            when (a) {
                                                in 0..60000 -> {
                                                    star = 3
                                                }
                                                in 60000..120000 -> {
                                                    star = 2
                                                }
                                                in 120000..180000 -> {
                                                    star = 1
                                                }
                                            }
                                        }
                                        2 -> {
                                            when (totalTime) {
                                                in 0..180000 -> {
                                                    star = 3
                                                }
                                                in 180000..240000 -> {
                                                    star = 2
                                                }
                                                in 240000..300000 -> {
                                                    star = 1
                                                }
                                            }
                                        }
                                        3 -> {
                                            when (totalTime) {
                                                in 0..240000 -> {
                                                    star = 3
                                                }
                                                in 240000..300000 -> {
                                                    star = 2
                                                }
                                                in 300000..360000 -> {
                                                    star = 1
                                                }
                                            }
                                        }
                                    }

                                    if (number != 32 && star > 0) {
                                        viewModel.saveResult(
                                            GameEntity(
                                                id = args.game.id,
                                                imageList = args.game.imageList,
                                                number = number,
                                                star = star,
                                                time = totalTime,
                                                state = true,
                                                level = args.game.level
                                            )
                                        )
                                        number++
                                        viewModel.openNextLevel(args.game.level, number)

                                    }
                                    viewModel.back()
                                }

                                list.clear()
                                cliCount = 0
                            }
                        } else {
                            lifecycleScope.launch(Dispatchers.Main) {
                                delay(500)
                                if (shp.vibration) vibratePhone()
                                shakeView(imageView)
                                shakeView(imageView1)
                                delay(700)
                                closeView(imageView)
                                closeView(imageView1)
                                list.clear()
                                cliCount = 0
                            }

                        }
                    } else if (cliCount < 1) {
                        openView(imageView)
                        list.add(imageView)
                        cliCount++
                    }
                }
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun showResultDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val binding = DialogWinBinding.inflate(layoutInflater)
        builder.setView(binding.root)
        val alertDialog = builder.create()

        totalTime = SystemClock.elapsedRealtime()
        time.stop()

        dateFormat = SimpleDateFormat("sss")
        a = abs(startTime - totalTime)

        when (args.game.level) {

            1 -> {
                when (a) {
                    in 0..60000 -> {
                        star = 3
                    }
                    in 60000..120000 -> {
                        star = 2
                    }
                    in 120000..180000 -> {
                        star = 1
                    }
                }
            }
            2 -> {
                when (totalTime) {
                    in 0..180000 -> {
                        star = 3
                    }
                    in 180000..240000 -> {
                        star = 2
                    }
                    in 240000..300000 -> {
                        star = 1
                    }
                }
            }
            3 -> {
                when (totalTime) {
                    in 0..240000 -> {
                        star = 3
                    }
                    in 240000..300000 -> {
                        star = 2
                    }
                    in 300000..360000 -> {
                        star = 1
                    }
                }
            }
        }
        if (star > 0) {

            binding.next.alpha = 1f
            binding.empty.visibility = View.GONE
            binding.next.isClickable = true

            when (star) {
                1 -> {
                    binding.starOne.visibility = View.VISIBLE
                    binding.starTwo.visibility = View.GONE
                    binding.starThree.visibility = View.GONE
                }
                2 -> {
                    binding.starOne.visibility = View.VISIBLE
                    binding.starTwo.visibility = View.VISIBLE
                    binding.starThree.visibility = View.GONE
                }
                3 -> {
                    binding.starOne.visibility = View.VISIBLE
                    binding.starTwo.visibility = View.VISIBLE
                    binding.starThree.visibility = View.VISIBLE
                }
            }
            viewModel.saveResult(
                GameEntity(
                    id = args.game.id,
                    imageList = args.game.imageList,
                    number = number,
                    star = star,
                    time = totalTime,
                    state = true,
                    level = args.game.level
                )
            )
            if (number != 32) {
                number++
                viewModel.openNextLevel(args.game.level, number)
            }

        } else {
            binding.next.alpha = 0.7f
            binding.empty.visibility = View.VISIBLE
            binding.next.isClickable = false
        }
        binding.restart.setOnClickListener {
            load()
            viewModel.getByNumber(args.game.level, args.game.number)
            time.base = SystemClock.elapsedRealtime()
            time.start()
            startTime = time.base
            alertDialog.dismiss()
        }

        binding.quit.setOnClickListener {
            viewModel.back()
            alertDialog.dismiss()
        }

        binding.next.setOnClickListener {

            if (args.game.number != 32) {

                load()
                viewModel.getByNumber(
                    args.game.level, number
                )

                this.binding.level.text = number.toString()
                time.base = SystemClock.elapsedRealtime()
                time.start()
                startTime = time.base
                alertDialog.dismiss()
            }
        }
        alertDialog.window!!.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )

        alertDialog.show()
    }

    private fun load() {
        images = ArrayList(count)
        binding.main.post {
            _width = (binding.ln.width - level.x * 10) / level.x + 10
            _height = (binding.ln.width - level.y * 10 + 200) / level.y + 10

            binding.containerImage.layoutParams.apply {
                width = _width * level.x
                height = _height * level.y+10
            }
            loadImages()
        }
    }


    private fun loadImages() {
        for (x in 0 until level.x) {
            for (y in 0 until level.y) {
                val imageView = ImageView(requireContext())
                binding.containerImage.addView(imageView)
                val lp = imageView.layoutParams as RelativeLayout.LayoutParams

                lp.apply {
                    width = (binding.ln.width - level.x * 10) / level.x
                    height = (binding.ln.width - level.y * 10) / level.y + 10
                }

                imageView.x = x * _width * 1f
                imageView.y = y * _height * 1f
                imageView.scaleType = ImageView.ScaleType.FIT_XY
                imageView.layoutParams = lp
                imageView.setImageResource(R.drawable.lock_svgrepo_com__1_)
                images.add(imageView)
            }
        }
    }

    private fun closeView(imageView: ImageView) {
        imageView.animate().setDuration(150).rotationY(90f).withEndAction {
            imageView.setImageResource(R.drawable.lock_svgrepo_com__1_)
            imageView.animate().setDuration(150).rotationY(0f).start()
        }.start()
    }

    private fun openView(imageView: ImageView) {
        imageView.animate().setDuration(150).rotationY(90f).withEndAction {
            imageView.setImageResource((imageView.tag as GameModel).image)
            imageView.animate().setDuration(150).rotationY(180f)
                .setInterpolator(DecelerateInterpolator()).start()
        }.start()
    }

    private fun shakeView(view: View) {
        view.animate().setDuration(50).xBy(8f).withEndAction {
            view.animate().setDuration(50).xBy(-16f)
                //.setInterpolator(DecelerateInterpolator())
                .withEndAction {
                    view.animate().setDuration(50).xBy(16f).withEndAction {
                        view.animate().setDuration(50).xBy(-8f).start()
                    }.start()
                }.start()
        }.start()
    }

    private fun Fragment.vibratePhone() {
        val vibrator = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    300, VibrationEffect.CONTENTS_FILE_DESCRIPTOR
                )
            )
        } else {
            vibrator.vibrate(300)
        }
    }

    private fun showExitDialog() {
        val dialog = AlertDialog.Builder(requireContext()).create()
        val dialogBinding =
            DialogExitBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        dialog.setCancelable(false)

        dialog.window!!.setBackgroundDrawable(
            ColorDrawable(
                Color.TRANSPARENT
            )
        )

        dialogBinding.no.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.yes.setOnClickListener {
            viewModel.back()
            dialog.dismiss()
        }

        dialog.setView(dialogBinding.root)
        dialog.show()
    }
}