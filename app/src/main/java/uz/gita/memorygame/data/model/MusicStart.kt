package uz.gita.memorygame.data.model

import android.content.Context
import android.media.MediaPlayer
import uz.gita.memorygame.R

object BackMusic {

    var mediaPlayer = MediaPlayer()

    fun create(context: Context) {
        mediaPlayer = MediaPlayer.create(context, R.raw.music)
        mediaPlayer.start()
    }
}