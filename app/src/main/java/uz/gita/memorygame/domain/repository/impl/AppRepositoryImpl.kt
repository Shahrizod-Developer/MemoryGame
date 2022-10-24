package uz.gita.memorygame.domain.repository.impl

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import uz.gita.memorygame.R
import uz.gita.memorygame.data.images.Database
import uz.gita.memorygame.data.model.GameModel
import uz.gita.memorygame.data.model.Level
import uz.gita.memorygame.data.room.dao.GameDao
import uz.gita.memorygame.data.room.entity.GameEntity
import uz.gita.memorygame.data.shp.MySharedPreference
import uz.gita.memorygame.domain.repository.AppRepository
import javax.inject.Inject
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine


class AppRepositoryImpl @Inject constructor(
    private val dao: GameDao
) : AppRepository {

    private val gson = Gson()
    private val shp = MySharedPreference.getInstance()

    override fun getAllEasyLevel(): Flow<List<GameEntity>> = flow<List<GameEntity>> {
        if (!shp.firstEasy) {
            val level = Level.EASY
            val list = ArrayList<GameEntity>()

            val count = level.x * level.y
            val data = Database.images
            var s = ""
            for (i in 0..51) {

                data.shuffle()
                val a = data.subList(0, count / 2)

                val result = ArrayList<GameModel>()
                result.addAll(a)
                result.addAll(a)
                result.shuffle()

                val type = object : TypeToken<List<GameModel>>() {}.type
                s = gson.toJson(result, type)

                list.add(GameEntity(0, s, i + 1, 0, 0, 1, false))
            }

            dao.insert(list)
            shp.firstEasy = true
        }
        dao.getAllEasyLevel().map {
            emit(it)
        }.collect()

    }.flowOn(Dispatchers.IO)

    override fun getAllMediumLevel(): Flow<List<GameEntity>> = flow<List<GameEntity>> {
        if (!shp.firstMedium) {
            val level = Level.MEDIUM
            val list = ArrayList<GameEntity>()

            val count = level.x * level.y
            val data = Database.images
            var s = ""
            for (i in 0..51) {
                data.shuffle()
                val a = data.subList(0, count / 2)

                val result = ArrayList<GameModel>()
                result.addAll(a)
                result.addAll(a)
                result.shuffle()

                val type = object : TypeToken<List<GameModel>>() {}.type
                s = gson.toJson(result, type)
                list.add(GameEntity(0, s, i + 1, 0, 0, 2, false))
            }

            dao.insert(list)
            shp.firstMedium = true
        }
        dao.getAllMediumLevel().map {
            emit(it)
        }.collect()
    }.flowOn(Dispatchers.IO)

    override fun getAllHardLevel(): Flow<List<GameEntity>> = flow<List<GameEntity>> {
        if (!shp.firstHard) {
            val level = Level.HARD
            val list = ArrayList<GameEntity>()

            val count = level.x * level.y
            val data = Database.images
            var s = ""
            for (i in 0..51) {
                data.shuffle()
                val a = data.subList(0, count / 2)

                val result = ArrayList<GameModel>()
                result.addAll(a)
                result.addAll(a)
                result.shuffle()

                val type = object : TypeToken<List<GameModel>>() {}.type
                s = gson.toJson(result, type)
                list.add(GameEntity(0, s, i + 1, 0, 0, 3, false))

            }
            dao.insert(list)
            shp.firstHard = true
        }
        dao.getAllHardLevel().map {
            emit(it)
        }.collect()
    }.flowOn(Dispatchers.IO)

    override fun update(entity: GameEntity) = dao.update(entity)

    override fun getLevel(): Flow<String> = flow {
        emit(shp.level)
    }

    override suspend fun openNextLevel(level: Int, number: Int) {
        dao.getAllLevel().map { it ->
            it.map {
                if (it.level == level && it.number == number) {
                    it.state = true
                    dao.update(it)
                }
            }
        }.collect()
    }

    override suspend fun setLevel(level: String) {
        shp.level = level
    }

    override fun getByNumber(level: Int, number: Int): Flow<List<GameModel>> = flow {

        val type = object : TypeToken<List<GameModel>>() {}.type
        dao.getByNumber(level, number).map {
            val gameModel = gson.fromJson<List<GameModel>>(it.imageList, type)
            emit(gameModel)
        }.collect()

    }.flowOn(Dispatchers.IO)
}