package com.ibulgakov.clientcontrolpc.ui.base

import android.location.Location
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks.CAUSE_NETWORK_LOST
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks.CAUSE_SERVICE_DISCONNECTED
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.ibulgakov.clientcontrolpc.Consts
import com.ibulgakov.clientcontrolpc.MainApp
import com.ibulgakov.clientcontrolpc.utils.LoginController
import com.ibulgakov.clientcontrolpc.utils.Prefs
import java.util.concurrent.TimeUnit


open class BaseActivity: AppCompatActivity(), GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    companion object {
        private val TAG = BaseActivity::class.java.simpleName
    }

    private var mGoogleApiClient: GoogleApiClient? = null
    private var mLocationRequest: LocationRequest? = null

    private val UPDATE_INTERVAL = TimeUnit.SECONDS.toMillis(10)
    private val FASTEST_INTERVAL: Long = TimeUnit.SECONDS.toMillis(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mGoogleApiClient = GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).build()
    }

    override fun onStart() {
        super.onStart()
        // Connect the client.
        mGoogleApiClient?.connect()
    }

    override fun onStop() {
        // Disconnecting the client invalidates it.
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this)

        mGoogleApiClient?.disconnect()

        super.onStop()
    }

    override fun onConnected(dataBundle: Bundle?) {
        // Get last known recent location.
        val currentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient)
        // Note that this can be NULL if last location isn't already known.
        if (currentLocation != null) {
            onLocationChanged(currentLocation)
        }
        // Begin polling for new location updates.
        startLocationUpdates()
    }

    override fun onConnectionSuspended(i: Int) {
        if (i == CAUSE_SERVICE_DISCONNECTED) {
            Toast.makeText(this, "Disconnected. Please re-connect.", Toast.LENGTH_SHORT).show();
        } else if (i == CAUSE_NETWORK_LOST) {
            Toast.makeText(this, "Network lost. Please re-connect.", Toast.LENGTH_SHORT).show();
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Toast.makeText(this, "Error. Please re-connect.", Toast.LENGTH_SHORT).show();
    }

    // Trigger new location updates at interval
    protected fun startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL)
        // Request location updates
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this)
    }

    override fun onLocationChanged(location: Location) {
        Log.d(TAG, "current location: " + location.toString())
        if (LoginController.isLoggedIn) {
            MainApp.globalBus.send(location)
            Prefs.save(Consts.Prefs.KEY_MY_LAST_POSITION, location)
        }
    }
}
