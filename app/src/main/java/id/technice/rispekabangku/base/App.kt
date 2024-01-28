package id.technice.rispekabangku.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import id.technice.rispekabangku.helper.SharedPref

@HiltAndroidApp
class App:Application() {
    companion object {
        lateinit var pref: SharedPref
    }


    override fun onCreate() {
        super.onCreate()
        pref = SharedPref(this)
    }




}