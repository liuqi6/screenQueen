package com.liuqi.screenqueen

import android.appwidget.AppWidgetManager
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.liuqi.screenqueen.ui.beauty.BeautiyActivity
import com.liuqi.screenqueen.widage.ScreenWidgetConfigureActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sample_text.text = stringFromJNI()
        initListener()
    }

    private fun initListener() {
        sign_btn.setOnClickListener {
            val intent = Intent(this, ScreenWidgetConfigureActivity().javaClass)
            val prefs = getSharedPreferences(ScreenWidgetConfigureActivity.PREFS_NAME, 0)
            val id = prefs.getInt("id", AppWidgetManager.INVALID_APPWIDGET_ID)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, id)
            startActivity(intent)
        }
        beauty_btn.setOnClickListener {
            val intent = Intent(this, BeautiyActivity().javaClass)
            startActivity(intent)
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
