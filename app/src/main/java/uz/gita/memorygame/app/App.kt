package uz.gita.memorygame.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import uz.gita.memorygame.data.shp.MySharedPreference


@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MySharedPreference.init(this)
    }
}