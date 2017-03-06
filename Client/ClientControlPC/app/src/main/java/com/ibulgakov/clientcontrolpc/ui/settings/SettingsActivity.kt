package com.ibulgakov.clientcontrolpc.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.ibulgakov.clientcontrolpc.Consts
import com.ibulgakov.clientcontrolpc.R
import com.ibulgakov.clientcontrolpc.ui.base.BaseActivity
import com.ibulgakov.clientcontrolpc.utils.Prefs
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class SettingsActivity : BaseActivity() {

    companion object {
        fun getIntent(context: Context) =
                Intent(context, SettingsActivity::class.java)
    }

    private lateinit var tvJobLocation: TextView
    private lateinit var etDistance: EditText
    private lateinit var btnSave: Button

    private var jobDistance: Int
        get() = Prefs.get().getInt(Consts.Prefs.KEY_JOB_DISTANCE, 0)
        set(value) = Prefs.edit().putInt(Consts.Prefs.KEY_JOB_DISTANCE, value).apply()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        tvJobLocation = find(R.id.tvMaps)
        etDistance = find(R.id.etDistance)
        btnSave = find(R.id.btnSave)

        etDistance.setText(jobDistance.toString(), TextView.BufferType.EDITABLE)

        setClickListeners()
    }

    private fun setClickListeners() {
        tvJobLocation.onClick {
            startActivity(SettingsMapsActivity.getIntent(this))
        }
        btnSave.onClick {
            jobDistance = etDistance.text.toString().toInt()
            toast("Сохранено!")
        }
    }

}
