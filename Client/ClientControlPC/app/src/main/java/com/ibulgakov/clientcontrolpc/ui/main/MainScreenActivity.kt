package com.ibulgakov.clientcontrolpc.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import com.ibulgakov.clientcontrolpc.MainApp
import com.ibulgakov.clientcontrolpc.R
import com.ibulgakov.clientcontrolpc.SenderThread
import com.ibulgakov.clientcontrolpc.tests.TestMainScreenActivity
import com.ibulgakov.clientcontrolpc.tests.TestTimeTrackerUtils
import com.ibulgakov.clientcontrolpc.ui.base.BaseActivity
import com.ibulgakov.clientcontrolpc.ui.settings.SettingsActivity
import com.ibulgakov.clientcontrolpc.utils.TimeTrackerUtils
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers


class MainScreenActivity : BaseActivity(), SenderThread.Callback {

    companion object {
        fun getIntent(context: Context): Intent =
                Intent(context, MainScreenActivity::class.java)

        private val TAG = MainScreenActivity::class.java.simpleName
    }

    private lateinit var tvStatus: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnSendCommand: Button
    private lateinit var commandText: EditText

    internal var codeCommand: Byte = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        tvStatus = find(R.id.tvStatus)
        btnUpdate = find(R.id.btnUpdate)
        btnSendCommand = find(R.id.btnSendCommand)
        commandText = find(R.id.command)

        TestTimeTrackerUtils()
        TestMainScreenActivity()

        tvStatus.text = getStatusText(TimeTrackerUtils.isInWork())
        MainApp.globalBus.observeEvents(LatLng::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { tvStatus.text = getStatusText(TimeTrackerUtils.isInWork()) }

        btnUpdate.onClick {
            tvStatus.text = getStatusText(TimeTrackerUtils.isInWork())
        }

        btnSendCommand.onClick {
            val message = commandText.text.toString()
            codeCommand = message[0].toByte()
            val sender = SenderThread(this, codeCommand)
            sender.execute()
        }
    }

    fun getStatusText(isInWork: Boolean?): String =
            if (isInWork!!) {
                //getString(R.string.main_screen_status_in_work)
                "Вы на рабочем месте"
            } else {
                "Вы не на рабочем месте"
            }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_screen_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(SettingsActivity.getIntent(this))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun showTost(text: String) {
        runOnUiThread {
            Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
        }
    }
}
