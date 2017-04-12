package com.ibulgakov.clientcontrolpc.ui.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.ibulgakov.clientcontrolpc.Consts.Prefs.KEY_JOB_LOCATION
import com.ibulgakov.clientcontrolpc.R
import com.ibulgakov.clientcontrolpc.ui.base.BaseActivity
import com.ibulgakov.clientcontrolpc.utils.Prefs
import org.jetbrains.anko.toast

class SettingsMapsActivity : BaseActivity(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    companion object {
        fun getIntent(context: Context) =
                Intent(context, SettingsMapsActivity::class.java)
    }

    private var map: GoogleMap? = null

    private var jobLocation: LatLng?
        get() = Prefs.load(KEY_JOB_LOCATION, LatLng::class.java)
        set(value) = Prefs.save(KEY_JOB_LOCATION, value)

    private var jobMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map!!.setOnMapLongClickListener(this)
        if (jobLocation != null) {
            updateJobMarker()
        }
    }

    private fun updateJobMarker() {
        if (jobMarker == null) {
            jobMarker = map!!.addMarker(MarkerOptions().position(jobLocation!!))
        } else {
            jobMarker!!.position = jobLocation
        }
    }

    override fun onMapLongClick(point: LatLng) {
        jobLocation = point
        updateJobMarker()
        toast("Местоположение рабочего места задано!")
    }
}
