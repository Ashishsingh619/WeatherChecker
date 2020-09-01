package com.example.weatherforecast


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.gms.maps.SupportMapFragment
import java.util.jar.Manifest


/**
 * A simple [Fragment] subclass.
 */
class SettingFragment : Fragment() {
    lateinit var sharedpreference: SharedPreferences
    lateinit var et_locationCity: String
    lateinit var et_locationCountry: String
    lateinit var et_units: String
    lateinit var btn_done: Button
    lateinit var switch_loc: Switch
    lateinit var relativeSetting: RelativeLayout
    var permissionId = 52
    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_setting, container, false)
        sharedpreference = activity!!.getSharedPreferences(
            getString(R.string.shared_file_name),
            Context.MODE_PRIVATE
        )
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(activity as Context)
        switch_loc = view.findViewById(R.id.switch_loc)
        relativeSetting = view.findViewById(R.id.relativeSetting)
        if (sharedpreference.getBoolean("switch", false)) {
            switch_loc.setChecked(true)
            relativeSetting.visibility = View.INVISIBLE
        } else {
            switch_loc.setChecked(false)
            relativeSetting.visibility = View.VISIBLE
        }
        switch_loc.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                //Toast.makeText(activity as Context,"On",Toast.LENGTH_LONG).show()
                getLastLOcation()
                relativeSetting.visibility = View.INVISIBLE
                sharedpreference.edit().putBoolean("switch", true).apply()

            } else {
                relativeSetting.visibility = View.VISIBLE
                sharedpreference.edit().putBoolean("switch", false).apply()
            }
        })
        btn_done = view.findViewById(R.id.btn_done)
        btn_done.setOnClickListener {
            et_locationCity = view.findViewById<EditText>(R.id.et_locationCity).text.toString()
            et_units = view.findViewById<EditText>(R.id.et_units).text.toString()
            et_locationCountry =
                view.findViewById<EditText>(R.id.et_locationCountry).text.toString()

            if (et_locationCity.isEmpty() || et_units.isEmpty() || et_locationCountry.isEmpty()) {
                Toast.makeText(
                    activity as Context,
                    "The input field Can't be empty",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                if (et_units.toLowerCase().trim() == "metric" || et_units.toLowerCase()
                        .trim() == "imperial"
                ) {
                    sharedpreference.edit().putString(
                        "location",
                        "${et_locationCity.toLowerCase()
                            .trim()},${et_locationCountry.toLowerCase()}"
                    ).apply()
                    sharedpreference.edit().putString("units", et_units.toLowerCase().trim())
                        .apply()
                    getFragmentManager()!!.beginTransaction().replace(R.id.frame, HomeFragment())
                        .commit()
                    setActivityTitle("Home")
                } else {
                    Toast.makeText(activity as Context, "Mention proper units", Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
        return view
    }

    private fun checkPerminssion(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                activity as Context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(
                activity as Context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
            return true
        else
            return false
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            activity as Activity,
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            permissionId
        )
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager =
            getActivity()!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    fun getLastLOcation() {
        if (checkPerminssion()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result
                    if (location == null)
                        getNewLocation()
                    else {
                        sharedpreference.edit().putString("latitude", location.latitude.toString())
                            .apply()
                        sharedpreference.edit()
                            .putString("longitude", location.longitude.toString()).apply()
                        println(location.latitude.toString())
                        println(location.longitude.toString())
                    }
                }
            } else {
                val dialog = AlertDialog.Builder(activity as Context)
                dialog.setTitle("OOPS!!")
                dialog.setMessage("Turn on your Location")
                dialog.setPositiveButton("Go to Settings") { text, Listener ->
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                    activity!!.finish()
                }
                dialog.setNegativeButton("Exit") { text, Listener ->
                    ActivityCompat.finishAffinity(activity as Activity)
                }
                dialog.create()
                dialog.show()
            }
        } else {
            requestPermission()
        }
    }

    fun getNewLocation() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2
        fusedLocationProviderClient!!.requestLocationUpdates(
            locationRequest, locationCallback,
            Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {
            var lastlocation = p0!!.lastLocation
            sharedpreference.edit().putString("latitude", lastlocation.latitude.toString()).apply()
            sharedpreference.edit().putString("longitude", lastlocation.longitude.toString())
                .apply()
            println(lastlocation.latitude.toString())
            println(lastlocation.longitude.toString())

        }
    }

    //important
    fun Fragment.setActivityTitle(title: String) {
        (activity as AppCompatActivity?)!!.supportActionBar?.title = title
    }


}
