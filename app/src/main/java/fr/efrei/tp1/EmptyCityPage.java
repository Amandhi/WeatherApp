package fr.efrei.tp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;

import fr.efrei.tp1.bo.City;
import fr.efrei.tp1.repository.CityRepository;

public class EmptyCityPage
        extends AppCompatActivity
        implements View.OnClickListener {

    private enum OrderState
    {
        Order, NotOrder
    }

    //The tag used into this screen for the logs
    public static final String TAG = CitiesActivity.class.getSimpleName();

    private EmptyCityPage.OrderState currentOrderState = EmptyCityPage.OrderState.NotOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty_city_page);

        //We configure the click on the floating button
        findViewById(R.id.fab_empty_page).setOnClickListener(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //We init the list into the onResume method
        //so the list is updated each time the screen goes to foreground
        initList();
    }

    private void initList()
    {
        //We retrieve the list of cities to display
        final List<City> cities = currentOrderState == EmptyCityPage.OrderState.NotOrder ? CityRepository.getInstance(this).getCities() : CityRepository.getInstance(this).sortCitiesByName();


        //if that list is not empty then we call CitiesActivity class to display the list of cities chosen by the user
        final Intent intentHomePage = new Intent(this, CitiesActivity.class);
        if (!cities.isEmpty()){
            startActivity(intentHomePage);
        }


    }


    @Override
    public void onClick(View view) {
        //We open the FinalAddCityActivity screen when the user clicks on the floating button
        final Intent intent = new Intent(this, AddCityActivity.class);
        startActivity(intent);
    }
}