package com.ibulgakov.clientcontrolpc.ui.main

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
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
import com.ibulgakov.clientcontrolpc.ui.base.BaseActivity
import com.ibulgakov.clientcontrolpc.ui.settings.SettingsActivity
import com.ibulgakov.clientcontrolpc.utils.TimeTrackerUtils
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket


class MainScreenActivity: BaseActivity() {

    companion object {
        fun getIntent(context: Context): Intent =
                Intent(context, MainScreenActivity::class.java)
    }

    private lateinit var tvStatus: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnSendCommand: Button
    private lateinit var commandText: EditText

    internal var serIpAddress: String = "192.168.0.102" // Server address
    internal var port = 10000           // Port
    internal var codeCommand: Byte = 0

    internal var codeNewUser: Byte = 1
    internal var codeChangePosition: Byte = 2
    internal var codeGetStatistic: Byte = 3
    internal var msg: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)

        tvStatus = find(R.id.tvStatus)
        btnUpdate = find(R.id.btnUpdate)
        btnSendCommand = find(R.id.btnSendCommand)
        commandText = find(R.id.command)


        tvStatus.text = getStatusText()
        MainApp.globalBus.observeEvents(LatLng::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { tvStatus.text = getStatusText() }

        btnUpdate.onClick {
            tvStatus.text = getStatusText()
        }

        btnSendCommand.onClick {
            val message = commandText.text.toString()
            codeCommand = message[0].toByte()
            val sender = SenderThread()
            sender.execute()
        }
    }

    private fun getStatusText(): String =
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
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(SettingsActivity.getIntent(this))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    internal inner class SenderThread : AsyncTask<Void, Void, Void>() {
        override fun doInBackground(vararg params: Void): Void? {
            try {
                val ipAddress = InetAddress.getByName(serIpAddress)
                val socket = Socket(ipAddress, port)
                val outputStream = socket.getOutputStream()
                val out = DataOutputStream(outputStream)
                when (codeCommand) {
                    codeNewUser -> {
                        out.write(codeNewUser.toInt())
                        out.flush()
                    }
                    codeChangePosition -> {
                        out.write(codeChangePosition.toInt())
                        out.flush()
                    }
                    codeGetStatistic -> {
                        out.write(codeGetStatistic.toInt())
                        out.flush()
                    }
                }
                socket.close()
                runOnUiThread {
                    Toast.makeText(applicationContext, "Message is receive", Toast.LENGTH_SHORT).show()
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                runOnUiThread {
                    Toast.makeText(applicationContext, "Server connection error", Toast.LENGTH_SHORT).show()
                }

            }
            return null
        }
    }
}
