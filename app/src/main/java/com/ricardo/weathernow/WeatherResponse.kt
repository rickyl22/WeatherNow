package com.ricardo.weathernow

data class WeatherResponse(val currently : WeatherPayloadCur, val daily : WeatherData)

data class WeatherData(val data : ArrayList<WeatherPayload>)

data class WeatherPayloadCur(val time : Long, val icon : String, val temperature : Double, val windSpeed : Double, val humidity : Double, val precipProbability : Double)

data class WeatherPayload(val time : Long, val icon : String, val temperatureHigh : Double, val temperatureLow : Double, val humidity : Double)