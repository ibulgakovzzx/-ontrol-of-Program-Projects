package com.ibulgakov.clientcontrolpc.tests

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import com.ibulgakov.clientcontrolpc.utils.Logger
import com.ibulgakov.clientcontrolpc.utils.TimeTrackerUtils

class TestTimeTrackerUtils {

    companion object {
        private val CLASS_NAME = TestTimeTrackerUtils::class.java.simpleName
    }

    init {
        val distanceToWork = 300

        val jobLatLng = LatLng(47.1315, 47.22)
        val myPosition = Location("").apply {
            latitude = 47.1311
            longitude = 47.22
        }

        val jobLatLng2 = LatLng(47.1315, 47.22)
        val myPosition2 = Location("").apply {
            latitude = 47.1811
            longitude = 47.22
        }

        Logger.print(CLASS_NAME, TimeTrackerUtils.calculateIsInWork(jobLatLng, myPosition, distanceToWork).toString())
        Logger.print(CLASS_NAME, TimeTrackerUtils.calculateIsInWork(jobLatLng2, myPosition2, distanceToWork).toString())
    }

}
