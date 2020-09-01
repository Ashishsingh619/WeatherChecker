package com.example.weatherforecast

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var toolbar: Toolbar
    lateinit var sharedpreference: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        sharedpreference = getSharedPreferences(
            getString(R.string.shared_file_name),
            Context.MODE_PRIVATE
        )
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Maps"
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val city = LatLng(
            sharedpreference.getString("lat", "-34.0")!!.toDouble(),
            sharedpreference.getString("lon", "151.0")!!.toDouble()
        )//151.0
        mMap.addMarker(
            MarkerOptions().position(city).title("${sharedpreference.getString("city", "sydney")}")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(city, 10f))
    }
}
