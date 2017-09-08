package com.liuqi.screenqueen.widage

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.net.Uri
import android.widget.RemoteViews
import com.liuqi.screenqueen.R
import com.liuqi.screenqueen.domin.utils.FileUtil
import java.io.File

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in [BeautyWidgetConfigureActivity]
 */
class BeautyWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {

    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    companion object {

        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager,
                                     appWidgetId: Int) {
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.beauty_widget)
            val file = File(FileUtil.getOutFilePath(FileUtil.PATH_PHOTOGRAPH))
            val fileArray = file.listFiles()
            val index = (Math.random() * fileArray.size).toInt()
            val path = fileArray[index].absolutePath
            try {
                views.setImageViewUri(R.id.image_beauty, Uri.parse("file://" + path));
            } catch (e: Exception) {
                views.setImageViewResource(R.id.image_beauty, R.drawable.ic_widget_beauty);
            }

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

