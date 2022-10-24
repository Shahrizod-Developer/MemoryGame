package uz.gita.memorygame.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.memorygame.data.room.dao.GameDao
import uz.gita.memorygame.data.room.entity.GameEntity


@Database(entities = [GameEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): GameDao
}