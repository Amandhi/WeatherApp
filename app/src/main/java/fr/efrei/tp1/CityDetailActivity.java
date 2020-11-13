package fr.efrei.tp1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.efrei.tp1.adapter.CitiesAdapter;
import fr.efrei.tp1.bo.City;
import fr.efrei.tp1.repository.CityRepository;
import fr.efrei.tp1.retrofit.Example;
import fr.efrei.tp1.retrofit.IService;
import fr.efrei.tp1.retrofit.Main;
import fr.efrei.tp1.retrofit.MyInterceptor;
import fr.efrei.tp1.retrofit.RetrofitInstance;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

final public class CityDetailActivity
    extends AppCompatActivity

{


  private enum OrderState
  {
    Order, NotOrder
  }
  public static final String USER_EXTRA = "userExtra";

  //The tag used into this screen for the logs
  public static final String TAG = CityDetailActivity.class.getSimpleName();



  private TextView name, textViewTemp, textViewFeelsLike, textViewTempMin, textViewTempMax;
  private ImageView imageViewweatherIcon;
  private RecyclerView recyclerView;


  private CityDetailActivity.OrderState currentOrderState = CityDetailActivity.OrderState.NotOrder;




  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    //We first set up the layout linked to the activity
    setContentView(R.layout.activity_city_detail);

    //Then we retrieved the widget
    name = findViewById(R.id.Cityname);
    textViewTemp = findViewById(R.id.temperature);
    textViewFeelsLike = findViewById(R.id.temperature_feels_like);
    textViewTempMin = findViewById(R.id.temperature_min);
    textViewTempMax = findViewById(R.id.temperature_max);
    imageViewweatherIcon = findViewById(R.id.weather_icon);

    //Then we retrieve the extra
    final City city = (City) getIntent().getSerializableExtra(CityDetailActivity.USER_EXTRA);

    //Then we bind the City and the UI
    name.setText(city.name);


    //Then we update the title into the actionBar
    getSupportActionBar().setTitle(city.name);



    // Call for method to display weather data on the page
    getWeatherData(city.name);

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu)
  {
    //We add the "menu_order" to the current activity
    getMenuInflater().inflate(R.menu.menu_order, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item)
  {
    name = findViewById(R.id.Cityname);
    String cityToDelete = name.getText().toString();

    //We handle the click on a menu item
    if (item.getItemId() == R.id.delete)
    {
      // Code for the delete alert dialog, when the user tries to delete a city from his/her page
      AlertDialog dialog = new AlertDialog.Builder(this)
              .setTitle("Warning !")
              .setMessage("Are you sure you want to delete "+cityToDelete+" from your favorites ?")
              .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  // if user clicks on yes, the city will be deleted from the list
                  final City city = (City) getIntent().getSerializableExtra(CityDetailActivity.USER_EXTRA);
                  name.setText(city.name);
                  UserdeletesCity(city);
                }
              })
              .setNegativeButton("No", null) // Otherwise nothing happens
              .show();
    }




    return super.onOptionsItemSelected(item);
  }



  //method called when the user decides to delete a city from his/her list
  private void UserdeletesCity (City cityName)
  {
    CityRepository.getInstance(this).deleteCity(cityName);

    final List<City> cities = currentOrderState == CityDetailActivity.OrderState.NotOrder ? CityRepository.getInstance(this).getCities() : CityRepository.getInstance(this).sortCitiesByName();

    //if the deletion of the city leads to an empty list then we call EmptyCityPage to inform the user to add cities (message on home page)
    if(cities.isEmpty()){
      final Intent EmptyPageAfterDeletion = new Intent(this, EmptyCityPage.class);
      startActivity(EmptyPageAfterDeletion);
    } else { //if there remain cities in the list after the deletion then we display that list of remaining cities on the home page
      final Intent ListPageAfterDeletion = new Intent(this, CitiesActivity.class);
      startActivity(ListPageAfterDeletion);
    }
  }


  // Call for API
  public void getWeatherData(String cityname){

    final City city = (City) getIntent().getSerializableExtra(CityDetailActivity.USER_EXTRA);
    IService iService = RetrofitInstance.getInstance().create(IService.class);



    Call<Example> call = iService.getWeatherData(cityname);

    call.enqueue((new Callback<Example>() {
      @Override
      public void onResponse(Call<Example> call, Response<Example> response) {


        // set in temperature TextView of activity_city_detail.xml temperature value retrieved from API in celsius degree
        Example mydata = response.body();
        Main main = mydata.getMain();
        double temp = main.getTemp();
        Integer temperature=(int)(temp-273.15);

        textViewTemp.setText(String.valueOf(temperature)+"°C");

        // set in feels_like of activity_city_detail.xml feels like temperature value retrieved from API in celsius degree
        double temp_feels_like = main.getFeels_like();
        Integer like=(int)(temp_feels_like-273.15);
        textViewFeelsLike.setText("Feels like: "+String.valueOf(like)+"°C");

        // set in temperature_min of activity_city_detail.xml min temperature value retrieved from API in celsius degree
        double temp_min = main.getTemp_min();
        Integer min=(int)(temp_min-273.15);
        textViewTempMin.setText("Min: "+String.valueOf(min)+"°C");

        // set in temperature_max of activity_city_detail.xml max temperature value retrieved from API in celsius degree
        double temp_max = main.getTemp_max();
        Integer max=(int)(temp_max-273.15);
        textViewTempMax.setText("Max: "+String.valueOf(max)+"°C");

        // this bit of code is to display the weather icon according to a city's current weather in activity_city_detail page
        //However this part does not work and troubles the rest of the code

        /*//Example icon = response.body();
        //List<String> mainIcon = mydata.getWeather();
        String weatherIcon = mydata.getWeather().get(3);
        //String wIcon = weatherIcon.getString(weatherIcon);

        



        if (weatherIcon.equals("01d")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_01d));
        }else if (weatherIcon.equals("02d")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_02d));
        }else if (weatherIcon.equals("03d")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_03d));
        }else if (weatherIcon.equals("04d")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_04d));
        }else if (weatherIcon.equals("09d")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_09d));
        }else if (weatherIcon.equals("010d")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_10d));
        }else if (weatherIcon.equals("11d")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_11d));
        }else if (weatherIcon.equals("13d")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_13d));
        }else if (weatherIcon.equals("50d")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img50d));
        }else if (weatherIcon.equals("01n")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_01n));
        }else if (weatherIcon.equals("02n")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_02n));
        }else if (weatherIcon.equals("03n")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_03n));
        }else if (weatherIcon.equals("04n")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_04n));
        }else if (weatherIcon.equals("09n")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_09n));
        }else if (weatherIcon.equals("010n")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_10n));
        }else if (weatherIcon.equals("11n")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_11n));
        }else if (weatherIcon.equals("13n")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img_13n));
        }else if (weatherIcon.equals("50n")){
          imageViewweatherIcon.setImageDrawable(getResources().getDrawable(R.drawable.img50n));
        }*/


      }

      @Override
      public void onFailure(Call<Example> call, Throwable t) {
        Toast.makeText(CityDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

      }
    }));




  }



}