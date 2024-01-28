package id.technice.rispekabangku.base

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import id.technice.rispekabangku.R


@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    fun removeActionBar() {
        supportActionBar?.hide()

        val window: Window = window
        val decorView: View = window.decorView
        val wic = WindowInsetsControllerCompat(window, decorView)


        wic.isAppearanceLightStatusBars = false  // true or false as desired.
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimary)
    }

    fun showSnackbar(view: View) {
        val strings = listOf("Tercopy abangku", "Copy King", "Tercopy kingg", "tersalin king", "Tersaling idola", "copy capt", "copy selalu capt")
        val randomString = strings.random()


        Snackbar.make(view, randomString, Snackbar.LENGTH_SHORT).show()
    }

}
