package com.ibulgakov.clientcontrolpc.utils

import android.util.Log
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class Logger {

    companion object {
        private val TAG = Logger::class.java.simpleName

        private val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.LONG)
        //private val loggerFileName: String = ZonedDateTime.now().format(dateFormatter)
        private val loggerFileName = "test"

        fun print(className: String, message: String) {
            val time = ZonedDateTime.now().format(dateFormatter)
            val line = "\n$time Модуль $className \n" +
                    "$message\n" +
                    "---------------------------------------------------------------"
            Log.d(TAG, message)
            FileUtils.write(loggerFileName, line)
        }
    }


}