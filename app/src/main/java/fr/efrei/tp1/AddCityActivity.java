package fr.efrei.tp1;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import fr.efrei.tp1.bo.City;
import fr.efrei.tp1.repository.CityRepository;

final public class AddCityActivity
    extends AppCompatActivity
    implements OnClickListener
{

  //The tag used into this screen for the logs
  public static final String TAG = AddCityActivity.class.getSimpleName();

  private EditText name;



  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    //We first set up the layout linked to the activity
    setContentView(R.layout.activity_add_city);

    //Then we retrieved the widget
    name = findViewById(R.id.name);

    //We configure the click on the save button
    findViewById(R.id.save).setOnClickListener(this);
  }

  @Override
  public void onClick(View v)
  {
    //we first retrieve user's entry
    final String cityName = name.getEditableText().toString();

    //We display that element into the logcat
    displayUserEntries(cityName);

    //We check if the user's entry is valid (not null and not empty)
    final boolean canAddCity = checkFormEntries(cityName);

    if (canAddCity == true)
    {
      //We add the city to the list and we reset the form
      saveCity(cityName);
      resetForm();
    }
    else
    {
      //we display a log error and a Toast
      Log.w(AddCityActivity.TAG, "Cannot add the city");
      Toast.makeText(this, R.string.cannot_add_city, Toast.LENGTH_SHORT).show();
    }
  }

  private void resetForm()
  {
    name.setText(null);

  }

  private void saveCity(String cityName)
  {
    CityRepository.getInstance(this).addCity(new City(cityName));
  }

  private boolean checkFormEntries(String cityName)
  {
    return TextUtils.isEmpty(cityName) == false == false;
  }

  private void displayUserEntries(String cityName)
  {
    Log.d(AddCityActivity.TAG, "name : '" + cityName + "'");

  }

}