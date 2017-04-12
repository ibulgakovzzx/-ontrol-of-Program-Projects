package com.ibulgakov.clientcontrolpc

import android.os.AsyncTask
import com.ibulgakov.clientcontrolpc.utils.LoginController
import com.ibulgakov.clientcontrolpc.utils.Prefs
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket

class SenderThread(var callback: Callback? = null, val codeCommand: Byte) : AsyncTask<Void, Void, Void>() {

    interface Callback {
        fun showTost(text: String)
    }

    internal var serIpAddress: String = "192.168.43.197" // Server address
    internal var port = 9998           // Port

    override fun doInBackground(vararg params: Void): Void? {
        try {
            val ipAddress = InetAddress.getByName(serIpAddress)
            val socket = Socket(ipAddress, port)
            val outputStream = socket.outputStream
            val out = DataOutputStream(outputStream)
            val userName = (Prefs.get().getString(LoginController.LOGIN, "") + "," + codeCommand.toInt())
                    .toByteArray()
            out.write(userName, 0, userName.size)
            out.flush()

            out.flush()
            socket.close()
            callback?.showTost("Message is receive")

        } catch (ex: Exception) {
            ex.printStackTrace()
            callback?.showTost("Server connection error")
        }
        return null
    }
}
