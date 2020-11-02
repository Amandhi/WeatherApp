package fr.efrei.tp1;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import fr.efrei.tp1.adapter.CitiesAdapter;
import fr.efrei.tp1.bo.City;
import fr.efrei.tp1.repository.UserRepository;

final public class CitiesActivity
    extends AppCompatActivity
    implements OnClickListener
{

  private enum OrderState
  {
    Order, NotOrder
  }

  //The tag used into this screen for the logs
  public static final String TAG = CitiesActivity.class.getSimpleName();

  private RecyclerView recyclerView;

  private OrderState currentOrderState = OrderState.NotOrder;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    //We first set up the layout linked to the activity
    setContentView(R.layout.activity_cities);

    recyclerView = findViewById(R.id.recyclerView);
    recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    //We configure the click on the fab
    findViewById(R.id.fab).setOnClickListener(this);
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
    //We retrieve the list of users to display
    final List<City> cities = currentOrderState == OrderState.NotOrder ? UserRepository.getInstance(this).getCity() : UserRepository.getInstance(this).sortCitiesByName();

    //We create the adapter and we attach it to the RecyclerView
    final CitiesAdapter usersAdapter = new CitiesAdapter(cities);
    recyclerView.setAdapter(usersAdapter);
  }

  @Override
  public void onClick(View v)
  {
    //We open the AddUserActivity screen when the user clicks on the FAB
    final Intent intent = new Intent(this, FinalAddCityActivity.class);
    startActivity(intent);
  }

}