package com.ricardo.weathernow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface WeatherAPI {

    @Headers("Content-Type: application/json")
    @GET("/forecast/bd0cbde09ec91a027939fa728f0ede18/{lat},{lon}")
    Call<WeatherResponse> getWeatherRequest(@Path("lat") String lat,
                                            @Path("lon") String lon);

}
