package fr.efrei.tp1.adapter;

import java.util.List;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import fr.efrei.tp1.R;
import fr.efrei.tp1.CityDetailActivity;
import fr.efrei.tp1.adapter.CitiesAdapter.UserViewHolder;
import fr.efrei.tp1.bo.City;

public final class CitiesAdapter
    extends Adapter<UserViewHolder>
{

  //The ViewHolder class
  //Each Widget is created as an attribut in order to update the UI
  public static final class UserViewHolder
      extends ViewHolder
  {

    private final TextView name;



    public UserViewHolder(@NonNull View itemView)
    {
      super(itemView);

      //We find the references of the widgets
      name = itemView.findViewById(R.id.name);

    }

    public void update(final City city)
    {
      //We update the UI binding the current city to the current item
      name.setText(city.name);


      //We handle the click on the current item in order to display a new activity
      itemView.setOnClickListener(new OnClickListener()
      {

        @Override
        public void onClick(View v)
        {
          //We create the intent that display the UserDetailActivity.
          //The current user is added as an extra
          //The User class implement the "Serializable" interface so I can put the whole object as an extra
          final Intent intent = new Intent(itemView.getContext(), CityDetailActivity.class);
          intent.putExtra(CityDetailActivity.USER_EXTRA, city);

          itemView.getContext().startActivity(intent);
        }

      });
    }

  }

  private final List<City> cities;

  public CitiesAdapter(List<City> cities)
  {
    this.cities = cities;
  }

  @NonNull
  @Override
  public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
  {
    //We create the ViewHolder
    final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_city, parent, false);
    return new UserViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull UserViewHolder holder, int position)
  {
    //We update the ViewHolder
    holder.update(cities.get(position));
  }

  @Override
  public int getItemCount()
  {
    return cities.size();
  }

}
