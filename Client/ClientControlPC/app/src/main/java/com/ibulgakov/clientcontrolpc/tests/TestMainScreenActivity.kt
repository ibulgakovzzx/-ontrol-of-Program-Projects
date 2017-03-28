package com.ibulgakov.clientcontrolpc.tests

import com.ibulgakov.clientcontrolpc.ui.main.MainScreenActivity
import com.ibulgakov.clientcontrolpc.utils.Logger

class TestMainScreenActivity {

    companion object {
        private val CLASS_NAME = TestMainScreenActivity::class.java.simpleName
    }

    init {
        val isInWork1 = true
        val isInWork2 = false
        val isInWork3 = null

        Logger.print(CLASS_NAME, MainScreenActivity().getStatusText(isInWork1))
        Logger.print(CLASS_NAME, MainScreenActivity().getStatusText(isInWork2))
        //Logger.print(MainScreenActivity().getStatusText(isInWork3))
    }

}
