package com.ibulgakov.clientcontrolpc.utils

import android.util.Log

class Logger {

    companion object {
        private val TAG = Logger::class.java.simpleName

        fun print(message: String) {
            Log.d(TAG, message)
        }
    }


}