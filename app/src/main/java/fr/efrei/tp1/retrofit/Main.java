package fr.efrei.tp1.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Main {

    @SerializedName("temp")
    double temp;

    @SerializedName("temp_min")
    double temp_min;

    @SerializedName("temp_max")
    double temp_max;

    @SerializedName("feels_like")
    double feels_like;

   /* @SerializedName("icon")
    List<String> icon;

    public List<String> getIcon() { return icon; }

    public void setIcon(String weather_icon) { this.icon = icon; }
*/
    public double getFeels_like() { return feels_like; }

    public void setFeels_like(double feels_like) { this.feels_like = feels_like; }

    public double getTemp() { return temp; }

    public void setTemp(double temp) { this.temp = temp; }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }
}
