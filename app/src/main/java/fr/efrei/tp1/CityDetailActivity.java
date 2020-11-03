package fr.efrei.tp1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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



  private TextView name, textViewTemp, textViewTempMin, textViewTempMax;
  private RecyclerView recyclerView;


  private CityDetailActivity.OrderState currentOrderState = CityDetailActivity.OrderState.NotOrder;




  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    //We first set up the layout linked to the activity
    setContentView(R.layout.activity_city_detail);

    //Then we retrieved the widget
    name = findViewById(R.id.name);
    textViewTemp = findViewById(R.id.temperature);

    //Then we retrieve the extra
    final City city = (City) getIntent().getSerializableExtra(CityDetailActivity.USER_EXTRA);

    //Then we bind the City and the UI
    name.setText(city.name);


    //Then we update the title into the actionBar
    getSupportActionBar().setTitle(city.name);



    // Call for method to display weather data on the page
    //getWeatherData(name.toString());
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
    //We handle the click on a menu item
    if (item.getItemId() == R.id.delete)
    {
      // Code for the delete alert dialog, when the user tries to delete a city from his/her page
      AlertDialog dialog = new AlertDialog.Builder(this)
              .setTitle("Warning !")
              .setMessage("Are you sure you want to delete this city from your favorites ?")
              .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  // if user clicks on yes, the city will be deleted from the list
                  final String cityName = name.toString();
                  deleteCity(cityName);
                  //initList();

                  //we return to the previous screen
                  onBackPressed();
                }
              })
              .setNegativeButton("No", null) // Otherwise nothing happens
              .show();
    }




    return super.onOptionsItemSelected(item);
  }



  private void deleteCity (String cityName)
  {
    CityRepository.getInstance(this).deleteCity(new City(cityName));
  }


  // Call for API
  public void getWeatherData(String cityname){

    final City city = (City) getIntent().getSerializableExtra(CityDetailActivity.USER_EXTRA);
    IService iService = RetrofitInstance.getInstance().create(IService.class);



    Call<Example> call = iService.getWeatherData(cityname);

    call.enqueue((new Callback<Example>() {
      @Override
      public void onResponse(Call<Example> call, Response<Example> response) {


        //Log.d("DATA", response.body().getMain().getTemp());
        //textViewTemp.setText(response.body().getMain().getTemp());
        //textViewTempMin.setText("Min : "+response.body().getMain().getTemp_min());
        //textViewTempMax.setText("Max : "+response.body().getMain().getTemp_max());

        // set in temperature TextView of activity_city_detail.xml temperature value retrieved from API
        Example mydata = response.body();
        Main main = mydata.getMain();
        Double temp = main.getTemp();
        Integer temperature=(int)(temp-273.15);

        textViewTemp.setText(String.valueOf(temperature)+"C");

      }

      @Override
      public void onFailure(Call<Example> call, Throwable t) {
        Toast.makeText(CityDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

      }
    }));




  }



}