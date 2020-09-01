package com.example.weatherforecast


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.weatherforecast.util.ConnectionManager
import kotlinx.android.synthetic.main.activity_navigation.*
import org.json.JSONException
import java.text.DateFormat.getTimeInstance
import java.text.SimpleDateFormat

import java.time.Instant
import java.time.ZoneId
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    lateinit var txt_cityName: TextView
    lateinit var txt_date: TextView
    lateinit var txt_temp: TextView
    lateinit var txt_weatherType: TextView
    lateinit var txt_tempMin: TextView
    lateinit var txt_tempMax: TextView
    lateinit var txt_sunrise: TextView
    lateinit var txt_sunset: TextView
    lateinit var txt_wind: TextView
    lateinit var txt_humidity: TextView
    lateinit var text_cf: TextView
    lateinit var url: String
    lateinit var txt_tempMincf: TextView
    lateinit var txt_tempMincf2: TextView
    lateinit var sharedpreference: SharedPreferences
    lateinit var relativeCover: RelativeLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_home, container, false)
        sharedpreference = activity!!.getSharedPreferences(
            getString(R.string.shared_file_name),
            Context.MODE_PRIVATE
        )

        relativeCover = view.findViewById(R.id.relativeCover)
        relativeCover.visibility = View.VISIBLE
        txt_cityName = view.findViewById(R.id.txt_cityName)
        txt_date = view.findViewById(R.id.txt_date)
        txt_temp = view.findViewById(R.id.text_temp)
        txt_tempMin = view.findViewById(R.id.txt_tempMin)
        txt_tempMax = view.findViewById(R.id.txt_tempMax)
        txt_sunrise = view.findViewById(R.id.txt_sunrise)
        txt_sunset = view.findViewById(R.id.txt_sunset)
        txt_weatherType = view.findViewById(R.id.txt_weatherType)
        txt_wind = view.findViewById(R.id.txt_wind)
        txt_humidity = view.findViewById(R.id.txt_humidity)
        text_cf = view.findViewById(R.id.text_cf)
        txt_tempMincf = view.findViewById(R.id.txt_tempMincf)
        txt_tempMincf2 = view.findViewById(R.id.txt_tempMincf2)
        var windUnit = "mph"
        var queue = Volley.newRequestQueue(activity as Context)
        if (sharedpreference.getBoolean("switch", false)) {
            url = "https://api.openweathermap.org/data/2.5/weather?lat=${sharedpreference.getString(
                "latitude",
                "19.01"
            )}&lon=${sharedpreference.getString(
                "longitude",
                "72.85"
            )}&appid=8481bcab5651f492af5dfcaf89b4d943&units=${sharedpreference.getString(
                "units",
                "metric"
            )}"
        } else {
            url =
                "https://api.openweathermap.org/data/2.5/weather?q=${sharedpreference.getString(
                    "location",
                    "mumbai,India"
                )}&appid=8481bcab5651f492af5dfcaf89b4d943&units=${sharedpreference.getString(
                    "units",
                    "metric"
                )}"
        }
        if (ConnectionManager().checkConnection(activity as Context)) {

            var jsonObjReg = JsonObjectRequest(Request.Method.GET, url, null, Response.Listener {
                println(it)
                try {
                    if (sharedpreference.getString("units", "metric") == "imperial") {
                        windUnit = "mph"
                        text_cf.text = "\u2109"
                        txt_tempMincf.text = "\u2109"
                        txt_tempMincf2.text = "\u2109"
                    } else {
                        windUnit = "m/s"
                        text_cf.text = "\u2103"
                        txt_tempMincf.text = "\u2103"
                        txt_tempMincf2.text = "\u2103"
                    }
                    relativeCover.visibility = View.GONE
                    var cityName = it.getString("name")
                    sharedpreference.edit().putString("city", cityName).apply()
                    var main = it.getJSONObject("main")
                    var weather = it.getJSONArray("weather")
                    var sys = it.getJSONObject("sys")
                    var wind = it.getJSONObject("wind")
                    var coord = it.getJSONObject(("coord"))
                    sharedpreference.edit().putString("lat", coord.getString("lat")).apply()
                    sharedpreference.edit().putString("lon", coord.getString("lon")).apply()
                    txt_cityName.text = cityName
                    var cal = Calendar.getInstance()
                    var year = cal.get(Calendar.YEAR)
                    var date = cal.get(Calendar.DAY_OF_MONTH)
                    var day = SimpleDateFormat(
                        "EEEE",
                        Locale.ENGLISH
                    ).format(cal.getTime())//LocalDate.now().getDayOfWeek().name()
                    var month =
                        SimpleDateFormat("MMMM").format(cal.getTime()) //cal.get(Calendar.MONTH);
                    var findate = "${day},${month}" + "  " + "${date},${year}"
                    txt_date.text = findate
                    txt_temp.text = main.getString("temp").substring(0, 2)
                    txt_tempMin.text = main.getString("temp_min").substring(0, 2)
                    txt_tempMax.text = main.getString("temp_max").substring(0, 2)
                    txt_weatherType.text = weather.getJSONObject(0).getString("main")
                    txt_wind.text = wind.getString("speed") + " " + windUnit
                    txt_humidity.text = main.getString("humidity") + " " + "%"
                    val sdf = java.text.SimpleDateFormat("HH:mm")
                    var unix = sys.getString("sunrise")
                    val sunrise = java.util.Date(unix.toLong() * 1000)
                    txt_sunrise.text = sdf.format(sunrise)
                    val sdf2 = java.text.SimpleDateFormat("HH:mm")
                    var unix2 = sys.getString("sunset")
                    val sunset = java.util.Date(unix2.toLong() * 1000)
                    txt_sunset.text = sdf2.format(sunset)

                } catch (e: JSONException) {
                    Toast.makeText(
                        activity as Context,
                        "Json Error Occured",
                        Toast.LENGTH_LONG
                    ).show()
                }
            },
                Response.ErrorListener {
                    if (activity != null)
                        Toast.makeText(
                            activity as Context,
                            "Volley error Occured",
                            Toast.LENGTH_LONG
                        ).show()
                })
            queue.add(jsonObjReg)
        } else {
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("OOPS!!")
            dialog.setMessage("No Internet Connection Found")
            dialog.setPositiveButton("Go to Settings") { text, Listener ->
                val intent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(intent)
                activity!!.finish()
            }
            dialog.setNegativeButton("Exit") { text, Listener ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }
        return view
    }
    /**override fun onResume() {
    super.onResume()
    var trans=ContextCompat.getColor(activity as Context,R.color.setTrans)
    (activity as AppCompatActivity?)!!.supportActionBar!!.setBackgroundDrawable(trans)
    //toolbar.getBackground().setAlpha(0);
    // getActivity().getActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33000000")));
    //MainActivity.toolbar.setCustomView()
    }
    <RelativeLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@drawable/app_backgnd3"
    android:id="@+id/relativeCover">
    <ProgressBar
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_centerInParent="true"/>
    </RelativeLayout>




    <RelativeLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@drawable/app_backgnd3"
    android:id="@+id/relativeCover">
    <ProgressBar
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_centerInParent="true"/>
    </RelativeLayout>
    }**/

}
