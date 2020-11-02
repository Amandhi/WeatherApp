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
import fr.efrei.tp1.preferences.AppPreferences;
import fr.efrei.tp1.repository.UserRepository;

final public class FinalAddCityActivity
    extends AppCompatActivity
    implements OnClickListener
{

  //The tag used into this screen for the logs
  public static final String TAG = FinalAddCityActivity.class.getSimpleName();

  private EditText loginEditText;
  private EditText name;


  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_final_add_city);

    //We retrieve the EditText References
    loginEditText = findViewById(R.id.login);



    //We add the listener on the "save" button
    findViewById(R.id.save).setOnClickListener(this);
  }

  @Override
  public void onClick(View v)
  {



    //we first retrieve user's entries
    final String cityName = name.getEditableText().toString();

    //We display the properties into the logcat
    //displayCitiesEntries(cityName);

    //We check if all entries are valid (not null and not empty)
    final boolean canAddCity = checkFormEntries(cityName);

    if (canAddCity == true)
    {
      //We add the user to the list and we reset the form
      saveCity(cityName);
      saveLogin();
      resetForm();
    }
    else
    {
      //we display a log error and a Toast
      Log.w(AddCityActivity.TAG, "Cannot add the city");
      Toast.makeText(this, R.string.cannot_add_city, Toast.LENGTH_SHORT).show();
    }
  }



  private void saveLogin()
  {
    //We save only if there is something to save
    if (TextUtils.isEmpty(loginEditText.getText()) == false)
    {
      AppPreferences.saveUserLogin(this, loginEditText.getText().toString());
    }

    //we return to the previous screen
    onBackPressed();
  }

  private void resetForm()
  {
    name.setText(null);

  }

  private void saveCity(String cityName)
  {
    UserRepository.getInstance(this).addCity(new City(cityName));
  }

  private boolean checkFormEntries(String cityName)
  {
    return TextUtils.isEmpty(cityName) == false == false;
  }

  /*private void displayCitiesEntries(String cityName)
  {
    Log.d(AddCityActivity.TAG, "name : '" + cityName + "'");

  }*/




}