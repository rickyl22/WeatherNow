package com.ricardo.weathernow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.graphics.Color
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt
import android.location.LocationManager
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            this.supportActionBar!!.hide()
        } catch (e: NullPointerException) {
        }
        setContentView(R.layout.activity_main)

        // heyyyyyyyyyyyyyyyyy

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, Array(1){ android.Manifest.permission.ACCESS_FINE_LOCATION}, 1)
        }else{
            initUI()
        }
        val node = Node("A",Node("B",Node("C",Node("D",Node("E",null)))))
    }
    class Node(val name : String,val next : Node? = null)



    fun remove(node: Node, index: Int): Node {
        var i = index
        if(index == 0) return node.next
        else{
            var cur = node
            while(i > 1){
                i--
                cur = cur.next!!
            }
            cur.next = cur?.next?.next
        }
        return node
    }

    fun initUI(){
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var apiInterface = WeatherAPIManager().client.create(WeatherAPI::class.java)
        val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        val longitude = location.longitude
        val latitude = location.latitude
        val call = apiInterface.getWeatherRequest(""+latitude,""+longitude)
        call.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                val payload = response.body()
                tv_current.text = payload?.currently?.temperature?.roundToInt().toString() + "\u2109"
                iv_today.setImageDrawable(getDrawable(getBackground(payload?.currently?.icon)))
                wind_today.text = payload?.currently?.windSpeed?.roundToInt().toString() + " mph"
                hum_today.text = (payload?.currently?.humidity!! * 100).roundToInt().toString() + "%"
                prec_today.text = (payload?.currently?.precipProbability!! * 100).roundToInt().toString() + "%"

                weekday1.text = SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date(payload?.daily.data[0].time!! * 1000))
                weekday2.text = SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date(payload?.daily.data[1].time!! * 1000))
                weekday3.text = SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date(payload?.daily.data[2].time!! * 1000))
                weekday4.text = SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date(payload?.daily.data[3].time!! * 1000))
                weekday5.text = SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date(payload?.daily.data[4].time!! * 1000))
                weekday6.text = SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date(payload?.daily.data[5].time!! * 1000))
                weekday7.text = SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date(payload?.daily.data[6].time!! * 1000))
                weekday8.text = SimpleDateFormat("EEEE", Locale.ENGLISH).format(Date(payload?.daily.data[7].time!! * 1000))

                high1.text = payload?.daily.data[0].temperatureHigh?.roundToInt().toString() + "\u2109"
                high2.text = payload?.daily.data[1].temperatureHigh?.roundToInt().toString() + "\u2109"
                high3.text = payload?.daily.data[2].temperatureHigh?.roundToInt().toString() + "\u2109"
                high4.text = payload?.daily.data[3].temperatureHigh?.roundToInt().toString() + "\u2109"
                high5.text = payload?.daily.data[4].temperatureHigh?.roundToInt().toString() + "\u2109"
                high6.text = payload?.daily.data[5].temperatureHigh?.roundToInt().toString() + "\u2109"
                high7.text = payload?.daily.data[6].temperatureHigh?.roundToInt().toString() + "\u2109"
                high8.text = payload?.daily.data[7].temperatureHigh?.roundToInt().toString() + "\u2109"

                low1.text = " / "+payload?.daily.data[0].temperatureLow?.roundToInt().toString() + "\u2109"
                low2.text = " / "+payload?.daily.data[1].temperatureLow?.roundToInt().toString() + "\u2109"
                low3.text = " / "+payload?.daily.data[2].temperatureLow?.roundToInt().toString() + "\u2109"
                low4.text = " / "+payload?.daily.data[3].temperatureLow?.roundToInt().toString() + "\u2109"
                low5.text = " / "+payload?.daily.data[4].temperatureLow?.roundToInt().toString() + "\u2109"
                low6.text = " / "+payload?.daily.data[5].temperatureLow?.roundToInt().toString() + "\u2109"
                low7.text = " / "+payload?.daily.data[6].temperatureLow?.roundToInt().toString() + "\u2109"
                low8.text = " / "+payload?.daily.data[7].temperatureLow?.roundToInt().toString() + "\u2109"

                img1.setImageDrawable(getDrawable(getBackground(payload?.daily?.data[0].icon)))
                img2.setImageDrawable(getDrawable(getBackground(payload?.daily?.data[1].icon)))
                img3.setImageDrawable(getDrawable(getBackground(payload?.daily?.data[2].icon)))
                img4.setImageDrawable(getDrawable(getBackground(payload?.daily?.data[3].icon)))
                img5.setImageDrawable(getDrawable(getBackground(payload?.daily?.data[4].icon)))
                img6.setImageDrawable(getDrawable(getBackground(payload?.daily?.data[5].icon)))
                img7.setImageDrawable(getDrawable(getBackground(payload?.daily?.data[6].icon)))
                img8.setImageDrawable(getDrawable(getBackground(payload?.daily?.data[7].icon)))

                if(Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >= 19 || Calendar.getInstance().get(Calendar.HOUR_OF_DAY) <= 5){
                    deepChangeTextColor(findViewById(R.id.main_ll))
                    val rl = findViewById<RelativeLayout>(R.id.main_ll)
                    rl.setBackgroundResource(R.drawable.night2)
                }


            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                call.cancel()
            }
        })
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            1 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    initUI()

                } else {

                    finish()
                }
                return
            }
        }
    }

    fun deepChangeTextColor( parentLayout : ViewGroup){
        for (count in 0..parentLayout.getChildCount()){
            var view = parentLayout.getChildAt(count)
            if(view is TextView){
                view.setTextColor(Color.WHITE)
            } else if(view is ViewGroup){
                deepChangeTextColor(view)
            }
        }
    }

    fun getBackground(icon : String?) : Int{
        if(icon.equals("clear-day",true)){
            return R.drawable.clear
        }else if(icon.equals("clear-night",true)){
            return R.drawable.night_clear
        }else if(icon.equals("rain",true)){
            return R.drawable.rain
        }else if(icon.equals("snow",true)){
            return R.drawable.snow
        }else if(icon.equals("sleet",true)){
            return R.drawable.sleet
        }else if(icon.equals("wind",true)){
            return R.drawable.wind
        }else if(icon.equals("fog",true)){
            return R.drawable.fog
        }else if(icon.equals("cloudy",true)){
            return R.drawable.cloudy
        }else if(icon.equals("partly-cloudy-night",true)){
            return R.drawable.partly_cloudy_night
        }else{
            return R.drawable.partly_cloudy
        }
    }
}
