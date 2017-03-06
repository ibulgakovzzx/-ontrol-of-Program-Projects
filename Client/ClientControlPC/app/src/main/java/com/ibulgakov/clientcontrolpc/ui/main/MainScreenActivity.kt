package com.ibulgakov.clientcontrolpc.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.google.android.gms.maps.model.LatLng
import com.ibulgakov.clientcontrolpc.MainApp
import com.ibulgakov.clientcontrolpc.R
import com.ibulgakov.clientcontrolpc.ui.base.BaseActivity
import com.ibulgakov.clientcontrolpc.ui.settings.SettingsActivity
import com.ibulgakov.clientcontrolpc.utils.TimeTrackerUtils
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class MainScreenActivity: BaseActivity() {

    private lateinit var tvStatus: TextView
    private lateinit var btnUpdate: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        tvStatus = find(R.id.tvStatus)
        btnUpdate = find(R.id.btnUpdate)


        tvStatus.text = getStatusText()
        MainApp.globalBus.observeEvents(LatLng::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { tvStatus.text = getStatusText() }

        btnUpdate.onClick {
            tvStatus.text = getStatusText()
        }
    }

    private fun getStatusText() : String =
            if (TimeTrackerUtils.hasSettingJobPlace()) {
                if (TimeTrackerUtils.isInWork()) {
                    getString(R.string.main_screen_status_in_work)
                } else {
                    getString(R.string.main_screen_status_out_work)
                }
            } else {
                getString(R.string.main_screen_status_need_set_job_place)
            }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_settings -> {
                startActivity(SettingsActivity.getIntent(this))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }
}
