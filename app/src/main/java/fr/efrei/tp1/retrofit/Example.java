package fr.efrei.tp1.retrofit;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Example {

    @SerializedName("main")
    private Main main;

   /* @SerializedName("weather")
    private List<String> weather;

    public List<String> getWeather() { return weather; }

    public void setWeather(ArrayList<String> weather) { this.weather = weather; }*/

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}
