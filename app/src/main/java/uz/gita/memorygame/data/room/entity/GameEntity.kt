package uz.gita.memorygame.data.room.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import uz.gita.memorygame.data.model.Level

@Parcelize
@Entity(tableName = "game")
data class GameEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val imageList: String,
    var number: Int,
    val star: Int,
    val time: Long,
    var level: Int,
    var state: Boolean,
) : Parcelable