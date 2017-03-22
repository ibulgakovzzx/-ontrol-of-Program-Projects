package com.ibulgakov.clientcontrolpc.utils

import android.location.Location
import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.ibulgakov.clientcontrolpc.Consts

object TimeTrackerUtils {

    private val TAG = TimeTrackerUtils::class.java.simpleName

    fun hasSettingJobPlace(): Boolean =
            Prefs.load(Consts.Prefs.KEY_JOB_LOCATION, LatLng::class.java) != null

    fun isInWork(): Boolean {
        val jobLatLng = Prefs.load(Consts.Prefs.KEY_JOB_LOCATION, LatLng::class.java)
        val jobDistance = Prefs.get().getInt(Consts.Prefs.KEY_JOB_DISTANCE, 0)
        val myLocation = Prefs.load(Consts.Prefs.KEY_MY_LAST_POSITION, Location::class.java)
        //Log.d(TAG, "isInWork:: jobLatLng = $jobLatLng  myLocation = $myLocation  jobDistance = $jobDistance")
        Logger.print("isInWork:: jobLatLng = $jobLatLng  myLocation = $myLocation  jobDistance = $jobDistance")
        if (jobLatLng != null && myLocation != null) {
            return calculateIsInWork(jobLatLng, myLocation, jobDistance)
        } else {
           return false
        }
    }

    private fun calculateIsInWork(jobLatLng: LatLng, myPosition: Location, distance: Int): Boolean {
        val jobLocation = Location("")
        with(jobLocation) {
            longitude = jobLatLng.longitude
            latitude = jobLatLng.latitude
        }
        val distanceToWork =  myPosition.distanceTo(jobLocation)
        Log.d(TAG, "distanceToWork = $distanceToWork")
        return distanceToWork < distance
    }

}
