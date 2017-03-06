package com.ibulgakov.clientcontrolpc.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Prefs private constructor() {
    init {
        throw IllegalStateException("No instances!")
    }

    companion object {
        private val gson = Gson()

        private var settings: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null

        @SuppressLint("CommitPrefEdits")
        fun init(context: Context) {
            settings = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)
            editor = settings!!.edit()
        }

        @JvmStatic fun get(): SharedPreferences {
            return settings!!
        }

        @JvmStatic fun edit(): SharedPreferences.Editor {
            return editor!!
        }

        @JvmStatic fun clear() {
            editor!!.clear().apply()
        }

        @JvmStatic fun <T> save(key: String, model: T) {
            edit().putString(key, gson.toJson(model, object : TypeToken<T>() {}.type)).apply()
        }

        @JvmStatic fun <T> load(key: String, klass: Class<T>): T? {
            val json = get().getString(key, null) ?: return null
            return gson.fromJson<T>(json, klass)
        }

        @JvmStatic fun remove(vararg keys: String) {
            for (key in keys) {
                editor!!.remove(key)
            }
            editor!!.apply()
        }
    }
}
