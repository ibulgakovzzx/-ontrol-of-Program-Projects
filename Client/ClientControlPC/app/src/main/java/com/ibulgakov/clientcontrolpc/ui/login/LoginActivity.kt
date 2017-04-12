package com.ibulgakov.clientcontrolpc.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ibulgakov.clientcontrolpc.R
import com.ibulgakov.clientcontrolpc.SenderThread
import com.ibulgakov.clientcontrolpc.ui.base.BaseActivity
import com.ibulgakov.clientcontrolpc.ui.main.MainScreenActivity
import com.ibulgakov.clientcontrolpc.ui.settings.SettingsMapsActivity
import com.ibulgakov.clientcontrolpc.utils.LoginController
import org.jetbrains.anko.find
import org.jetbrains.anko.onClick

class LoginActivity : BaseActivity(), SenderThread.Callback {
    companion object {
        private val REQUEST_CODE_MAPS = 11
    }



    private lateinit var etCode: EditText
    private lateinit var btnSend: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (LoginController.isLoggedIn) {
            startActivity(MainScreenActivity.getIntent(this))
            finish()
        }

        setContentView(R.layout.activity_login)

        etCode = find(R.id.etCode)
        btnSend = find(R.id.btnSend)

        btnSend.onClick {
            val sender = SenderThread(this, 52)
            sender.execute()
            navigateToMapsScreen()
        }
    }

    fun navigateToMapsScreen() {
        LoginController.login(etCode.text.toString())
        startActivityForResult(SettingsMapsActivity.getIntent(this), REQUEST_CODE_MAPS)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_MAPS) {
            startActivity(MainScreenActivity.getIntent(this))
            finish()
        }
    }

    override fun showTost(text: String) {
        runOnUiThread {
            Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
        }
    }
}
