package uz.gita.memorygame.di

import android.content.Context
import android.provider.DocumentsContract.Root
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.memorygame.data.room.dao.GameDao
import uz.gita.memorygame.data.room.database.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @[Provides Singleton]
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "game.db").build()
    }

    @[Provides Singleton]
    fun provideDao(database: AppDatabase): GameDao = database.dao()

}