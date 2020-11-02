package fr.efrei.tp1;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import fr.efrei.tp1.bo.City;
import fr.efrei.tp1.repository.UserRepository;
import fr.efrei.tp1.retrofit.Example;
import fr.efrei.tp1.retrofit.IService;
import fr.efrei.tp1.retrofit.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

final public class CityDetailActivity
    extends AppCompatActivity
{

  public static final String USER_EXTRA = "userExtra";

  //The tag used into this screen for the logs
  public static final String TAG = CityDetailActivity.class.getSimpleName();

  private TextView name, textViewTemp, textViewTempMin, textViewTempMax;




  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    //We first set up the layout linked to the activity
    setContentView(R.layout.activity_city_detail);

    //Then we retrieved the widget we will need to manipulate into the
    name = findViewById(R.id.name);

    //Then we retrieve the extra
    final City city = (City) getIntent().getSerializableExtra(CityDetailActivity.USER_EXTRA);

    //Then we bind the User and the UI
    name.setText(city.name);


    //Then we update the title into the actionBar
    getSupportActionBar().setTitle(city.name);


    //
    getWeatherData(name.toString());

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
      // code delete alert dialog
      AlertDialog dialog = new AlertDialog.Builder(this)
              .setTitle("Warning !")
              .setMessage("Are you sure you want to delete this city from your favorites ?")
              .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                  final String cityName = name.getEditableText().toString();
                  deleteCity(cityName);

                  //we return to the previous screen
                  onBackPressed();
                }
              })
              .setNegativeButton("No", null)
              .show();
    }




    return super.onOptionsItemSelected(item);
  }

  private void deleteCity (String cityName)
  {
    UserRepository.getInstance(this).deleteCity(new City(cityName));
  }


  private void getWeatherData(String name){
    IService iService = RetrofitInstance.getInstance().create(IService.class);

    Call<Example> call = iService.getWeatherData(name);

    call.enqueue((new Callback<Example>() {
      @Override
      public void onResponse(Call<Example> call, Response<Example> response) {

        //Log.d("DATA", response.body().getMain().getTemp());
        textViewTemp.setText("Temperature : "+response.body().getMain().getTemp());
        textViewTempMin.setText("Min : "+response.body().getMain().getTemp_min());
        textViewTempMax.setText("Max : "+response.body().getMain().getTemp_max());

      }

      @Override
      public void onFailure(Call<Example> call, Throwable t) {

      }
    }));
  }



}